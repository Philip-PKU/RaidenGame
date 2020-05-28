package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Bivector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ConstSpeedTargetTrackingMotionController extends BaseMotionController implements TargetAwareMotionController {
    BaseRaidenObject target;
    float acceleration, constSpeed, fallbackAngle;
    Bivector currentSpeed;
    boolean firstSpeedUpdate = true;

    public ConstSpeedTargetTrackingMotionController(BaseRaidenObject target, float acceleration, float constSpeed,
                                                    float fallbackAngle) {
        this.target = target;
        this.acceleration = acceleration;
        this.constSpeed = constSpeed;
        this.fallbackAngle = fallbackAngle;
    }

    public ConstSpeedTargetTrackingMotionController(BaseRaidenObject target, float acceleration, float constSpeed) {
        this(target, acceleration, constSpeed, (float) Math.PI);
    }

    private Bivector getSpeedUpdate() {
        float targetDX = target.getX() - raidenObject.getX();
        float targetDY = target.getY() - raidenObject.getY();
        return new Bivector(targetDX, targetDY).normalize(constSpeed);
    }

    @Override
    public void scheduleSpeed() {
        if (!target.isAlive()) {
            if (firstSpeedUpdate) {
                float speedX = (float) sin(fallbackAngle) * constSpeed;
                float speedY = (float) cos(fallbackAngle) * constSpeed;
                raidenObject.setSpeedX(speedX);
                raidenObject.setSpeedY(speedY);
            }
            return;
        }
        if (firstSpeedUpdate) {
            firstSpeedUpdate = false;
            currentSpeed = getSpeedUpdate();
        } else
            currentSpeed = currentSpeed.add(getSpeedUpdate().multiply(acceleration)).normalize(constSpeed);
        raidenObject.setSpeedX(currentSpeed.X);
        raidenObject.setSpeedY(currentSpeed.Y);
    }

    @Override
    public float distToTarget() {
        return new Bivector(raidenObject.getX() - target.getX(), raidenObject.getY() - target.getY()).getNorm();
    }
}
