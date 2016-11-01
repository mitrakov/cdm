package ru.mitrakov.self.cdm.client.game;

import com.jme3.export.*;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Tommy
 */
public class Cell implements Savable {
    public static enum CellType {
        CellNone, CellPlain, CellSand, CellWater, CellHill, CellPit, CellStone,
        CellTree, CellCorpse, CellBridge, CellFog
    };
    
    public CellType cellType;
    public int idx;

    public Cell(CellType cellType, int idx) {
        this.cellType = cellType;
        this.idx = idx;
    }
    
    public int x() {
        return idx % Battle.WIDTH;
    }
    
    public int y() {
        return idx / Battle.WIDTH;
    }

    // GENERATED CODE
    @Override
    public void write(JmeExporter ex) throws IOException {}

    @Override
    public void read(JmeImporter im) throws IOException {}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.cellType);
        hash = 13 * hash + this.idx;
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
        final Cell other = (Cell) obj;
        if (this.cellType != other.cellType) {
            return false;
        }
        if (this.idx != other.idx) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cell{" + "cellType=" + cellType + ", idx=" + idx + '}';
    }
}
