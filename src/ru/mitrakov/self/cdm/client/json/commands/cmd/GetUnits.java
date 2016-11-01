package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class GetUnits extends CmdRequest {
    public GetUnits(int sid, String token) {
        super("guu", sid, token);
    }

    // GENERATED CODE
    @Override
    public String toString() {
        return "GetUnits{" + '}';
    }
}
