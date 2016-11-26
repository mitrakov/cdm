package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class GuiGiveUpController implements ScreenController {

    private final IGui gui;
    private final INetwork network;
    private final IStorage storage;

    public GuiGiveUpController(IGui gui, INetwork network, IStorage storage) {
        this.gui = gui;
        this.network = network;
        this.storage = storage;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(pattern = ".*")   // event will be handled only once!
    public void onEscPressed(String id, NiftyInputEvent event) {
        if (event == NiftyInputEvent.Escape)
            onNoClick(id, null);
    }
    
    @NiftyEventSubscriber(id = "btn_yes")
    public void onYesClick(String id, ButtonClickedEvent event) {
        gui.hide();  // don't forget to hide gui first!
        network.send(new GiveUp(network.getSid(), storage.getToken()));
    }
    
    @NiftyEventSubscriber(id = "btn_no")
    public void onNoClick(String id, ButtonClickedEvent event) {
        gui.hide();
    }
}
