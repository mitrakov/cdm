package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import ru.mitrakov.self.cdm.client.Starter;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.json.commands.cmd.GetUserId;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class GuiFindController implements Controller {
    private final IStorage storage;
    private final INetwork network;
    
    private String curPopupId = "";
    private String nameToFind = "";
    
    public GuiFindController(){
        this(Starter.model.getStorage(), Starter.model.getNetwork());
    }

    public GuiFindController(IStorage storage, INetwork network) {
        assert storage != null && network != null;
        this.storage = storage;
        this.network = network;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        curPopupId = element.getId();
    }
    
    @Override
    public void onStartScreen() {}

    @Override
    public void init(Properties parameter, Attributes controlDefinitionAttributes) {}

    @Override
    public void onFocus(boolean getFocus) {}

    @Override
    public boolean inputEvent(NiftyInputEvent inputEvent) {return true;}
    
    @NiftyEventSubscriber(id = "popup_find_txt")
    public void onNameChanged(String id, TextFieldChangedEvent event) {
        nameToFind = event.getText();
    }
    
    @NiftyEventSubscriber(id = "popup_find_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        network.send(new GetUserId(network.getSid(), storage.getToken(), nameToFind));
        onCancelClick(id, event);
    }
    
    @NiftyEventSubscriber(id = "popup_find_cancel")
    public void onCancelClick(String id, ButtonClickedEvent event) {
        event.getButton().getElement().getNifty().closePopup(curPopupId);
    }
}
