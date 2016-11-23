package ru.mitrakov.self.cdm.client.engine;

import com.jme3.input.InputManager;
import ru.mitrakov.self.cdm.client.game.*;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.gui.IGui;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Unit
 */
public final class EngineInputController {
    
    private final Engine engine;
    private final IGui gui;
    private final IStorage storage;
    private final INetwork network;
    private final IBattleManager battleManager;

    public EngineInputController(Engine engine, IGui gui, IStorage storage, INetwork network, IBattleManager battleManager) {
        assert gui != null && engine != null && storage != null && network != null && battleManager != null;
        this.gui = gui;
        this.engine = engine;
        this.storage = storage;
        this.network = network;
        this.battleManager = battleManager;
    }
    
    public void init() {
        InputManager manager = engine.getInputManager(); assert manager != null;
        manager.addMapping("Esc", new KeyTrigger(KeyInput.KEY_ESCAPE));
        manager.addListener(escapeListener, "Esc");
        manager.addMapping("Debug", new KeyTrigger(KeyInput.KEY_L));
        manager.addListener(debugListener, "Debug");
        manager.addMapping("OnClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        manager.addListener(onClickListener, "OnClick");
    }
    
    private final InputListener escapeListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                if (battleManager.getBattleMode() != IBattle.BattleMode.None)
                    gui.showGiveUp();
            }
        }
    };
    
    private final InputListener debugListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                //battleManager.debug();
            }
        }
    };
    
    private final InputListener onClickListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                try {
                    switch (battleManager.getBattleMode()) {
                        case MyTurnChooseUnit:
                            chooseUnit();
                            break;
                        case MyTurn:
                            moveUnit();
                            break;
                        case MyTurnReadyToFire:
                            attackUnit();
                            break;
                        default:
                    }
                } catch (Exception ex) {
                    Logger.getLogger(EngineInputController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    
    protected void chooseUnit() {
        SceneState state = engine.getStateManager().getState(SceneState.class);
        if (state != null) {
            Unit unit = state.getUnit();
            if (unit != null)
                battleManager.setCurUnit(unit);
        }
    }
    
    protected void moveUnit() throws IOException {
        SceneState state = engine.getStateManager().getState(SceneState.class);
        if (state != null) {
            Cell cell = state.getCell();
            if (cell != null) {
                Unit unit = battleManager.getCurUnit();
                if (unit != null) {
                    if (cell.x() == unit.x && cell.y() == unit.y)
                        gui.showWeapon(unit.weapons);
                    else network.send(new Move(network.getSid(), storage.getToken(), unit.unitId, cell.x(), cell.y()));
                }
            }
        }
    }
    
    protected void attackUnit() throws IOException {
        SceneState state = engine.getStateManager().getState(SceneState.class);
        if (state != null) {
            Unit unit = battleManager.getCurUnit();
            Weapon weapon = battleManager.getCurWeapon();
            if (unit != null && weapon != null) {
                int direction = state.getCurrentDirection();
                network.send(new Strike(network.getSid(), storage.getToken(), unit.unitId, weapon.type.ordinal(), direction, 32, 33, 1));
                battleManager.setCurUnit(unit); // return to MyTurn mode
            }
        }
    }
}
