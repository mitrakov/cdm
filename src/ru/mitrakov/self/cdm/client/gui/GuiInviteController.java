package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.NiftyEventSubscriber;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;

/**
 *
 * @author Tommy
 */
public final class GuiInviteController extends GuiPopupController implements Controller {
    
    @NiftyEventSubscriber(id = "btn_accept")
    public void onAcceptClick(String id, ButtonClickedEvent event) {
        network.send(new Accept(network.getSid(), storage.getToken(), storage.getEnemySid()));
        onCloseClick(id, null);
    }
    
    @NiftyEventSubscriber(id = "btn_reject")
    public void onRejectClick(String id, ButtonClickedEvent event) {
        network.send(new Reject(network.getSid(), storage.getToken(), storage.getEnemySid()));
        onCloseClick(id, null);
    }
}
