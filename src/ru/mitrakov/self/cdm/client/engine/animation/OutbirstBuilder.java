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
public class OutbirstBuilder implements SpatialBuilder {
    private final int startIdx;

    public OutbirstBuilder(int startIdx) {
        this.startIdx = startIdx;
    }

    @Override
    public Spatial create(Application app, AnimationQueue queue) {
        Sphere s = new Sphere(32, 32, 3);
        Geometry g = new Geometry("shell", s);
        
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        g.setMaterial(mat);
        
        g.addControl(new OutbirstControl(startIdx, queue));
        return g;
    }
}
