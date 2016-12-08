package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.math.Vector3f;
import com.jme3.renderer.*;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public abstract class AnimatedControl extends AbstractControl {
    private final AnimationQueue queue;
    protected final Vector3f startPoint;

    public AnimatedControl(int startIdx, AnimationQueue queue) {
        this.queue = queue;
        startPoint = new Vector3f(startIdx % Battle.WIDTH, .3f, startIdx / Battle.WIDTH);
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial != null) // if someone removes control from the spatial, "setSpatial(null)" is called
            spatial.setLocalTranslation(startPoint);
    }
    
    protected void stop() {
        spatial.removeFromParent();
        spatial.removeControl(this);
        queue.stop();
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
