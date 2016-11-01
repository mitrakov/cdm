package ru.mitrakov.self.cdm.client.handlers;

import ru.mitrakov.self.cdm.client.json.commands.cmd.ResponseToken;
import ru.mitrakov.self.cdm.client.json.commands.cmd.ResponseError;
import ru.mitrakov.self.cdm.client.json.commands.cmd.ResponseOk;
import ru.mitrakov.self.cdm.client.json.commands.*;
import java.io.IOException;
import ru.mitrakov.self.cdm.client.game.IStorage;

/**
 *
 * @author Tommy
 */
public class FirstHandler extends Handler {
    private final IStorage storage;

    public FirstHandler(Handler parent, IStorage storage) {
        super(parent);
        this.storage = storage;
    }

    @Override
    public void handle(Cmd cmd) throws IOException {
        if (cmd instanceof ResponseOk)
            System.out.println("Server responded OK");
        else if (cmd instanceof ResponseError)
            System.err.println(((ResponseError)cmd).error);
        else super.handle(cmd);
    }
}
