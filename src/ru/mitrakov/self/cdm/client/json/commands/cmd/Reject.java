package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class Reject extends CmdRequest {
    public final int enemySid;

    public Reject(int sid, String token, int enemySid) {
        super("rej", sid, token);
        this.enemySid = enemySid;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.enemySid;
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
        final Reject other = (Reject) obj;
        if (this.enemySid != other.enemySid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reject{" + "enemySid=" + enemySid + '}';
    }
}