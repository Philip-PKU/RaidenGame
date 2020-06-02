package main.motionControllers;

/**
 * A motion controller with constant speed Y.
 *
 * @author 蔡辉宇
 */
public class ConstSpeedYMotionController extends BaseMotionController {
    float speedY;

    /**
     * Constructor.
     * @param speedY Constant speedY.
     */
    public ConstSpeedYMotionController(float speedY) {
        this.speedY = speedY;
    }

    public void scheduleSpeed() {
        raidenObject.setSpeedY(speedY);
    }

    public float getY() {
        return raidenObject.getY();
    }
}
