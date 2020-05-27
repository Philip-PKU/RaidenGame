package motionControllers;

import utils.Bivector;

public class TargetAwareConstSpeedXYMotionController extends ConstSpeedXYMotionController
        implements TargetAwareMotionController {
    float targetX, targetY, maxSpeed;

    public TargetAwareConstSpeedXYMotionController(float speedX, float speedY, float targetX, float targetY, float maxSpeed) {
        super(speedX, speedY);
        this.targetX = targetX;
        this.targetY = targetY;
        this.maxSpeed = maxSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public static TargetAwareConstSpeedXYMotionController createFromXY(float initX, float initY,
                                                                       float targetX, float targetY, float maxSpeed) {
        float targetDX = targetX - initX;
        float targetDY = targetY - initY;
        Bivector speed = new Bivector(targetDX, targetDY).normalize(maxSpeed);
        return new TargetAwareConstSpeedXYMotionController(speed.X, speed.Y, targetX, targetY, maxSpeed);
    }

    @Override
    public float distToTarget() {
        return new Bivector(raidenObject.getX() - targetX, raidenObject.getY() - targetY).getNorm();
    }
}
