package ru.mitrakov.self.cdm.client.engine.animation;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.*;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Tommy
 */
public class ShellBuilder implements SpatialBuilder {
    private final int startIdx, endIdx;

    public ShellBuilder(int startIdx, int endIdx) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    @Override
    public Spatial create(Application app, AnimationQueue queue) {
        Sphere s = new Sphere(32, 32, .16f);
        Geometry g = new Geometry("shell", s);
        
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        g.setMaterial(mat);
        
        g.addControl(new ShellControl(startIdx, endIdx, FastMath.PI/3, queue));
        return g;
    }
}
