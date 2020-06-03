package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.PeriodicLaunchCondition;
import main.launchControllers.SimpleLaunchController;
import main.motionControllers.ConstSpeedYMotionController;
import main.motionControllers.MotionController;
import main.motionControllers.TwoStagedMotionController;
import main.raidenObjects.weapons.BarbetteBullet;
import main.utils.Faction;
import main.utils.InitLocation;

import static main.World.interactantList;

/**
 * Barbette. A giant, powerful, tank-like aircraft that never hesitates to kill.
 * Its weapon ({@link BarbetteBullet}) is shot straight to the south but will gradually get closer to the player's
 * last know location, i,e, the location when the weapon is launched.
 * Its motion is controlled by a {@link TwoStagedMotionController}: the first takes it into the map, and the second
 * (a {@link ConstSpeedYMotionController}) makes it go straight south.
 *
 * @author 张哲瑞
 */
public final class BarbetteAircraft extends BaseShootingAircraft {
    private static final float hitSizeY = 140f;
    private static int defaultMaxHp = 600, staticMaxHp = defaultMaxHp;

    public BarbetteAircraft() {
        super("BarbetteAircraft",75, 160, Faction.ENEMY,
                staticMaxHp, 13, 300, 200);
        ConstSpeedYMotionController stageOneController = new ConstSpeedYMotionController(2.5f);
        MotionController stageTwoController = new ConstSpeedYMotionController(1.3f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> getWeaponLaunchController().activate()));

        this.registerWeaponLaunchController(new SimpleLaunchController<>(
                "BarbetteAircraft shoots BarbetteBullet",
                new PeriodicLaunchCondition(300, 80, 22, 5),
                () -> {
                    PlayerAircraft closestPlayer = getClosestPlayer();
                    interactantList.add(new BarbetteBullet(getX() - 10, getMaxY()));
                    interactantList.add(new BarbetteBullet(getX() + 10, getMaxY()));
                })
        );
        probCoin1 = 0.15f;
        probCoin2 = 0.4f;
        probMagnet = 0.1f;
        probInvincible = 0.05f;
        probCure = 0.05f;
        probWeaponUpgrade = 0.2f;
        probSuperpower = 0.02f;
    }

    public BarbetteAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public BarbetteAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public float getHitBottomRightY() {
        return getY() + hitSizeY / 2f;
    }

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        BarbetteAircraft.staticMaxHp = staticMaxHp;
    }
}
