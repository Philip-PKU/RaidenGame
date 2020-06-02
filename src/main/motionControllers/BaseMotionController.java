package main.motionControllers;

import main.raidenObjects.BaseRaidenObject;

/**
 * Base class for all {@link MotionController}s.
 *
 * @author 蔡辉宇
 */
public abstract class BaseMotionController implements MotionController {
    protected BaseRaidenObject raidenObject;

    public void registerParent(BaseRaidenObject raidenObject) {
        this.raidenObject = raidenObject;
    }

    public BaseRaidenObject getParent() {
        return raidenObject;
    }
}
