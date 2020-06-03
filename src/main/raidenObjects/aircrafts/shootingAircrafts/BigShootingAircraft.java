package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.PeriodicLaunchCondition;
import main.launchControllers.SimpleLaunchController;
import main.motionControllers.*;
import main.raidenObjects.weapons.BigBullet;
import main.utils.Faction;
import main.utils.InitLocation;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static main.World.*;

/**
 * BigShootingAircraft. A big, heavily-armed aircraft that ruthlessly bombard everything beneath it.
 * Its weapon ({@link BigBullet}) is aimed at the a random location below and then shot.
 * The weapon does not change its course and speed during its life cycle.
 * Its motion is controlled by a {@link TwoStagedMotionController}: the first takes it into the map, and the second
 * (a {@link XYMotionController} created from {@link HoveringXMotionController}) makes it hover around its initial
 * X position.
 *
 * @author 蔡辉宇
 */
public final class BigShootingAircraft extends BaseShootingAircraft {
    private static int defaultMaxHp = 500, staticMaxHp = defaultMaxHp;

    public BigShootingAircraft() {
        super("BigShootingAircraft",100, 65, Faction.ENEMY,
                staticMaxHp, 13, 300, 180);
        ConstSpeedYMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(1, max(0, x - 60f), min(x + 60f, windowWidth));
        MotionController stageTwoController = XYMotionController.createFromXController(
                stageTwoXController, 0.9f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> getWeaponLaunchController().activate()));
        this.registerWeaponLaunchController(new SimpleLaunchController<>(
                "BigShootingAircraft shoots BigBullet",
                new PeriodicLaunchCondition(200, 50, 6, 3),
                () -> {
                    interactantList.add(new BigBullet(getX() - 20, getMaxY() - 5,
                            getX() + rand.nextFloat() * 100 - 50, getY() + windowHeight / 2f + rand.nextFloat() * 150 - 75));
                    interactantList.add(new BigBullet(getX() + 20, getMaxY() - 5,
                            getX() + rand.nextFloat() * 100 - 50, getY() + windowHeight / 2f + rand.nextFloat() * 150 - 75));
                })
        );
        probCoin0 = 0.2f;
        probCoin1 = 0.2f;
        probCoin2 = 0.1f;
        probCure = 0.1f;
        probInvincible = 0.1f;
        probWeaponUpgrade = 0.05f;
        probMagnet = 0.05f;
    }

    public BigShootingAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public BigShootingAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public float getHitTopLeftY() {
        return getY() - 10;
    }

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        BigShootingAircraft.staticMaxHp = staticMaxHp;
    }
}
