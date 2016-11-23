package ru.mitrakov.self.cdm.client.gui;

import java.util.Collection;
import ru.mitrakov.self.cdm.client.game.Weapon;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;

/**
 *
 * @author Tommy
 */
public interface IGui {
    public void showInvite(Invite cmd);
    public void showReject(Reject cmd);
    public void showGiveUp();
    public void showVictory(String winnerName);
    public void showWeapon(Collection<? extends Weapon> weapons);
    public void hide();
    public void update();
    public void gotoScreen(String screen);
    public void gotoLandingScreen();
    public void exit();
}
