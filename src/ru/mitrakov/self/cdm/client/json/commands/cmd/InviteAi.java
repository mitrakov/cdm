package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class InviteAi extends CmdRequest {

    public InviteAi(int sid, String token) {
        super("iai", sid, token);
    }

    // GENERATED CODE
    @Override
    public String toString() {
        return "InviteAi{" + '}';
    }
}
