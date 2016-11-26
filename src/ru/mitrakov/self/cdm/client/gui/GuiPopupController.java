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
    protected Nifty nifty;

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
        this.nifty = nifty;
        this.curPopupId = element.getId();
    }
    
    @Override
    public void onStartScreen() {}

    @Override
    public void init(Properties parameter, Attributes controlDefinitionAttributes) {}

    @Override
    public void onFocus(boolean getFocus) {}

    @Override
    public boolean inputEvent(NiftyInputEvent inputEvent) {return true;}
    
    @NiftyEventSubscriber(pattern = "txt_.*")   // event will be handled only once!
    public void onKeyPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.SubmitText)
            onOkClick(id, null);
    }
    
    @NiftyEventSubscriber(pattern = ".*")   // event will be handled only once!
    public void onEscPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.Escape)
            onCloseClick(id, null);
    }
    
    // should be overriden by subclasses
    public void onOkClick(String id, ButtonClickedEvent event) {
        onCloseClick(id, null);
    }
    
    @NiftyEventSubscriber(id = "btn_close")
    public void onCloseClick(String id, ButtonClickedEvent event) {
        nifty.closePopup(curPopupId);
    }
    
    @NiftyEventSubscriber(id = "btn_goto_main")
    public void onGotoMainClick(String id, ButtonClickedEvent event) {
        nifty.gotoScreen("main");
        onCloseClick(id, event);
    }
}
