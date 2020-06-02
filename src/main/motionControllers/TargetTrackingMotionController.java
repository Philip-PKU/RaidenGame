package main.motionControllers;

import main.raidenObjects.BaseRaidenObject;
import main.utils.Bivector;

/**
 * An sub-interface of {@link MotionController} that has target tracking utilities.
 *
 * @author 蔡辉宇
 */
public interface TargetTrackingMotionController extends MotionController {
    /**
     * Returns the tracked target, which is a {@link BaseRaidenObject}.
     * @return The tracked target.
     */
    BaseRaidenObject getTarget();

    /**
     * Get unnormalized speed update, based on parent and target positions.
     * @return An unnormalized speed update, representing the direction of acceleration.
     */
    default Bivector getUnnormalizedSpeedUpdate() {
        float targetDX = getTarget().getX() - getParent().getX();
        float targetDY = getTarget().getY() - getParent().getY();
        return new Bivector(targetDX, targetDY);
    }

    /**
     * Get the distance to target.
     * @return Distance to target.
     */
    default float distToTarget() {
        return new Bivector(getParent().getX() - getTarget().getX(), getParent().getY() - getTarget().getY()).getNorm();
    }
}
