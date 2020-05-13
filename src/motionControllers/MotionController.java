package motionControllers;

import raidenObjects.BaseRaidenObject;

public interface MotionController {
    void scheduleSpeed();

    void resetSpeed();

    void registerParent(BaseRaidenObject raidenObject);
}
