package motionControllers;

import static java.lang.Math.*;

/**
 * A motion controller with constant X and Y speed.
 *
 * @author 蔡辉宇
 */
public class ConstSpeedXYMotionController extends ConstSpeedYMotionController {
    float speedX;

    /**
     * Constructor.
     *
     * @param speedX Constant speed of X.
     * @param speedY Constant speed of Y.
     */
    public ConstSpeedXYMotionController(float speedX, float speedY) {
        super(speedY);
        this.speedX = speedX;
    }

    @Override
    public void scheduleSpeed() {
        super.scheduleSpeed();
        raidenObject.setSpeedX(speedX);
    }

    /**
     * Construct a {@link ConstSpeedXYMotionController} from given angle and constSpeed.
     * @param theta Angle of motion.
     * @param constSpeed Constant speed of motion.
     * @return
     */
    public static ConstSpeedXYMotionController createFromAngle(float theta, float constSpeed) {
        float thetaInRad = (float) toRadians(theta);
        float speedX = (float) sin(thetaInRad) * constSpeed;
        float speedY = (float) cos(thetaInRad) * constSpeed;
        return new ConstSpeedXYMotionController(speedX, speedY);
    }
}
