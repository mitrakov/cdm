package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class GiveUp extends CmdRequest {

    public GiveUp(int sid, String token) {
        super("gup", sid, token);
    }

    // GENERATED CODE
    @Override
    public String toString() {
        return "GiveUp{" + '}';
    }
}
