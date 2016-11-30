package ru.mitrakov.self.cdm.client.game;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Unit
 */
public interface IBattle {
    public static enum BattleMode {None, MyTurn, EnemyTurn, MyTurnChooseUnit, MyTurnReadyToFire}
    public static enum ActionType {Move, Fist, Bullet, Shell, Outbirst}
    
    public void start();
    public void newTurn(boolean curAggressorTurn);
    public void setCurWeaponIdx(int index);
    public void setCurUnit(Unit unit);
    public void updateCell(int idx, Cell cell);
    public void updateMyUnitsCount(int count);
    public void updateMyUnit(int unitId, int x, int y, int hp, int state);
    public void updateEnemyUnitsCount(int count);
    public void updateEnemyUnit(int unitId, int x, int y, int hp, int state);
    public void updateWeapons(int unitId, Collection<? extends Weapon> weapons);
    public void showAction(ActionType actionType, List<Integer> path);
    public void destroy();
    
    public Unit             getCurUnit();
    public Weapon           getCurWeapon();
    public boolean          isAggressor();
    public BattleMode       getMode();
    public Collection<Cell> getCells();
    public Collection<Unit> getMyUnits();
    public Collection<Unit> getEnemyUnits();
}
