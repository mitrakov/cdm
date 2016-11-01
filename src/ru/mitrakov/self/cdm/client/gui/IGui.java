package ru.mitrakov.self.cdm.client.gui;

import java.util.Collection;
import ru.mitrakov.self.cdm.client.game.Weapon;

/**
 *
 * @author Tommy
 */
public interface IGui {
    public void showInvite(int sid);
    public void showReject(int sid);
    public void showVictory(String winnerName);
    public void showWeapon(Collection<? extends Weapon> weapons);
    public void hide();
    public void update();
    public void gotoScreen(String screen);
    public void gotoLandingScreen();
    public void exit();
}
