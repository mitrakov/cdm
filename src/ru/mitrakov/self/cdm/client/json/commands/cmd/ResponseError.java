package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.CmdResponse;

/**
 *
 * @author Tommy
 */
public class ResponseError extends CmdResponse {
    public final String error;
    public final int code;

    public ResponseError(int sid, int code, String error) {
        super("_err", sid);
        this.code = code;
        this.error = error;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.error);
        hash = 17 * hash + this.code;
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
        final ResponseError other = (ResponseError) obj;
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (this.code != other.code) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseError{" + "error=" + error + ", code=" + code + '}';
    }
}
