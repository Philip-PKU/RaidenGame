package motionControllers;

import raidenObjects.BaseRaidenObject;

/**
 * Controls the motion of parent {@link BaseRaidenObject}.
 *
 * @author 蔡辉宇
 */
public interface MotionController {
    /**
     * Schedule speed for parent {@link BaseRaidenObject}.
     * First calculate the speed, then set it by invoking {@link BaseRaidenObject#setSpeedX(float)} and {@link BaseRaidenObject#setSpeedY(float)}.
     */
    void scheduleSpeed();

    /**
     * Reset the speed of parent {@link BaseRaidenObject}.
     */
    default void resetSpeed() {
        getParent().setSpeedX(0);
        getParent().setSpeedY(0);
    }

    /**
     * Register its parent {@link BaseRaidenObject}.
     * @param raidenObject The parent of this motion controller, i.e. the object that it controls.
     */
    void registerParent(BaseRaidenObject raidenObject);

    /**
     * Get its parent.
     * @return A {@link BaseRaidenObject}.
     */
    BaseRaidenObject getParent();
}
