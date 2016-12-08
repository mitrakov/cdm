package ru.mitrakov.self.cdm.client.engine.animation;

/**
 *
 * @author Tommy
 */
public class OutbirstControl extends AnimatedControl {
    public OutbirstControl(int startIdx, AnimationQueue queue) {
        super(startIdx, queue);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (spatial.getLocalScale().length() < 0.1)    // if get shrivelled
            stop();
        else spatial.scale(0.9f);
    }
}
