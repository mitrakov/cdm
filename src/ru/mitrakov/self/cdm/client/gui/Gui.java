package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Collection;
import javax.swing.JOptionPane;
import ru.mitrakov.self.cdm.client.Model;
import ru.mitrakov.self.cdm.client.Starter;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.game.Weapon;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;
import ru.mitrakov.self.cdm.client.json.commands.cmd.Invite;
import ru.mitrakov.self.cdm.client.json.commands.cmd.Reject;

/**
 *
 * @author Tommy
 */
public final class Gui implements IGui {
    private final Engine engine;
    private final IStorage storage;
    
    public Gui(Engine engine, IStorage storage) {
        assert engine != null && storage != null;
        this.engine = engine;
        this.storage = storage;
    }

    @Override
    public void showInvite(Invite cmd) {
        if (engine.getContext().isCreated()) {
            engine.hold();
            storage.setEnemySid(cmd.userId);
            Element popup = engine.getNifty().createPopup("invite");
            Label label = popup.findNiftyControl("label_invite", Label.class); assert label != null;
            label.setText(String.format("Игрок %d желает сразиться с Вами\nПринять?", cmd.userId));
            engine.getNifty().showPopup(engine.getNifty().getCurrentScreen(), popup.getId(), null);
        } else {
            String s = String.format("Вам пришло новое приглашение от игрока %d", cmd.userId);
            if (JOptionPane.showConfirmDialog(null, s, "Coup de Main", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                Starter.restartModel(cmd);
        }
    }
    
    @Override
    public void showReject(Reject cmd) {
        engine.hold();
        Element popup = engine.getNifty().createPopup("reject");
        Label label = popup.findNiftyControl("label_reject", Label.class); assert label != null;
        label.setText(String.format("Игрок %d отказался играть с Вами", cmd.enemySid));
        engine.getNifty().showPopup(engine.getNifty().getCurrentScreen(), popup.getId(), null);
    }
    
    @Override
    public void showVictory(String winnerName) {
        engine.hold();
        Element popup = engine.getNifty().createPopup("victory");
        Label label = popup.findNiftyControl("label_victory", Label.class); assert label != null;
        label.setText(String.format("Игрок %s одержал победу!", winnerName));
        engine.getNifty().showPopup(engine.getNifty().getCurrentScreen(), popup.getId(), null);
    }

    @Override
    public void showWeapon(Collection<? extends Weapon> weapons) {
        engine.hold();
        ListBox listbox = engine.getNifty().getScreen("weapon").findNiftyControl("lst_weapon", ListBox.class); assert listbox != null;
        listbox.clear();
        for (Weapon weapon : weapons)
            listbox.addItem(String.format("%s (%d)", weapon.type, weapon.count));
        engine.getNifty().gotoScreen("weapon");
    }

    @Override
    public void hide() {
        engine.resume();
        engine.getNifty().gotoScreen("empty");
    }
    
    @Override
    public void update() {
        // @mitrakov: earlier I use "engine.getNifty().getCurrentScreen().startScreen()"
        // but it appeared to be erroneous: Nifty throws a lot of warnings and exceptions
        ScreenController controller = engine.getNifty().getCurrentScreen().getScreenController();
        if (controller instanceof GuiMainController)
            ((GuiMainController)controller).rebuildUnits(engine.getNifty());
    }
    
    @Override
    public void gotoScreen(String screen) {
        engine.hold();
        engine.getNifty().gotoScreen(screen);
    }
    
    @Override
    public void gotoLandingScreen() {
        engine.hold();
        gotoScreen(storage.getToken().isEmpty() ? "landing" : "landing_auth");
    }
    
    @Override
    public void exit() {
        engine.stop();
    }
}
