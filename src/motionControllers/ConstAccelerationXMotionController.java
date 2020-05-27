package motionControllers;

import static world.World.gameStep;

public class ConstAccelerationXMotionController extends BaseMotionController implements XAwareMotionController {
    float acceleration, maxSpeedX, initSpeedX;
    boolean isAccelerating = false;
    int gameStepWhenAccelerationStarted;

    public ConstAccelerationXMotionController(float initSpeedX, float acceleration, float maxSpeedX) {
        this.acceleration = acceleration;
        this.initSpeedX = initSpeedX;
        this.maxSpeedX = maxSpeedX;
    }

    public ConstAccelerationXMotionController(float initSpeedX, float acceleration) {
        this(initSpeedX, acceleration, Float.MAX_VALUE);
    }

    @Override
    public float getX() {
        return raidenObject.getX();
    }

    @Override
    public void scheduleSpeed() {
        if (isAccelerating) {
            float speedX = Math.min(
                    initSpeedX + (gameStep.intValue() - gameStepWhenAccelerationStarted) * acceleration,
                    maxSpeedX
            );
            raidenObject.setSpeedX(speedX);
        } else {
            isAccelerating = true;
            gameStepWhenAccelerationStarted = gameStep.intValue();
        }
    }
}
