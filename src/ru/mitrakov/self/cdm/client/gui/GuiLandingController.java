package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Tommy
 */
public final class GuiLandingController implements ScreenController {
    private final IGui gui;

    public GuiLandingController(IGui gui) {
        assert gui != null;
        this.gui = gui;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(id = "btn_play")
    public void onPlayClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("main");
    }
    
    @NiftyEventSubscriber(id = "btn_sign_in")
    public void onSignInClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("sign_in");
    }
    
    @NiftyEventSubscriber(id = "btn_sign_up")
    public void onSignUpClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("sign_up");
    }
    
    @NiftyEventSubscriber(id = "btn_settings")
    public void onSettingsClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("settings");
    }
    
    @NiftyEventSubscriber(id = "btn_quit")
    public void onQuitClick(String id, ButtonClickedEvent event) {
        gui.exit();
    }
}
