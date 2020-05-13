package motionControllers;

import static java.lang.Math.*;

public class ConstSpeedXYMotionController extends ConstSpeedYMotionController {
    float speedX;

    public ConstSpeedXYMotionController(float speedX, float speedY) {
        super(speedY);
        this.speedX = speedX;
    }

    @Override
    public void scheduleSpeed() {
        super.scheduleSpeed();
        raidenObject.setSpeedX(speedX);
    }

    public static ConstSpeedXYMotionController fromTargetAngle(float theta, float maxSpeed) {
        float thetaInRad = (float) toRadians(theta);
        float speedX = (float) sin(thetaInRad) * maxSpeed;
        float speedY = (float) cos(thetaInRad) * maxSpeed;
        return new ConstSpeedXYMotionController(speedX, speedY);
    }
}
