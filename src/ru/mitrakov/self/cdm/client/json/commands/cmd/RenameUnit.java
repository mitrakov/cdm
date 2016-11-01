package ru.mitrakov.self.cdm.client.json.commands.cmd;

import java.util.Objects;
import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class RenameUnit extends CmdRequest {
    public final int unitIdx;
    public final String name;

    public RenameUnit(int sid, String token, int unitIdx, String name) {
        super("ruu", sid, token);
        this.unitIdx = unitIdx;
        this.name = name;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.unitIdx;
        hash = 31 * hash + Objects.hashCode(this.name);
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
        final RenameUnit other = (RenameUnit) obj;
        if (this.unitIdx != other.unitIdx) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RenameUnit{" + "unitIdx=" + unitIdx + ", name=" + name + '}';
    }
}
