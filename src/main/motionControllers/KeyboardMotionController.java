package main.motionControllers;

import main.utils.RaidenKeyAdapter;

import static main.world.World.isOutOfWindow;

/**
 * A motion controller that set speed according to keyboard inputs.
 * The controller does coordinate check to ensure the parent does not goes of screen.
 */
public class KeyboardMotionController extends BaseMotionController implements MotionController {
    RaidenKeyAdapter keyAdapter;
    float constSpeed;

    /**
     * Constructor.
     *
     * @param keyAdapter A {@link RaidenKeyAdapter} controlling this MotionController.
     * @param constSpeed Constant speed of motion.
     */
    public KeyboardMotionController(RaidenKeyAdapter keyAdapter, float constSpeed) {
        this.keyAdapter = keyAdapter;
        this.constSpeed = constSpeed;
    }

    @Override
    public void scheduleSpeed() {
        int arrowState = keyAdapter.getMotionState();
        raidenObject.setSpeedX(0);
        raidenObject.setSpeedY(0);
        if ((arrowState & keyAdapter.LEFT) != 0 &&
                !isOutOfWindow(raidenObject.getMinX() - constSpeed, raidenObject.getMinY()) &&
                !isOutOfWindow(raidenObject.getMinX() - constSpeed, raidenObject.getMaxY()))
            raidenObject.setSpeedX(-constSpeed);
        if ((arrowState & keyAdapter.RIGHT) != 0 &&
                !isOutOfWindow(raidenObject.getMaxX() + constSpeed, raidenObject.getMinY()) &&
                !isOutOfWindow(raidenObject.getMaxX() + constSpeed, raidenObject.getMaxY()))
            raidenObject.setSpeedX(constSpeed);
        if ((arrowState & keyAdapter.UP) != 0 &&
                !isOutOfWindow(raidenObject.getMinX(), raidenObject.getMinY() - constSpeed) &&
                !isOutOfWindow(raidenObject.getMaxX(), raidenObject.getMinY() - constSpeed))
            raidenObject.setSpeedY(-constSpeed);
        if ((arrowState & keyAdapter.DOWN) != 0 &&
                !isOutOfWindow(raidenObject.getMinX(), raidenObject.getMaxY() + constSpeed) &&
                !isOutOfWindow(raidenObject.getMaxX(), raidenObject.getMaxY() + constSpeed))
            raidenObject.setSpeedY(constSpeed);
    }
}
