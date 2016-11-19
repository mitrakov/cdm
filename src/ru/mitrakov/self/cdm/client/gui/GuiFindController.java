package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import ru.mitrakov.self.cdm.client.json.commands.cmd.GetUserId;

/**
 *
 * @author Tommy
 */
public final class GuiFindController extends GuiPopupController implements Controller {
    private String nameToFind = "";
    
    @NiftyEventSubscriber(id = "popup_txt_find")
    public void onNameChanged(String id, TextFieldChangedEvent event) {
        nameToFind = event.getText();
    }
    
    @Override
    @NiftyEventSubscriber(id = "popup_find_ok")
    public void onOkClick(String id, ButtonClickedEvent event) {
        if (!nameToFind.isEmpty()) {
            network.send(new GetUserId(network.getSid(), storage.getToken(), nameToFind));
            onCloseClick(id, null);
        }
    }
}
