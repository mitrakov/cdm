package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.*;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseState extends CmdResponse {
    public final List<String> state;

    public ResponseState(int sid, Collection<? extends String> state) {
        super("_st", sid);
        this.state = new ArrayList<>(state);
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.state);
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
        final ResponseState other = (ResponseState) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseState{" + "state=" + state + '}';
    }
}
