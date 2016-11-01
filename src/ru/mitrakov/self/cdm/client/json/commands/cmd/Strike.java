package ru.mitrakov.self.cdm.client.json.commands.cmd;

import ru.mitrakov.self.cdm.client.json.commands.CmdRequest;

/**
 * @author Tommy
 */
public class Strike extends CmdRequest {
    public final int unit;
    public final int weapon;
    public final int direction;
    public final int angle;
    public final int strength;
    public final int spin;

    public Strike(int sid, String token, int unit, int weapon, int direction, int angle, int strength, int spin) {
        super("str", sid, token);
        this.unit = unit;
        this.weapon = weapon;
        this.direction = direction;
        this.angle = angle;
        this.strength = strength;
        this.spin = spin;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.unit;
        hash = 41 * hash + this.weapon;
        hash = 41 * hash + this.direction;
        hash = 41 * hash + this.angle;
        hash = 41 * hash + this.strength;
        hash = 41 * hash + this.spin;
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
        final Strike other = (Strike) obj;
        if (this.unit != other.unit) {
            return false;
        }
        if (this.weapon != other.weapon) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        if (this.angle != other.angle) {
            return false;
        }
        if (this.strength != other.strength) {
            return false;
        }
        if (this.spin != other.spin) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Strike{" + "unitIdx=" + unit + ", weapon=" + weapon + ", direction=" + direction + ", angle=" + angle + ", strength=" + strength + ", spin=" + spin + '}';
    }
}
