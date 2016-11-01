package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class Invite extends CmdRequest {
    public final int userId;

    public Invite(int sid, String token, int userId) {
        super("inv", sid, token);
        this.userId = userId;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.userId;
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
        final Invite other = (Invite) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invite{" + "userId=" + userId + '}';
    }
}
