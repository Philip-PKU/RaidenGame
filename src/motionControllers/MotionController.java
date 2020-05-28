package motionControllers;

import raidenObjects.BaseRaidenObject;

public interface MotionController {
    void scheduleSpeed();

    default void resetSpeed() {
        getRaidenObject().setSpeedX(0);
        getRaidenObject().setSpeedY(0);
    }

    void registerParent(BaseRaidenObject raidenObject);

    BaseRaidenObject getRaidenObject();
}
