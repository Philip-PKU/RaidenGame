package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Bivector;

public class TargetTrackingMotionController extends BaseMotionController implements TargetAwareMotionController {
    BaseRaidenObject target;
    float acceleration, maxSpeed;
    Bivector currentSpeed;
    boolean firstSpeedUpdate = true;

    public TargetTrackingMotionController(BaseRaidenObject target, float acceleration, float maxSpeed) {
        this.target = target;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
    }

    private Bivector getSpeedUpdate() {
        float targetDX = target.getX() - raidenObject.getX();
        float targetDY = target.getY() - raidenObject.getY();
        return new Bivector(targetDX, targetDY).normalize(maxSpeed);
    }

    @Override
    public void scheduleSpeed() {
        Bivector speed;
        if (firstSpeedUpdate) {
            firstSpeedUpdate = false;
            currentSpeed = getSpeedUpdate();
            speed = currentSpeed;
        }
        else
            speed = currentSpeed.add(getSpeedUpdate().multiply(acceleration)).normalize(maxSpeed);
        raidenObject.setSpeedX(speed.X);
        raidenObject.setSpeedY(speed.Y);
    }

    @Override
    public float distToTarget() {
        return new Bivector(raidenObject.getX() - target.getX(), raidenObject.getY() - target.getY()).getNorm();
    }
}
