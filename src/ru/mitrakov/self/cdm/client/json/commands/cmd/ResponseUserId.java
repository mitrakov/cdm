package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseUserId extends CmdResponse {
    public final String name;
    public final int userId;

    public ResponseUserId(int sid, String name, int userId) {
        super("_uid", sid);
        this.name = name;
        this.userId = userId;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + this.userId;
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
        final ResponseUserId other = (ResponseUserId) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseUserId{" + "name=" + name + ", userId=" + userId + '}';
    }
}
