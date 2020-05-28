package raidenObjects.aircrafts;

import motionControllers.ConstAccelerationYMotionController;
import motionControllers.ConstSpeedYMotionController;
import motionControllers.TwoStagedMotionController;
import utils.Faction;

public final class BumpingAircraft extends BaseAircraft {
    private static int staticMaxHp = 50;

    public BumpingAircraft(float x, float y) {
        super("BumpingAircraft", x, y, 30, 23, Faction.ENEMY,
                staticMaxHp, 13, 49, 50);
        ConstSpeedYMotionController stageOneMotionController = new ConstSpeedYMotionController(2);
        ConstAccelerationYMotionController stageTwoMotionController = new ConstAccelerationYMotionController(4, 0.05f, 14);
        this.registerMotionController(new TwoStagedMotionController(stageOneMotionController, stageTwoMotionController,
                () -> getY() > 60, () -> {
        }));
        probCoin0 = 0.1f;
        probCoin1 = 0.2f;
        probCoin2 = 0.1f;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        BumpingAircraft.staticMaxHp = staticMaxHp;
    }
}
