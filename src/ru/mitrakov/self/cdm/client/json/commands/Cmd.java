package ru.mitrakov.self.cdm.client.json.commands;

import java.util.Objects;

/**
 *
 * @author Tommy
 */
public abstract class Cmd {
    public final String cmd;
    
    public Cmd(String cmd) {
        this.cmd = cmd;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.cmd);
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
        final Cmd other = (Cmd) obj;
        if (!Objects.equals(this.cmd, other.cmd)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cmd{" + "cmd=" + cmd + '}';
    }
}
