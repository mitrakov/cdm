package ru.mitrakov.self.cdm.client.game;

import java.util.*;
import ru.mitrakov.self.cdm.client.game.IBattle.ActionType;

/**
 *
 * @author Tommy
 */
public interface IBattleManager {
    public void setAggressor(boolean aggressor);
    public void updateCell(int idx, Cell cell);
    public void updateMyUnitsCount(int value);
    public void updateEnemyUnitsCount(int value);
    public void updateMyUnit(int unitId, int x, int y, int hp, int state);
    public void updateEnemyUnit(int unitId, int x, int y, int hp, int state);
    public void updateWeapons(int unitId, Collection<? extends Weapon> weapons);
    public void showAction(ActionType actionType, List<Integer> path);
    public void setCurAggressorTurn(boolean value);
    public void setCurWeaponIdx(int index);
    public void setCurUnit(Unit unit);
    public void destroyBattle();
    public void refresh();
    public IBattle.BattleMode getBattleMode();
    public Unit getCurUnit();
    public Weapon getCurWeapon();
}
