package motionControllers;

import static world.World.gameStep;

public class ConstAccelerationYMotionController extends BaseMotionController implements YAwareMotionController {
    float acceleration, maxSpeedY, initSpeedY;
    boolean isAccelerating = false;
    int gameStepWhenAccelerationStarted;

    public ConstAccelerationYMotionController(float initSpeedY, float acceleration, float maxSpeedY) {
        this.acceleration = acceleration;
        this.initSpeedY = initSpeedY;
        this.maxSpeedY = maxSpeedY;
    }

    public ConstAccelerationYMotionController(float initSpeedY, float acceleration) {
        this(initSpeedY, acceleration, Float.MAX_VALUE);
    }

    @Override
    public float getY() {
        return raidenObject.getY();
    }

    @Override
    public void scheduleSpeed() {
        if (isAccelerating) {
            float speedY = Math.min(
                    initSpeedY + (gameStep.intValue() - gameStepWhenAccelerationStarted) * acceleration,
                    maxSpeedY
            );
            raidenObject.setSpeedY(speedY);
        } else {
            isAccelerating = true;
            gameStepWhenAccelerationStarted = gameStep.intValue();
        }
    }
}
