package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.*;
import de.lessvoid.nifty.screen.*;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.input.NiftyInputEvent;
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

    public GuiSignUpController(IGui gui, INetwork network, Engine engine) {
        assert gui != null && network != null && engine != null;
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
    
    @NiftyEventSubscriber(pattern = "txt_.*")
    public void onKeyPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.SubmitText)
            onOkClick(id, null);
    }
    
    @NiftyEventSubscriber(pattern = ".*")   // event will be handled only once!
    public void onEscPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.Escape)
            onCancelClick(id, null);
    }
    
    @NiftyEventSubscriber(id = "btn_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        Screen screen = engine.getNifty().getCurrentScreen();
        TextField txtLogin = screen.findNiftyControl("txt_login", TextField.class); assert txtLogin != null;
        TextField txtPass = screen.findNiftyControl("txt_pass", TextField.class); assert txtPass != null;
        TextField txtEmail = screen.findNiftyControl("txt_email", TextField.class); assert txtEmail != null;
        String login = txtLogin.getRealText();
        String password = txtPass.getRealText();
        String email = txtPass.getRealText();
        engine.saveLoginPassword(login, password);
        network.send(new SignUp(login, password, email));
    }
    
    @NiftyEventSubscriber(id = "btn_cancel")
    public void onCancelClick(String id, ButtonClickedEvent event) {
        gui.gotoScreen("landing");
    }
}
