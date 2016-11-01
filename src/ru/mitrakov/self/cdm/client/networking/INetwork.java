package ru.mitrakov.self.cdm.client.networking;

import ru.mitrakov.self.cdm.client.handlers.Handler;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;

/**
 *
 * @author Tommy
 */
public interface INetwork {
    public void send(Cmd cmd);
    public void start();
    public void setHandler(Handler handler);
    public int getSid();
}
