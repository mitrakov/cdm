package ru.mitrakov.self.cdm.client;

import com.jme3.math.*;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.renderer.Camera;
import java.util.*;

/**
 *
 * @author Tommy
 */
public class TrixCamera implements AnalogListener {
    public static enum Mode {NONE, FLY, AROUND};
    
    protected final static float R = 3;
    protected final static float SPEED = 1.6f;
    
    protected final Camera cam;
    protected final InputManager inputManager;
    
    protected Mode mode = Mode.FLY;
    protected boolean enabled = true;
    protected float phi = -FastMath.HALF_PI;
    protected Vector3f foothold = Vector3f.ZERO;
    
    protected List<String> mappings = Arrays.asList("TRIX_TURN_LEFT", "TRIX_TURN_RIGHT", "TRIX_TURN_UP", "TRIX_TURN_DOWN");

    public TrixCamera(Camera cam, InputManager inputManager) {
        assert cam != null && inputManager != null;
        this.cam = cam;
        this.inputManager = inputManager;
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!enabled) return;
        switch (mode) {
            case FLY: freeCamHandler(name, value); break;
            case AROUND: aroundHandler(name, value); break;
            default:
        }
    }
    
    public void register() {
        inputManager.addListener(this, mappings.toArray(new String[0]));
        inputManager.addMapping("TRIX_TURN_LEFT",  new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("TRIX_TURN_RIGHT", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("TRIX_TURN_UP",    new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping("TRIX_TURN_DOWN",  new MouseAxisTrigger(MouseInput.AXIS_Y, false));
    }
    
    public void unregister() {
        inputManager.removeListener(this);
        for (String mapping : mappings)
            if (inputManager.hasMapping(mapping))
                inputManager.deleteMapping(mapping);
    }
    
    public void disable() {
        enabled = false;
    }
    
    public void enable() {
        enabled = true;
    }
    
    public void setFlyMode() {
        mode = Mode.FLY;
    }
    
    public void setMoveAroundMode(Vector3f foothold) {
        setMoveAroundMode(foothold, phi);
    }
    
    public void setMoveAroundMode(Vector3f foothold, float phi) {
        this.foothold = foothold.clone();
        this.phi = phi;
        mode = Mode.AROUND;
    }
    
    protected void freeCamHandler(String name, float value) {
        switch (name) {
            case "TRIX_TURN_LEFT":
                rotate(value, Vector3f.UNIT_Y);
                break;
            case "TRIX_TURN_RIGHT":
                rotate(-value, Vector3f.UNIT_Y);
                break;
            case "TRIX_TURN_UP":
                rotate(value, cam.getLeft());
                break;
            case "TRIX_TURN_DOWN":
                rotate(-value, cam.getLeft());
                break;
            default:
        }
    }
    
    protected void aroundHandler(String name, float value) {
        switch (name) {
            case "TRIX_TURN_LEFT":
                moveAround(-value);
                break;
            case "TRIX_TURN_RIGHT":
                moveAround(value);
                break;
            default:
        }
    }
    
    protected void rotate(float value, Vector3f axis) {
        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(SPEED * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        cam.setAxes(q);
    }
    
    protected void moveAround(float value) {
        phi += value;
        float x = R * FastMath.cos(phi);
        float z = R * FastMath.sin(phi);
        
        cam.setLocation(foothold.add(x, 0, z));
        cam.lookAt(foothold, Vector3f.UNIT_Y);
    }
    
    // GENERATED CODE
    public Mode getMode() {
        return mode;
    }
}
