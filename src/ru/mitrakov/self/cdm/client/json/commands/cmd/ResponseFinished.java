package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseFinished extends CmdResponse {
    public final String winnerName;

    public ResponseFinished(int sid, String winnerName) {
        super("_end", sid);
        this.winnerName = winnerName;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.winnerName);
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
        final ResponseFinished other = (ResponseFinished) obj;
        if (!Objects.equals(this.winnerName, other.winnerName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseFinished{" + "winnerName=" + winnerName + '}';
    }
}
