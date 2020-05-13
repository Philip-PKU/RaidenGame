package motionControllers;

public class HoveringXMotionController extends BaseMotionController implements MotionController {
    float speedX, leftBoundX, rightBoundX;
    boolean movingLeft;

    public HoveringXMotionController(float speedX,
                                     float leftBoundX, float rightBoundX) {
        this.speedX = speedX;
        this.leftBoundX = leftBoundX;
        this.rightBoundX = rightBoundX;
    }

    @Override
    public void scheduleSpeed() {
        if (movingLeft) {
            if (raidenObject.getX() > leftBoundX)
                raidenObject.setSpeedX(-speedX);
            else movingLeft = false;
        }
        else {
            if (raidenObject.getX() < rightBoundX)
                raidenObject.setSpeedX(speedX);
            else movingLeft = true;
        }
    }
}
