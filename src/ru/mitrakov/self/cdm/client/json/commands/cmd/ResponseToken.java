package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseToken extends CmdResponse {
    public final String token;

    public ResponseToken(int sid, String token) {
        super("_tok", sid);
        this.token = token;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.token);
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
        final ResponseToken other = (ResponseToken) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseToken{" + "token=" + token + '}';
    }
}