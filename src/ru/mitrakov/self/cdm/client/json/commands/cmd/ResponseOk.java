package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseOk extends CmdResponse {
    public ResponseOk(int sid) {
        super("_ok", sid);
    }
}
