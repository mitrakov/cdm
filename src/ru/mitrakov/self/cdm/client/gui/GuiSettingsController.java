package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.*;
import de.lessvoid.nifty.screen.*;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.game.IStorage;

/**
 *
 * @author Tommy
 */
public final class GuiSettingsController implements ScreenController, KeyInputHandler {
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
    
    @Override
    public boolean keyEvent(NiftyInputEvent event) {
        // ....
        if (event == NiftyInputEvent.Escape)
            onBackClick("", null);
        return true;
    }
    
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
