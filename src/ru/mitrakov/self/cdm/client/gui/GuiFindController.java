package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import ru.mitrakov.self.cdm.client.json.commands.cmd.GetUserId;

/**
 *
 * @author Tommy
 */
public final class GuiFindController extends GuiPopupController implements Controller {
    @Override
    @NiftyEventSubscriber(id = "btn_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        Element popup = nifty.findPopupByName(curPopupId); assert popup != null;
        TextField txt = popup.findNiftyControl("txt_find", TextField.class); assert txt != null;
        String nameToFind = txt.getRealText();
        if (!nameToFind.isEmpty()) {
            network.send(new GetUserId(network.getSid(), storage.getToken(), nameToFind));
            onCloseClick(id, null);
        }
    }
}
