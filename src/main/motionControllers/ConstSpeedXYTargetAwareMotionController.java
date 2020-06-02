package main.motionControllers;

import main.utils.Bivector;

/**
 * A target aware motion controller with constant X and Y speed.
 * The class contains a {@link #distToTarget()} function that allows
 *
 * @author 蔡辉宇
 */
public class ConstSpeedXYTargetAwareMotionController extends ConstSpeedXYMotionController {
    float targetX, targetY, constSpeed;

    /**
     * Constructor.
     *
     * @param speedX Constant speedX.
     * @param speedY Constant speedY.
     * @param targetX X coordinate of target.
     * @param targetY Y coordinate of target.
     * @param constSpeed Constant speed of motion.
     */
    public ConstSpeedXYTargetAwareMotionController(float speedX, float speedY, float targetX, float targetY, float constSpeed) {
        super(speedX, speedY);
        this.targetX = targetX;
        this.targetY = targetY;
        this.constSpeed = constSpeed;
    }

    public float getConstSpeed() {
        return constSpeed;
    }

    /**
     * Construct a {@link ConstSpeedXYTargetAwareMotionController} from initial and target XY coordinates and constSpeed.
     * @param initX Initial X coordinate.
     * @param initY Initial Y coordinate.
     * @param targetX Target X coordinate.
     * @param targetY Target Y coordinate.
     * @param constSpeed Constant speed of motion.
     * @return A new {@link ConstSpeedXYTargetAwareMotionController}.
     */
    public static ConstSpeedXYTargetAwareMotionController createFromXY(float initX, float initY,
                                                                       float targetX, float targetY, float constSpeed) {
        float targetDX = targetX - initX;
        float targetDY = targetY - initY;
        Bivector speed = new Bivector(targetDX, targetDY).normalize(constSpeed);
        return new ConstSpeedXYTargetAwareMotionController(speed.X, speed.Y, targetX, targetY, constSpeed);
    }

    public float distToTarget() {
        return new Bivector(raidenObject.getX() - targetX, raidenObject.getY() - targetY).getNorm();
    }
}
