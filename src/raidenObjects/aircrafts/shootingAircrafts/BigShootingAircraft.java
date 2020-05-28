package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchCondition;
import launchControllers.SimpleLaunchController;
import motionControllers.*;
import raidenObjects.weapons.BigBullet;
import utils.Faction;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static world.World.*;

public final class BigShootingAircraft extends BaseShootingAircraft {
    private static int staticMaxHp = 500;

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 100, 65, Faction.ENEMY,
                staticMaxHp, 13, 300, 180);
        YAwareMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(1, max(0, x - 60f), min(x + 60f, windowWidth));
        MotionController stageTwoController = XYMotionController.createFromXController(
                stageTwoXController, 0.5f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> getWeaponLaunchController().activate()));
        this.registerWeaponLaunchController(new SimpleLaunchController<>(
                "BigShootingAircraft shoots BigBullet",
                new PeriodicLaunchCondition(150, 50, 6, 3),
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

    @Override
    public float getHitTopLeftY() {
        return getY() - 10;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        BigShootingAircraft.staticMaxHp = staticMaxHp;
    }
}
