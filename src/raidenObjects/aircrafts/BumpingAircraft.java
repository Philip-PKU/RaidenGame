package raidenObjects.aircrafts;

import motionControllers.ConstAccelerationYMotionController;
import motionControllers.ConstSpeedYMotionController;
import motionControllers.TwoStagedMotionController;
import utils.Faction;

public final class BumpingAircraft extends BaseAircraft {
    private boolean hasReachedTargetPos;

    public BumpingAircraft(float x, float y) {
        super("BumpingAircraft", x, y, 30, 23, Faction.ENEMY,
                100, 13, 50);
        ConstSpeedYMotionController stageOneMotionController = new ConstSpeedYMotionController(2);
        ConstAccelerationYMotionController stageTwoMotionController = new ConstAccelerationYMotionController(4, 0.05f, 14);
        this.registerMotionController(new TwoStagedMotionController(stageOneMotionController, stageTwoMotionController,
                () -> getY() > 60, stageTwoMotionController::startAccelerating));
    }
}
