package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Bivector;

public class ConstAccelerationTargetTrackingMotionController extends BaseMotionController implements TargetAwareMotionController {
    BaseRaidenObject target;
    float acceleration, initSpeed, maxSpeed;
    Bivector currentSpeed;
    boolean firstSpeedUpdate = true, maxSpeedReached = false;

    public ConstAccelerationTargetTrackingMotionController(BaseRaidenObject target, float acceleration, float initSpeed, float maxSpeed) {
        this.target = target;
        this.acceleration = acceleration;
        this.initSpeed = initSpeed;
        this.maxSpeed = maxSpeed;
    }

    private Bivector getUnnormalizedSpeedUpdate() {
        float targetDX = target.getX() - raidenObject.getX();
        float targetDY = target.getY() - raidenObject.getY();
        return new Bivector(targetDX, targetDY);
    }

    @Override
    public void scheduleSpeed() {
        if (!target.isAlive()) {
            return;
        }
        if (firstSpeedUpdate) {
            firstSpeedUpdate = false;
            currentSpeed = getUnnormalizedSpeedUpdate().normalize(initSpeed);
        }
        else if (!maxSpeedReached) {
            currentSpeed = currentSpeed.add(getUnnormalizedSpeedUpdate().normalize(acceleration)).normalize(maxSpeed);
            if (currentSpeed.getNorm() >= maxSpeed) {
                maxSpeedReached = true;
            }
        }
        raidenObject.setSpeedX(currentSpeed.X);
        raidenObject.setSpeedY(currentSpeed.Y);
    }

    @Override
    public float distToTarget() {
        return new Bivector(raidenObject.getX() - target.getX(), raidenObject.getY() - target.getY()).getNorm();
    }
}
