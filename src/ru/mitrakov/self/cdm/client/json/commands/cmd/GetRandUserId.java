package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class GetRandUserId extends CmdRequest {
    public GetRandUserId(int sid, String token) {
        super("rid", sid, token);
    }

    // GENERATED CODE
    @Override
    public String toString() {
        return "GetRandUserId{" + '}';
    }
}
