package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.game.IStorage;

/**
 *
 * @author Tommy
 */
public final class GuiSettingsController implements ScreenController {
    private final IGui gui;
    private final Engine engine;
    private final IStorage storage;

    public GuiSettingsController(IGui gui, Engine engine, IStorage storage) {
        assert gui != null && engine != null && storage != null;
        this.gui = gui;
        this.engine = engine;
        this.storage = storage;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(id = "btn_logout")
    public void onLogoutClick(String id, ButtonClickedEvent event) {
        engine.saveLoginPassword(null, null);
        storage.setToken("");
        onBackClick(id, event);
    }
    
    @NiftyEventSubscriber(id = "btn_back")
    public void onBackClick(String id, ButtonClickedEvent event) {
        gui.gotoLandingScreen();
    }
}
