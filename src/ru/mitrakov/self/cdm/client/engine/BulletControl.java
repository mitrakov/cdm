package ru.mitrakov.self.cdm.client.engine;

import com.jme3.renderer.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public class BulletControl extends AbstractControl {
    protected final Vector3f startPoint;
    protected final Vector3f endPoint;
    protected final Vector3f moveVector;
    
    private boolean init = false;

    public BulletControl(int startIdx, int endIdx) {
        startPoint = new Vector3f(startIdx % Battle.WIDTH, .3f, startIdx / Battle.WIDTH);
        endPoint = new Vector3f(endIdx % Battle.WIDTH, .3f, endIdx / Battle.WIDTH);
        moveVector = endPoint.subtract(startPoint).normalize().divide(10);
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
        if (spatial.getLocalTranslation().distance(endPoint) < moveVector.length()) {  // if spatial.xyz == endPoint
            spatial.removeFromParent();
            spatial.removeControl(this);
        } else spatial.move(moveVector);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
