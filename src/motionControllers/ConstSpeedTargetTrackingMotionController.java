package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Bivector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * A target tracking motion controller with constant speed.
 *
 * @author 蔡辉宇
 */
public class ConstSpeedTargetTrackingMotionController extends BaseMotionController implements TargetTrackingMotionController {
    BaseRaidenObject target;
    float acceleration, constSpeed, fallbackAngle;
    Bivector currentSpeed;
    boolean firstSpeedUpdate = true;

    /**
     * Constructor.
     *
     * @param target The tracked target. A {@link BaseRaidenObject}.
     * @param acceleration Multiplication constant for speed updates.
     * @param constSpeed Constant speed of movement.
     * @param fallbackAngle Fallback initial motion angle when the target is dead.
     */
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
            currentSpeed = getUnnormalizedSpeedUpdate().normalize(constSpeed);
        } else
            currentSpeed = currentSpeed.add(getUnnormalizedSpeedUpdate().normalize(constSpeed).multiply(acceleration))
                    .normalize(constSpeed);
        raidenObject.setSpeedX(currentSpeed.X);
        raidenObject.setSpeedY(currentSpeed.Y);
    }

    @Override
    public BaseRaidenObject getTarget() {
        return target;
    }
}
