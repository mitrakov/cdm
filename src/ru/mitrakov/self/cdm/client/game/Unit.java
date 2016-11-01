package ru.mitrakov.self.cdm.client.game;

import com.jme3.export.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tommy
 */
public class Unit implements Savable {
    public final int unitId;
    public final boolean mine;
    public int x;
    public int y;
    public int hp;
    public int state;
    public final List<Weapon> weapons = new ArrayList<>();

    public Unit(int idx, int x, int y, int hp, int state, boolean mine) {
        this.unitId = idx;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.state = state;
        this.mine = mine;
    }
    
    // GENERATED CODE
    @Override
    public void write(JmeExporter ex) throws IOException {}

    @Override
    public void read(JmeImporter im) throws IOException {}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.unitId;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        hash = 67 * hash + this.hp;
        hash = 67 * hash + this.state;
        hash = 67 * hash + (this.mine ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.weapons);
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
        final Unit other = (Unit) obj;
        if (this.unitId != other.unitId) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.hp != other.hp) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        if (this.mine != other.mine) {
            return false;
        }
        if (!Objects.equals(this.weapons, other.weapons)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit{" + "unitId=" + unitId + ", x=" + x + ", y=" + y + ", hp=" + hp + ", state=" + state + ", mine=" + mine + ", weapons=" + weapons + '}';
    }
}
