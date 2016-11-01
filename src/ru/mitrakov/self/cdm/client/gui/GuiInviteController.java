package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.json.commands.cmd.Accept;
import ru.mitrakov.self.cdm.client.json.commands.cmd.Reject;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class GuiInviteController implements ScreenController {
    private final IGui gui;
    private final IStorage storage;
    private final INetwork network;

    public GuiInviteController(IGui gui, IStorage storage, INetwork network) {
        assert gui != null && storage != null && network != null;
        this.gui = gui;
        this.storage = storage;
        this.network = network;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @NiftyEventSubscriber(id = "accept")
    public void onAcceptClick(String id, ButtonClickedEvent event) {
        network.send(new Accept(network.getSid(), storage.getToken(), storage.getEnemySid()));
        gui.hide();
    }
    
    @NiftyEventSubscriber(id = "reject")
    public void onRejectClick(String id, ButtonClickedEvent event) {
        network.send(new Reject(network.getSid(), storage.getToken(), storage.getEnemySid()));
        gui.hide();
    }
}
