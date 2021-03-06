package ru.mitrakov.self.cdm.client.handlers;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.UnsupportedEncodingException;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;
import ru.mitrakov.self.cdm.client.json.commands.cmd.ResponseState;
import ru.mitrakov.self.cdm.client.json.commands.cmd.ResponseMove;
import java.util.*;
import ru.mitrakov.self.cdm.client.Utils;
import ru.mitrakov.self.cdm.client.game.Cell;
import ru.mitrakov.self.cdm.client.game.Cell.CellType;
import ru.mitrakov.self.cdm.client.game.IBattle.ActionType;
import ru.mitrakov.self.cdm.client.game.IBattleManager;
import ru.mitrakov.self.cdm.client.game.Weapon;
import ru.mitrakov.self.cdm.client.gui.IGui;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;

/**
 *
 * @author Tommy
 */
public final class BattleHandler extends Handler {
    private final IGui gui;
    private final IBattleManager battleManager;

    public BattleHandler(Handler parent, IGui gui, IBattleManager battleManager) {
        super(parent);
        assert gui != null && battleManager != null;
        this.gui = gui;
        this.battleManager = battleManager;
    }

    @Override
    public void handle(Cmd cmd) {
        if (cmd instanceof ResponseState)
            handleNewState(((ResponseState)cmd).state);
        else if (cmd instanceof ResponseMove) {
            handleNewState(((ResponseMove)cmd).state);
        } else if (cmd instanceof ResponseStrike) {
            ActionType[] types = ActionType.values();
            for (List<Integer> lst : ((ResponseStrike)cmd).arrays) {
                assert lst.size() > 0;
                int action = lst.get(0);
                ActionType actionType = action < types.length ? types[action] : ActionType.None;
                battleManager.showAction(actionType, lst.subList(1, lst.size()));
            }
            handleNewState(((ResponseStrike)cmd).state);
        } else if (cmd instanceof Reject) {
            gui.showReject((Reject)cmd);
        } else if (cmd instanceof ResponseFinished) {
            battleManager.destroyBattle();
            gui.showVictory(((ResponseFinished)cmd).winnerName);
        }
        else super.handle(cmd);
    }
    
    protected void handleNewState(Collection<? extends String> states) {
        gui.hide();
        for (String s : states) {
            switch (s.charAt(0)) {
                case 'A': 
                    battleManager.setAggressor(s.charAt(1) == 'A');
                    break;
                case 'B':
                    char[] cells = s.substring(1).toCharArray();
                    for (int i = 0; i < cells.length; i++) {
                        char c = cells[i];
                        switch(c) {
                            case 'B': battleManager.updateCell(i, new Cell(CellType.CellPlain, i));  break;
                            case 'C': battleManager.updateCell(i, new Cell(CellType.CellSand, i));   break;
                            case 'D': battleManager.updateCell(i, new Cell(CellType.CellWater, i));  break;
                            case 'E': battleManager.updateCell(i, new Cell(CellType.CellHill, i));   break;
                            case 'F': battleManager.updateCell(i, new Cell(CellType.CellPit, i));    break;
                            case 'G': battleManager.updateCell(i, new Cell(CellType.CellStone, i));  break;
                            case 'H': battleManager.updateCell(i, new Cell(CellType.CellTree, i));   break;
                            case 'I': battleManager.updateCell(i, new Cell(CellType.CellCorpse, i)); break;
                            case 'J': battleManager.updateCell(i, new Cell(CellType.CellBridge, i)); break;
                            case 'K': battleManager.updateCell(i, new Cell(CellType.CellFog, i));    break;
                            default : battleManager.updateCell(i, new Cell(CellType.CellNone, i));   break;
                        }
                    }
                    break;
                case 'C': {
                    List<List<Integer>> zipped = Utils.grouped(Utils.fromBase64(s.substring(1)), 4);
                    battleManager.updateMyUnitsCount(zipped.size());
                    for (int i=0; i < zipped.size(); i++)
                        battleManager.updateMyUnit(i, zipped.get(i).get(0), zipped.get(i).get(1), zipped.get(i).get(2), zipped.get(i).get(3));
                    break;
                }
                case 'D': {
                    List<List<Integer>> zipped = Utils.grouped(Utils.fromBase64(s.substring(1)), 4);
                    battleManager.updateEnemyUnitsCount(zipped.size());
                    for (int i=0; i < zipped.size(); i++)
                        battleManager.updateEnemyUnit(i, zipped.get(i).get(0), zipped.get(i).get(1), zipped.get(i).get(2), zipped.get(i).get(3));
                    break;
                }
                case 'E': {
                    try {
                        String[] names = new String(Base64.decode(s.substring(1)), "UTF-8").split("\\|");
                        for (int i=0; i < names.length; i++)
                            battleManager.updateMyUnit(i, names[i]);
                    } catch (Base64DecodingException | UnsupportedEncodingException ignored) {}
                    break;
                }
                case 'F': {
                    try {
                        String[] names = new String(Base64.decode(s.substring(1)), "UTF-8").split("\\|");
                        for (int i=0; i < names.length; i++)
                            battleManager.updateEnemyUnit(i, names[i]);
                    } catch (Base64DecodingException | UnsupportedEncodingException ignored) {}
                    break;
                }
                case 'G': {
                    List<Integer> values = Utils.fromBase64(s.substring(1));
                    for (int i=0, unitIdx=0; i<values.size(); unitIdx++) {
                        int count = values.get(i++);
                        List<Weapon> lst = new LinkedList<>();
                        for (int j = 0; j < count; j++) {
                            int type = values.get(i++);
                            int size = values.get(i++);
                            lst.add(new Weapon(type, size));
                        }
                        battleManager.updateWeapons(unitIdx, lst);
                    }
                    break;
                }
                case 'L': 
                    battleManager.setCurAggressorTurn(s.charAt(1) == 'A');
                    break;
                default:
            }
        }
        battleManager.refresh();
    }
}
