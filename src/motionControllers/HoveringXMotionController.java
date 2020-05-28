package motionControllers;

/**
 * A motion controller doing hovering-X motion, i.e. the parent will move back and forth in the horizontal direction.
 *
 * @author 蔡辉宇
 */
public class HoveringXMotionController extends BaseMotionController implements MotionController {
    float speedX, leftBoundX, rightBoundX;
    boolean movingLeft;

    /**
     * Constructor.
     *
     * @param speedX Constant hovering speed of X.
     * @param leftBoundX Left bound of X coordinate.
     * @param rightBoundX Right bound of X coordinate.
     */
    public HoveringXMotionController(float speedX,
                                     float leftBoundX, float rightBoundX) {
        this.speedX = speedX;
        this.leftBoundX = leftBoundX;
        this.rightBoundX = rightBoundX;
    }

    @Override
    public void scheduleSpeed() {
        if (movingLeft) {
            if (raidenObject.getX() - speedX - 1 > leftBoundX)
                raidenObject.setSpeedX(-speedX);
            else movingLeft = false;
        } else {
            if (raidenObject.getX() + speedX + 1 < rightBoundX)
                raidenObject.setSpeedX(speedX);
            else movingLeft = true;
        }
    }
}
