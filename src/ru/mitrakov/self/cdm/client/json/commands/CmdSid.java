package ru.mitrakov.self.cdm.client.json.commands;

/**
 *
 * @author Tommy
 */
public abstract class CmdSid extends Cmd {
    public final int sid;

    public CmdSid(String cmd, int sid) {
        super(cmd);
        this.sid = sid;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.sid;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CmdSid other = (CmdSid) obj;
        if (this.sid != other.sid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CmdSid{" + "sid=" + sid + '}';
    }
}
