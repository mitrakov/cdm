package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.app.Application;
import com.jme3.scene.Spatial;

/**
 *
 * @author Tommy
 */
public interface SpatialBuilder {
    Spatial create(Application app, AnimationQueue queue);
}
