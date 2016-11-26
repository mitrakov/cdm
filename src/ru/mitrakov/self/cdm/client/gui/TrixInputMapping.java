package ru.mitrakov.self.cdm.client.gui;

import de.lessvoid.nifty.input.*;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

/**
 *
 * @author Tommy
 */
public class TrixInputMapping implements NiftyInputMapping {

    @Override
    public NiftyInputEvent convert(KeyboardInputEvent inputEvent) {
        // ....
        if (inputEvent.isKeyDown())
            if (inputEvent.getKey() == KeyboardInputEvent.KEY_ESCAPE)
                return NiftyInputEvent.Escape;
        return null;
    }
}
