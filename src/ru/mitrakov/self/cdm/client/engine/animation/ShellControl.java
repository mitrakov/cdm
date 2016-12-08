package ru.mitrakov.self.cdm.client.engine.animation;

import static com.jme3.math.FastMath.*;
import com.jme3.math.Vector3f;
import ru.mitrakov.self.cdm.client.game.Battle;

/**
 *
 * @author Tommy
 */
public class ShellControl extends AnimatedControl {
    public static final float g = 9.8f;     // gravity const
    
    protected final Vector3f endPoint;      // end point
    protected final Vector3f moveVector;    // normalized vector of XZ-direction    
    protected final float alpha;            // angle of strike
    protected final float y0;               // start Y-position
    protected final float v0;               // start speed (strength)
    protected float t = 0;                  // total time (sec)

    public ShellControl(int startIdx, int endIdx, float alpha, AnimationQueue queue) {
        super(startIdx, queue);
        endPoint = new Vector3f(endIdx % Battle.WIDTH, .3f, endIdx / Battle.WIDTH);
        Vector3f dir = endPoint.subtract(startPoint);
        moveVector = dir.normalize();
        this.alpha = alpha;
        this.y0 = startPoint.y;
        this.v0 = sqrt(dir.length()*g/sin(2*alpha));
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (spatial.getLocalTranslation().y < 0)    // if reached the floor, stop it
            stop();
        else {
            float dt = tpf;        // scale time (in theory: dt = tpf)
            t += dt;               // inc total time
            spatial.move(moveVector.mult(v0*dt*cos(alpha)));
            spatial.getLocalTranslation().setY(y0 + v0*t*sin(alpha) - g*t*t/2);
        }
    }
}
