package main.raidenObjects.aircrafts;

import main.motionControllers.ConstAccelerationYMotionController;
import main.motionControllers.ConstSpeedYMotionController;
import main.motionControllers.TwoStagedMotionController;
import main.utils.Faction;
import main.utils.InitLocation;

/**
 * BumpingAircraft. A light-weighted aircraft so hostile to the players that it is willing to sacrifice itself to kill
 * them.
 * Its motion is controller by a {@link ConstAccelerationYMotionController} which makes it rush straight south.
 *
 * @author 蔡辉宇
 */
public final class BumpingAircraft extends BaseAircraft {
    private static int defaultMaxHp = 50, staticMaxHp = defaultMaxHp;

    public BumpingAircraft() {
        super("BumpingAircraft",30, 23, Faction.ENEMY,
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

    public BumpingAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public BumpingAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        BumpingAircraft.staticMaxHp = staticMaxHp;
    }
}
