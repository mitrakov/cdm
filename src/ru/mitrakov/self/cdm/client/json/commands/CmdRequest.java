package ru.mitrakov.self.cdm.client.json.commands;

import java.util.Objects;

/**
 *
 * @author Tommy
 */
public abstract class CmdRequest extends CmdSid {
    public final String token;

    public CmdRequest(String cmd, int sid, String token) {
        super(cmd, sid);
        this.token = token;
    }
    
    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.token);
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
        final CmdRequest other = (CmdRequest) obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CmdRequest{" + "token=" + token + '}';
    }
}
