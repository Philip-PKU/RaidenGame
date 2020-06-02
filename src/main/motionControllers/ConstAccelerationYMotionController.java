package main.motionControllers;

import static main.world.World.gameStep;

/**
 * A motion controller with constant acceleration in the Y direction.
 *
 * @author 蔡辉宇
 */
public class ConstAccelerationYMotionController extends BaseMotionController {
    float acceleration, maxSpeedY, initSpeedY;
    boolean isAccelerating = false;
    int gameStepWhenAccelerationStarted;

    /**
     * Constructor.
     *
     * @param initSpeedY Initial speedY.
     * @param acceleration Acceleration in speedY (per game step).
     * @param maxSpeedY Maximum speedY.
     */
    public ConstAccelerationYMotionController(float initSpeedY, float acceleration, float maxSpeedY) {
        this.acceleration = acceleration;
        this.initSpeedY = initSpeedY;
        this.maxSpeedY = maxSpeedY;
    }

    public ConstAccelerationYMotionController(float initSpeedY, float acceleration) {
        this(initSpeedY, acceleration, Float.MAX_VALUE);
    }

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
