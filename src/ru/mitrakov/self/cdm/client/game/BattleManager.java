package ru.mitrakov.self.cdm.client.game;

import java.util.*;
import ru.mitrakov.self.cdm.client.engine.Engine;

/**
 *
 * @author Unit
 */
public final class BattleManager implements IBattleManager {
    
    private final Engine engine;
    private final List<Cell> cells = new LinkedList<>();
    private final List<Unit> myUnits = new LinkedList<>();
    private final List<Unit> enemyUnits = new LinkedList<>();
    
    private boolean newGame = false;
    private boolean aggressor = false;
    private boolean curAggressorTurn = true;
    private IBattle battle = null;

    public BattleManager(Engine engine) {
        assert engine != null;
        this.engine = engine;
    }

    @Override
    public void setAggressor(boolean aggressor) {
        this.aggressor = aggressor;
        newGame = true;
    }

    @Override
    public void updateCell(int idx, Cell cell) {
        if (battle != null)
            battle.updateCell(idx, cell);
        else cells.add(cell);
    }
    
    @Override
    public void updateMyUnitsCount(int value) {
        if (battle != null)
            battle.updateMyUnitsCount(value);
    }

    @Override
    public void updateEnemyUnitsCount(int value) {
        if (battle != null)
            battle.updateEnemyUnitsCount(value);
    }

    @Override
    public void updateMyUnit(int unitId, int x, int y, int hp, int state) {
        if (battle != null)
            battle.updateMyUnit(unitId, x, y, hp, state);
        else myUnits.add(new Unit(unitId, x, y, hp, state, true));
    }

    @Override
    public void updateEnemyUnit(int unitId, int x, int y, int hp, int state) {
        if (battle != null)
            battle.updateEnemyUnit(unitId, x, y, hp, state);
        else enemyUnits.add(new Unit(unitId, x, y, hp, state, false));
    }

    @Override
    public void updateWeapons(int unitId, Collection<? extends Weapon> weapons) {
        if (battle != null)
            battle.updateWeapons(unitId, weapons);
        else {
            assert myUnits.size() > 0;
            for (Unit unit : myUnits) {
                if (unit.unitId == unitId) {
                    unit.weapons.clear();
                    unit.weapons.addAll(0, weapons);
                }
            }
        }
    }
    
    @Override
    public void showAction(List<Integer> path) {
        if (battle != null)
            battle.showAction(path);
    }

    @Override
    public void setCurAggressorTurn(boolean value) {
        if (curAggressorTurn != value)
            if (battle != null)
                battle.newTurn(value);
        curAggressorTurn = value;
    }

    @Override
    public void setCurWeaponIdx(int index) {
        if (battle != null)
            battle.setCurWeaponIdx(index);
    }
    
    @Override
    public void destroyBattle() {
        assert battle != null;
        battle.destroy();
        
        // clean up everything
        cells.clear();
        myUnits.clear();
        enemyUnits.clear();
        battle = null;
    }

    @Override
    public void refresh() {
        if (newGame) {
            newGame = false;
            if (battle != null)
                destroyBattle();
            battle = new Battle(engine, aggressor, cells, myUnits, enemyUnits);
            battle.start();
        }
    }

    @Override
    public void setCurUnit(Unit unit) {
        if (battle != null)
            battle.setCurUnit(unit);
    }

    @Override
    public IBattle.BattleMode getBattleMode() {
        return battle != null ? battle.getMode() : IBattle.BattleMode.None;
    }

    @Override
    public Unit getCurUnit() {
        return battle != null ? battle.getCurUnit() : null;
    }

    @Override
    public Weapon getCurWeapon() {
        return battle != null ? battle.getCurWeapon() : null;
    }
}
