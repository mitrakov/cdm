package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Tommy
 */
public abstract class AnimatedControl extends AbstractControl {
    private final AnimationQueue queue;

    public AnimatedControl(AnimationQueue queue) {
        this.queue = queue;
    }
    
    protected void stop() {
        spatial.removeFromParent();
        spatial.removeControl(this);
        queue.stop();
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
