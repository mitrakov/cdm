package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.List;
import ru.mitrakov.self.cdm.client.game.IBattleManager;

/**
 *
 * @author Tommy
 */
public final class GuiWeaponController implements ScreenController, KeyInputHandler {
    private final IGui gui;
    private final IBattleManager battleManager;

    public GuiWeaponController(IGui gui, IBattleManager battleManager) {
        assert gui != null && battleManager != null;
        this.gui = gui;
        this.battleManager = battleManager;
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {}

    @Override
    public void onStartScreen() {}

    @Override
    public void onEndScreen() {}
    
    @Override
    public boolean keyEvent(NiftyInputEvent event) {
        if (event == NiftyInputEvent.Escape)
            gui.hide();
        return true;
    }
    
    @NiftyEventSubscriber(id = "lst_weapon")
    public void itemClicked(String id, ListBoxSelectionChangedEvent event) {
        List<Integer> lst = event.getSelectionIndices();
        if (lst.size() == 1) {
            int idx = lst.get(0);
            battleManager.setCurWeaponIdx(idx);
        }
        gui.hide();
    }
}
