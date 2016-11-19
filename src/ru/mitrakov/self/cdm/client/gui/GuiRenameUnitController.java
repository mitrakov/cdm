package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ru.mitrakov.self.cdm.client.json.commands.cmd.GetUnits;
import ru.mitrakov.self.cdm.client.json.commands.cmd.RenameUnit;

/**
 *
 * @author Tommy
 */
public final class GuiRenameUnitController extends GuiPopupController implements Controller {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    @Override
    @NiftyEventSubscriber(id = "popup_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        Element popup = nifty.findPopupByName(curPopupId); assert popup != null;
        TextField txt = popup.findNiftyControl("popup_txt_rename", TextField.class); assert txt != null;
        String newName = txt.getDisplayedText();
        if (!newName.isEmpty()) {
            network.send(new RenameUnit(network.getSid(), storage.getToken(), storage.getCurUserUnit(), newName));
            onCloseClick(id, null);
        }
        // .....
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {network.send(new GetUnits(network.getSid(), storage.getToken()));}
        }, 1000, TimeUnit.MILLISECONDS);
    }
}
