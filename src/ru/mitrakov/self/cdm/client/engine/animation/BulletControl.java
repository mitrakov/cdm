package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.renderer.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public class BulletControl extends AnimatedControl {
    protected final Vector3f startPoint;
    protected final Vector3f endPoint;
    protected final Vector3f moveVector;
    protected final float v0 = 6;
    
    private boolean init = false;

    public BulletControl(int startIdx, int endIdx, AnimationQueue queue) {
        super(queue);
        startPoint = new Vector3f(startIdx % Battle.WIDTH, .3f, startIdx / Battle.WIDTH);
        endPoint = new Vector3f(endIdx % Battle.WIDTH, .3f, endIdx / Battle.WIDTH);
        moveVector = endPoint.subtract(startPoint).normalize();
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
        if (spatial.getLocalTranslation().distance(endPoint) < .5f)    // if reached endPoint, stop it
            stop();
        else {
            float dt = tpf;        // scale time (in theory: dt = tpf)
            spatial.move(moveVector.mult(v0*dt));
        }
    }
}
