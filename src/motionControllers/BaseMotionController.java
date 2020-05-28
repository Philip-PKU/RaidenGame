package motionControllers;

import raidenObjects.BaseRaidenObject;

public abstract class BaseMotionController implements MotionController {
    protected BaseRaidenObject raidenObject;
    protected boolean activated = false;

    public void registerParent(BaseRaidenObject raidenObject) {
        this.raidenObject = raidenObject;
    }

    public BaseRaidenObject getRaidenObject() {
        return raidenObject;
    }
}
