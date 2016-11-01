package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.*;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseUnits extends CmdResponse {
    public final List<String> names;
    public final int captainId;

    public ResponseUnits(int sid, Collection<? extends String> names, int captainId) {
        super("_uu", sid);
        this.names = new ArrayList<>(names);
        this.captainId = captainId;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.names);
        hash = 97 * hash + this.captainId;
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
        final ResponseUnits other = (ResponseUnits) obj;
        if (!Objects.equals(this.names, other.names)) {
            return false;
        }
        if (this.captainId != other.captainId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseUnits{" + "names=" + names + ", captainId=" + captainId + '}';
    }
}
