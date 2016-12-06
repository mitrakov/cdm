package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.*;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Tommy
 */
public class BulletBuilder implements SpatialBuilder {
    private final int startIdx, endIdx;

    public BulletBuilder(int startIdx, int endIdx) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    @Override
    public Spatial create(Application app, AnimationQueue queue) {
        Sphere s = new Sphere(16, 16, .09f);
        Geometry g = new Geometry("bullet", s);
        
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(mat);
        
        g.addControl(new BulletControl(startIdx, endIdx, queue));
        return g;
    }
}
