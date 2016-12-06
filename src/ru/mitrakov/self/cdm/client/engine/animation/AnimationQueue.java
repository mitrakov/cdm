package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.app.SimpleApplication;
import java.util.*;

/**
 *
 * @author Tommy
 */
public class AnimationQueue {
    private final SimpleApplication app;
    private final Queue<SpatialBuilder> builders = new LinkedList<>();
    private transient boolean isPlaying = false;

    public AnimationQueue(SimpleApplication app) {
        assert app != null && app.getRootNode() != null;
        this.app = app;
    }
    
    public void enqueue(SpatialBuilder builder) {
        assert builder != null;
        builders.add(builder);
        runNext();
    }
    
    public void stop() {
        isPlaying = false;
        runNext();
    }
    
    private void runNext() {
        if (isPlaying) return;    // current animation is not finished yet
        SpatialBuilder builder = builders.poll();
        if (builder != null) {
            app.getRootNode().attachChild(builder.create(app, this));
            isPlaying = true;
        }
    }
}
