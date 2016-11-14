package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import java.util.Collection;
import javax.swing.JOptionPane;
import ru.mitrakov.self.cdm.client.Model;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.game.Weapon;

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
    public void showInvite(int sid) {
        String s = String.format("Игрок %d желает сразиться с Вами\nПринять?", sid);
        if (engine.getContext().isCreated()) {
            engine.hold();
            storage.setEnemySid(sid);
            // @mitrakov: don't use findNiftyControl("invite_txt", Label.class); it somehow returns null
            Element txt = engine.getNifty().getScreen("invite").findElementByName("invite_txt"); assert txt != null;
            txt.getRenderer(TextRenderer.class).setText(s);        
            engine.getNifty().gotoScreen("invite");
        } else if (JOptionPane.showConfirmDialog(null, s, "Coup de Main", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
            Model.needRestart = true;
        }
    }
    
    @Override
    public void showReject(int sid) {
        engine.hold();
        Element popup = engine.getNifty().createPopup("popup_reject");
        Label label = popup.findNiftyControl("lbl_reject", Label.class); assert label != null;
        label.setText(String.format("Игрок %d отказался играть с Вами", sid));
        engine.getNifty().showPopup(engine.getNifty().getCurrentScreen(), popup.getId(), null);
    }
    
    @Override
    public void showVictory(String winnerName) {
        engine.hold();
        Element popup = engine.getNifty().createPopup("popup_victory");
        Label label = popup.findNiftyControl("lbl_victory", Label.class); assert label != null;
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
        engine.getNifty().getCurrentScreen().startScreen();
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
