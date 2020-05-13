package motionControllers;

public class ConstSpeedYMotionController extends BaseMotionController implements YAwareMotionController {
    float speedY;

    public ConstSpeedYMotionController(float speedY) {
        this.speedY = speedY;
    }

    public void scheduleSpeed() {
        raidenObject.setSpeedY(speedY);
    }

    @Override
    public float getY() {
        return raidenObject.getY();
    }
}
