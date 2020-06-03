package main.motionControllers;

import static main.World.gameStep;

/**
 * A motion controller with constant acceleration in the X direction.
 *
 * @author 蔡辉宇
 */
public class ConstAccelerationXMotionController extends BaseMotionController {
    float acceleration, maxSpeedX, initSpeedX;
    boolean isAccelerating = false;
    int gameStepWhenAccelerationStarted;

    /**
     * Constructor.
     *
     * @param initSpeedX Initial speedX.
     * @param acceleration Acceleration in speedX (per game step).
     * @param maxSpeedX Maximum speedX.
     */
    public ConstAccelerationXMotionController(float initSpeedX, float acceleration, float maxSpeedX) {
        this.acceleration = acceleration;
        this.initSpeedX = initSpeedX;
        this.maxSpeedX = maxSpeedX;
    }

    public ConstAccelerationXMotionController(float initSpeedX, float acceleration) {
        this(initSpeedX, acceleration, Float.MAX_VALUE);
    }

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
