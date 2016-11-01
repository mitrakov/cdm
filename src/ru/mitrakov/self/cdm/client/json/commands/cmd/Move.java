package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 *
 * @author Tommy
 */
public class Move extends CmdRequest {
    public final int unitIdx;
    public final int x;
    public final int y;

    public Move(int sid, String token, int unitIdx, int x, int y) {
        super("mov", sid, token);
        this.unitIdx = unitIdx;
        this.x = x;
        this.y = y;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.unitIdx;
        hash = 17 * hash + this.x;
        hash = 17 * hash + this.y;
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
        final Move other = (Move) obj;
        if (this.unitIdx != other.unitIdx) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
