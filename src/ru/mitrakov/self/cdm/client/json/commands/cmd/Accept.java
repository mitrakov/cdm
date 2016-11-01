package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class Accept extends CmdRequest {
    public final int enemySid;

    public Accept(int sid, String token, int enemySid) {
        super("acc", sid, token);
        this.enemySid = enemySid;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.enemySid;
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
        final Accept other = (Accept) obj;
        if (this.enemySid != other.enemySid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accept{" + "enemySid=" + enemySid + '}';
    }
}
