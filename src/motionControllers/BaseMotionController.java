package motionControllers;

import raidenObjects.BaseRaidenObject;

public abstract class BaseMotionController implements MotionController {
    protected BaseRaidenObject raidenObject;
    protected boolean activated = false;

    public void registerParent(BaseRaidenObject raidenObject) {
        this.raidenObject = raidenObject;
    }

    @Override
    public void resetSpeed() {
        raidenObject.setSpeedX(0);
        raidenObject.setSpeedY(0);
    }

}
