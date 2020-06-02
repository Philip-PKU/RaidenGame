package main.motionControllers;

import main.raidenObjects.BaseRaidenObject;
import main.utils.Bivector;

/**
 * A target tracking MotionController with constant acceleration.
 * Given a target, it initializes its speed by (0, {@link #initSpeed}.
 * Then, in each step, it adjusts its speed by {@link #acceleration}, in the direction of the tracked {@link #target}.
 * When it reaches {@link #maxSpeed}, it stops accelerating.
 *
 * @author 蔡辉宇
 */
public class ConstAccelerationTargetTrackingMotionController extends BaseMotionController implements TargetTrackingMotionController {
    BaseRaidenObject target;
    float acceleration, initSpeed, maxSpeed;
    Bivector currentSpeed;
    boolean firstSpeedUpdate = true, maxSpeedReached = false;

    /**
     * Constructor.
     *
     * @param target The target being tracked.
     * @param acceleration Normalization constant for speed updates.
     * @param initSpeed Initial speedY.
     * @param maxSpeed Maximum speed. Once reached, the controller no longer takes speed updates.
     */
    public ConstAccelerationTargetTrackingMotionController(BaseRaidenObject target, float acceleration, float initSpeed, float maxSpeed) {
        this.target = target;
        this.acceleration = acceleration;
        this.initSpeed = initSpeed;
        this.maxSpeed = maxSpeed;
    }

    public BaseRaidenObject getTarget() {
        return target;
    }

    @Override
    public void scheduleSpeed() {
        if (!target.isAlive()) {
            return;
        }
        if (firstSpeedUpdate) {
            firstSpeedUpdate = false;
            currentSpeed = new Bivector(0, initSpeed);
        } else if (!maxSpeedReached) {
            currentSpeed = currentSpeed.add(getUnnormalizedSpeedUpdate().normalize(acceleration)).normalize(maxSpeed);
            if (currentSpeed.getNorm() >= maxSpeed) {
                maxSpeedReached = true;
            }
        }
        raidenObject.setSpeedX(currentSpeed.X);
        raidenObject.setSpeedY(currentSpeed.Y);
    }
}
