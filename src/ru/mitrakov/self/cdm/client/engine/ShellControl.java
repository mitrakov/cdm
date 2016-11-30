package ru.mitrakov.self.cdm.client.engine;

import static com.jme3.math.FastMath.*;
import com.jme3.renderer.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public class ShellControl extends AbstractControl {
    public static final float g = 9.8f;     // gravity const
    
    protected final Vector3f startPoint;    // start point
    protected final Vector3f endPoint;      // end point
    protected final Vector3f moveVector;    // normalized vector of XZ-direction    
    protected final float alpha;            // angle of strike
    protected final float y0;               // start Y-position
    protected final float v0;               // start speed (strength)
    protected float t = 0;                  // total time (sec)
    
    private boolean init = false;

    public ShellControl(int startIdx, int endIdx, float alpha) {
        startPoint = new Vector3f(startIdx % Battle.WIDTH, .3f, startIdx / Battle.WIDTH);
        endPoint = new Vector3f(endIdx % Battle.WIDTH, .3f, endIdx / Battle.WIDTH);
        Vector3f dir = endPoint.subtract(startPoint);
        moveVector = dir.normalize();
        this.alpha = alpha;
        this.y0 = startPoint.y;
        this.v0 = sqrt(dir.length()*g/sin(2*alpha));
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
        if (spatial.getLocalTranslation().y < 0) {    // if reached the floor, stop it
            spatial.removeFromParent();
            spatial.removeControl(this);
        } else {
            float dt = tpf;        // scale time (in theory: dt = tpf)
            t += dt;               // inc total time
            spatial.move(moveVector.mult(v0*dt*cos(alpha)));
            spatial.getLocalTranslation().setY(y0 + v0*t*sin(alpha) - g*t*t/2);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
