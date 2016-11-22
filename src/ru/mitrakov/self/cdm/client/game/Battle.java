package ru.mitrakov.self.cdm.client.game;

import ru.mitrakov.self.cdm.client.engine.SceneState;
import ru.mitrakov.self.cdm.client.engine.Engine;
import java.util.*;

/**
 *
 * @author Unit
 */
public final class Battle implements IBattle {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 12;
    
    private final Engine engine;
    private final boolean aggressor;
    private final List<Cell> cells;
    private final List<Unit> myUnits;
    private final List<Unit> enemyUnits;
    
    private SceneState sceneState;
    private BattleMode mode;
    private Weapon curWeapon;
    private Unit curUnit;

    public Battle(Engine engine, boolean aggressor, Collection<? extends Cell> cells, 
            Collection<? extends Unit> myUnits, Collection<? extends Unit> enemyUnits)
    {
        assert engine != null;
        this.engine = engine;
        this.aggressor = aggressor;
        this.mode = aggressor ? BattleMode.MyTurnChooseUnit : BattleMode.EnemyTurn;
        this.cells = new ArrayList<>(cells);
        this.myUnits = new ArrayList<>(myUnits);
        this.enemyUnits = new ArrayList<>(enemyUnits);
    }

    @Override
    public void start() {
        sceneState = new SceneState(this);
        engine.getStateManager().attach(sceneState);
    }

    @Override
    public void newTurn(boolean curAggressorTurn) {
        check();
        if (aggressor && curAggressorTurn || !aggressor && !curAggressorTurn)
             mode = BattleMode.MyTurnChooseUnit;
        else mode = BattleMode.EnemyTurn;
        sceneState.moveCameraOnStartPosition(curAggressorTurn);
    }

    @Override
    public void setCurWeaponIdx(int index) {
        check();
        assert curUnit != null;
        if (0 <= index && index < curUnit.weapons.size()) {
            if (mode == BattleMode.MyTurn) {
                mode = BattleMode.MyTurnReadyToFire;
                curWeapon = curUnit.weapons.get(index);
                sceneState.moveCameraOnFirePosition(curUnit, aggressor);
            }
        }
    }
    
    @Override
    public void setCurUnit(Unit unit) {
        check();
        if (mode == BattleMode.MyTurnChooseUnit || mode == BattleMode.MyTurnReadyToFire) {
            mode = BattleMode.MyTurn;
            curUnit = unit;
            sceneState.moveCameraOnUnit(unit, aggressor);
        }
    }

    @Override
    public void updateCell(int idx, Cell cell) {
        check();
        assert 0 <= idx && idx < cells.size();
        cells.set(idx, cell);
    }
    
    @Override
    public void updateMyUnitsCount(int count) {
        assert count >= 0;
        while (myUnits.size() > count)
            myUnits.remove(myUnits.size()-1);
    }

    @Override
    public void updateEnemyUnitsCount(int count) {
        assert count >= 0;
        while (enemyUnits.size() > count)
            enemyUnits.remove(enemyUnits.size()-1);
    }

    @Override
    public void updateMyUnit(int unitId, int x, int y, int hp, int state) {
        check();
        for (Unit unit : myUnits) {
            if (unit.unitId == unitId) {
                // updating unit info
                unit.x = x;
                unit.y = y;
                unit.hp = hp;
                unit.state = state;
                // move camera on current unit
                if (unit == curUnit && mode == BattleMode.MyTurn)
                    sceneState.moveCameraOnUnit(unit, aggressor);
            }
        }
    }

    @Override
    public void updateEnemyUnit(int unitId, int x, int y, int hp, int state) {
        check();
        for (Unit unit : enemyUnits) {
            if (unit.unitId == unitId) {
                unit.x = x;
                unit.y = y;
                unit.hp = hp;
                unit.state = state;
            }
        }
    }

    @Override
    public void updateWeapons(int unitId, Collection<? extends Weapon> weapons) {
        for (Unit unit : myUnits) {
            if (unit.unitId == unitId) {
                unit.weapons.clear();
                unit.weapons.addAll(0, weapons);
            }
        }
    }

    @Override
    public void destroy() {
        engine.getStateManager().detach(sceneState);
    }
    
    @Override
    public Unit getCurUnit() {
        return curUnit;
    }

    @Override
    public Weapon getCurWeapon() {
        return curWeapon;
    }

    @Override
    public BattleMode getMode() {
        return mode;
    }
    
    private void check() {
        if (sceneState == null)
            throw new RuntimeException("Call battle.start() before manipulating with a battle");
    }

    @Override
    public boolean isAggressor() {
        return aggressor;
    }

    @Override
    public Collection<Cell> getCells() {
        return cells;
    }

    @Override
    public Collection<Unit> getMyUnits() {
        return myUnits;
    }

    @Override
    public Collection<Unit> getEnemyUnits() {
        return enemyUnits;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Battle FINALIZED!!!");
        super.finalize();
    }
    
    
    
}
