package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.math.Vector3f;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public class OutbirstControl extends AnimatedControl {
    private boolean init = false;
    protected final Vector3f startPoint;    // start point

    public OutbirstControl(int startIdx, AnimationQueue queue) {
        super(queue);
        startPoint = new Vector3f(startIdx % Battle.WIDTH, .3f, startIdx / Battle.WIDTH);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (!init) {
            // move spatial to the start position (don't do it in constructor,
            // because spatial is NULL there)
            init = true;
            spatial.setLocalTranslation(startPoint);
        }
        
        // move it!
        if (spatial.getLocalScale().length() < 0.1)    // if get shrivelled
            stop();
        else spatial.scale(0.9f);
    }
}
