package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import ru.mitrakov.self.cdm.client.Starter;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public class GuiPopupController implements Controller {
    protected final IStorage storage;
    protected final INetwork network;
    
    protected String curPopupId = "";

    public GuiPopupController() {
        this(Starter.model.getStorage(), Starter.model.getNetwork());
    }

    public GuiPopupController(IStorage storage, INetwork network) {
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
    
    @NiftyEventSubscriber(id = "popup_close")
    public void onCloseClick(String id, ButtonClickedEvent event) {
        event.getButton().getElement().getNifty().closePopup(curPopupId);
    }
    
    @NiftyEventSubscriber(id = "popup_goto_main")
    public void onGotoMainClick(String id, ButtonClickedEvent event) {
        event.getButton().getElement().getNifty().gotoScreen("main");
        onCloseClick(id, event);
    }
}
