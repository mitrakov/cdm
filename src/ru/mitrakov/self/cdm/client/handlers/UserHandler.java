package ru.mitrakov.self.cdm.client.handlers;

import ru.mitrakov.self.cdm.client.json.commands.cmd.*;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;
import ru.mitrakov.self.cdm.client.game.IStorage;
import ru.mitrakov.self.cdm.client.gui.IGui;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 *
 * @author Tommy
 */
public final class UserHandler extends Handler {
    
    private final IGui gui;
    private final IStorage storage;
    private final INetwork network;

    public UserHandler(Handler parent, INetwork network, IGui gui, IStorage storage) {
        super(parent);
        assert network != null && gui != null && storage != null;
        this.gui = gui;
        this.storage = storage;
        this.network = network;
    }

    @Override
    public void handle(Cmd cmd) {
        if (cmd instanceof ResponseUserId)
            network.send(new Invite(network.getSid(), storage.getToken(), ((ResponseUserId)cmd).userId));
        else if (cmd instanceof Invite) {
            // TODO: check if now playing
            gui.showInvite((Invite)cmd);
        } else if (cmd instanceof ResponseToken) {
            storage.setToken(((ResponseToken)cmd).token);
            network.send(new GetUnits(((ResponseToken)cmd).sid, ((ResponseToken)cmd).token));
            gui.gotoScreen("landing_auth");
        } else if (cmd instanceof ResponseUnits) {
            storage.setUnitNames(((ResponseUnits)cmd).names);
            storage.setCaptainId(((ResponseUnits)cmd).captainId);
            gui.update();
        } else super.handle(cmd);
    }
}
