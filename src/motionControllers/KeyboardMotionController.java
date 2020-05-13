package motionControllers;

import utils.BaseRaidenKeyAdapter;

import static world.World.isOutOfWindow;

public class KeyboardMotionController extends BaseMotionController implements MotionController {
    BaseRaidenKeyAdapter keyAdapter;
    float maxSpeed;

    public KeyboardMotionController(BaseRaidenKeyAdapter keyAdapter, float maxSpeed) {
        this.keyAdapter = keyAdapter;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void scheduleSpeed() {
        int arrowState = keyAdapter.getArrowState();
        raidenObject.setSpeedX(0);
        raidenObject.setSpeedY(0);
        if ((arrowState & keyAdapter.left) != 0 &&
                !isOutOfWindow(raidenObject.getMinX() - maxSpeed, raidenObject.getMinY()) &&
                !isOutOfWindow(raidenObject.getMinX() - maxSpeed, raidenObject.getMaxY()))
            raidenObject.setSpeedX(-maxSpeed);
        if ((arrowState & keyAdapter.right) != 0 &&
                !isOutOfWindow(raidenObject.getMaxX() + maxSpeed, raidenObject.getMinY()) &&
                !isOutOfWindow(raidenObject.getMaxX() + maxSpeed, raidenObject.getMaxY()))
            raidenObject.setSpeedX(maxSpeed);
        if ((arrowState & keyAdapter.up) != 0 &&
                !isOutOfWindow(raidenObject.getMinX(), raidenObject.getMinY() - maxSpeed) &&
                !isOutOfWindow(raidenObject.getMaxX(), raidenObject.getMinY() - maxSpeed))
            raidenObject.setSpeedY(-maxSpeed);
        if ((arrowState & keyAdapter.down) != 0 &&
                !isOutOfWindow(raidenObject.getMinX(), raidenObject.getMaxY() + maxSpeed) &&
                !isOutOfWindow(raidenObject.getMaxX(), raidenObject.getMaxY() + maxSpeed))
            raidenObject.setSpeedY(maxSpeed);
    }
}
