package ru.mitrakov.self.cdm.client.json.commands;

/**
 *
 * @author Tommy
 */
public abstract class CmdResponse extends CmdSid {
    public CmdResponse(String cmd, int sid) {
        super(cmd, sid);
    }
    
    // GENERATED CODE
    @Override
    public String toString() {
        return "CmdResponse{" + '}';
    }
}
