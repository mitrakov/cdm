package ru.mitrakov.self.cdm.client;

import com.sun.istack.internal.Nullable;
import java.io.IOException;
import java.net.*;
import ru.mitrakov.self.cdm.client.handlers.Handler;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;


/**
 *
 * @author Tommy
 */
public class Starter {
    public static Model model;
    private static boolean needRestart = true;
    private static Cmd startCmd;
    
    public static void main(String[] args) throws InterruptedException {
        // turn asserts on
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        
        // convert the app into a singleton process
        registerInstance();
        
        // check whether the app should run in background
        if (args.length == 1 && args[0].equals("background"))
            needRestart = false;
        
        // infinite loop to make the app restartable
        while (true) {
            if (needRestart) {
                if (model == null || !model.engine.getContext().isCreated())
                    model = new Model().start();
                if (startCmd != null) {
                    Thread.sleep(2000);  // to make the model ready
                    model.getNetwork().getHandler().handle(startCmd);
                }
                needRestart = false;
                startCmd = null;
            } else Thread.sleep(100);
        }
    }
    
    public static void restartModel(@Nullable Cmd cmd) {
        needRestart = true;
        startCmd = cmd;
    }
    
    private static void registerInstance() {
        // creating new thread because accept() is a blocking operation
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // start listening on a loopback socket (if no exceptions thrown,
                    // then we run a first instance of the app
                    ServerSocket serverSocket = new ServerSocket(37686, 10, InetAddress.getByAddress(new byte[] {127,0,0,1}));
                    while (true) {
                        try (Socket sock = serverSocket.accept()) {
                            // this code would run only if the user is attempting
                            // to start another instance of the app;
                            // it usually means that the user wants
                            // to switch the app from background to normal mode;
                            // so we just restart itself (whilst the other instance
                            // will be closed automatically)
                            if (sock.getInetAddress().isLoopbackAddress())
                                restartModel(null);
                        }
                    }
                } catch (IOException ignored) {
                    // failed to seize socket! It means that the user is attempting
                    // to start another instance; so we notify the first instance
                    // about this (causing it to restart) and shutdown itself
                    try {
                        System.err.println("Another instance is already running");
                        new Socket("127.0.0.1", 37686).close();
                        System.exit(0);
                    } catch (Exception ignore) {}
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }
}
