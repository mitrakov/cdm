package ru.mitrakov.self.cdm.client.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mitrakov.self.cdm.client.handlers.Handler;
import ru.mitrakov.self.cdm.client.json.Parser;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;
import ru.mitrakov.self.cdm.client.json.commands.CmdSid;

/**
 *
 * @author Tommy
 */
public class Network implements INetwork {
    
    protected DatagramSocket sock;
    protected int sid = 0;
    protected Handler handler;
    
    public Network() {
        try {
            this.sock = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    try {
                        DatagramPacket datagram = new DatagramPacket(new byte[1024], 1024);
                        sock.receive(datagram);
                        String json = new String(datagram.getData(), 0, datagram.getLength());
                        System.out.println(json);
                        Cmd cmd = Parser.parseString(json);
                        if (cmd instanceof CmdSid)
                            sid = ((CmdSid)cmd).sid;
                        handler.handle(cmd);
                    } catch (IOException ex) {
                        Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void send(Cmd cmd) {
        try {
            String jsonStr = Parser.convertToJson(cmd);
            System.out.println("Trying to send:" + jsonStr);
            byte[] data = jsonStr.getBytes();
            this.sock.send(new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.3"), 33995));
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSid() {
        return sid;
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    
    @Override
    public Handler getHandler() {
        return handler;
    }
}
