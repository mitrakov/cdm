package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.*;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.json.commands.cmd.GetRandUserId;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class GuiMainController implements ScreenController {
    private final IGui gui;
    private final INetwork network;
    private final IStorage storage;
    
    private static enum Action {
        InviteByName, InviteRandom, Back, RenameUnit
    };
    
    protected List<Action> actions = new ArrayList<Action>() {{
        add(Action.InviteByName);
        add(Action.InviteRandom);
        add(Action.Back);
    }};
    
    protected Map<Action, String> names = new TreeMap<Action, String>() {{
        put(Action.InviteByName, "Играть");
        put(Action.InviteRandom, "Случ. бой");
        put(Action.Back, "Назад");
        put(Action.RenameUnit, "Переимен.");
    }};
    
    protected Nifty nifty;

    public GuiMainController(IGui gui, INetwork network, IStorage storage, Engine engine) {
        assert gui != null && network != null && engine != null;
        this.gui = gui;
        this.storage = storage;
        this.network = network;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
    }

    @Override
    public void onStartScreen() {
        rebuildMenu(nifty);
        rebuildUnits(nifty);
    }

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(pattern = "btn_main.*")
    public void onBtnClicked(String id, ButtonClickedEvent event) {
        for (Integer i : new int[] {0, 1, 2, 3, 4, 5}) {
            if (id.endsWith(i.toString())) {
                Action action = actions.get(i); assert action != null;
                switch (action) {
                    case InviteByName: {
                        Element popup = nifty.createPopup("popup_find");
                        nifty.showPopup(nifty.getCurrentScreen(), popup.getId(), null);
                        break;
                    }
                    case InviteRandom:
                        network.send(new GetRandUserId(network.getSid(), storage.getToken()));
                        break;
                    case Back:
                        gui.gotoLandingScreen();
                        break;
                    case RenameUnit: {
                        Element popup = nifty.createPopup("popup_rename_unit");
                        TextField txt = popup.findNiftyControl("popup_txt_rename", TextField.class); assert txt != null;
                        txt.setText(storage.getUnitNames().get(storage.getCurUserUnit()));
                        nifty.showPopup(nifty.getCurrentScreen(), popup.getId(), null);
                        break;
                    }
                    default:
                }
            }
        }
    }
    
    @NiftyEventSubscriber(pattern = "pan_unit.*")
    public void onUnitClicked(String id, NiftyMousePrimaryClickedEvent event) {
        if (!actions.contains(Action.RenameUnit))
            actions.add(0, Action.RenameUnit);
        int x = Integer.parseInt(id.substring(id.length()-1));
        storage.setCurUserUnit(x);
        rebuildMenu(event.getElement().getNifty());
    }
    
    protected void rebuildMenu(Nifty nifty) {
        assert nifty != null;
        for (int i : new int[] {0, 1, 2, 3, 4, 5}) {
            String id = String.format("btn_main%d", i);
            Button btn = nifty.getCurrentScreen().findNiftyControl(id, Button.class); assert btn != null;
            if (i < actions.size()) {
                Action action = actions.get(i); assert action != null;
                btn.getElement().setVisible(true);
                btn.setText(names.get(action));
            } else btn.getElement().setVisible(false);
        }
    }
    
    protected void rebuildUnits(Nifty nifty) {
        List<String> unitNames = storage.getUnitNames();
        for (int i = 0; i < Math.min(unitNames.size(), 5); i++) {
            String id = String.format("label_unit%d", i);
            Label label = nifty.getCurrentScreen().findNiftyControl(id, Label.class); assert label != null;
            label.setText(unitNames.get(i));
        }
    }
}
