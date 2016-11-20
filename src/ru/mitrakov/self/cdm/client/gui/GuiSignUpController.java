package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import ru.mitrakov.self.cdm.client.engine.Engine;
import ru.mitrakov.self.cdm.client.json.commands.cmd.SignUp;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class GuiSignUpController implements ScreenController {
    private final IGui gui;
    private final Engine engine;
    private final INetwork network;
    
    private String email = "";
    private String login = "";
    private String password = "";

    public GuiSignUpController(IGui gui, INetwork network, Engine engine) {
        assert gui != null && network != null;
        this.gui = gui;
        this.engine = engine;
        this.network = network;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(id = "txt_email")
    public void onEmailChanged(String id, TextFieldChangedEvent event) {
        email = event.getText();
    }
    
    @NiftyEventSubscriber(id = "txt_login")
    public void onLoginChanged(String id, TextFieldChangedEvent event) {
        login = event.getText();
    }
    
    @NiftyEventSubscriber(id = "txt_pass")
    public void onPasswordChanged(String id, TextFieldChangedEvent event) {
        password = event.getText();
    }
    
    @NiftyEventSubscriber(pattern = "txt_.*")
    public void onKeyPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.SubmitText)
            onOkClick(id, null);
    }
    
    @NiftyEventSubscriber(id = "btn_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        engine.saveLoginPassword(login, password);
        network.send(new SignUp(login, password, email));
    }
    
    @NiftyEventSubscriber(id = "btn_cancel")
    public void onCancelClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("landing");
    }
}
