package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.*;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseAction extends CmdResponse {
    public final int action;
    public final List<Integer> path;
    public final List<String> state;

    public ResponseAction(int sid, int action, Collection<? extends Integer> path, Collection<? extends String> state) {
        super("_ac", sid);
        this.action = action;
        this.path = new ArrayList<>(path);
        this.state = new ArrayList<>(state);
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.action;
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
        final ResponseAction other = (ResponseAction) obj;
        if (this.action != other.action) {
            return false;
        }
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
        return "ResponseAction{" + "action=" + action + ", path=" + path + ", state=" + state + '}';
    }
}
