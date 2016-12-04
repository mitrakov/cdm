package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.*;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseStrike extends CmdResponse {
    public final List<List<Integer>> arrays;
    public final List<String> state;

    public ResponseStrike(int sid, Collection<? extends List<Integer>> arrays, Collection<? extends String> state) {
        super("_str", sid);
        this.arrays = new ArrayList<>(arrays);
        this.state = new ArrayList<>(state);
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.arrays);
        hash = 13 * hash + Objects.hashCode(this.state);
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
        final ResponseStrike other = (ResponseStrike) obj;
        if (!Objects.equals(this.arrays, other.arrays)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseStrike{" + "arrays=" + arrays + ", state=" + state + '}';
    }
}
