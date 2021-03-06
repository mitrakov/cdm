package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.*;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseMove extends CmdResponse {
    public final List<Integer> path;
    public final List<String> state;

    public ResponseMove(int sid, Collection<? extends Integer> path, Collection<? extends String> state) {
        super("_mov", sid);
        this.path = new ArrayList<>(path);
        this.state = new ArrayList<>(state);
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.path);
        hash = 79 * hash + Objects.hashCode(this.state);
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
        final ResponseMove other = (ResponseMove) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseMove{" + "path=" + path + ", state=" + state + '}';
    }
}
