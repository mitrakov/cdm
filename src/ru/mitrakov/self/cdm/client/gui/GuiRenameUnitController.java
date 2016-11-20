package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import ru.mitrakov.self.cdm.client.Scheduler;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;

/**
 *
 * @author Tommy
 */
public final class GuiRenameUnitController extends GuiPopupController implements Controller {
    @Override
    @NiftyEventSubscriber(id = "btn_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        Element popup = nifty.findPopupByName(curPopupId); assert popup != null;
        TextField txt = popup.findNiftyControl("txt_rename", TextField.class); assert txt != null;
        String newName = txt.getDisplayedText();
        if (!newName.isEmpty()) {
            network.send(new RenameUnit(network.getSid(), storage.getToken(), storage.getCurUserUnit(), newName));
            Scheduler.getInstance().run(new Runnable() {
                @Override
                public void run() {network.send(new GetUnits(network.getSid(), storage.getToken()));}
            }, 1000);
            onCloseClick(id, null);
        }
    }
}
