package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchController;
import motionControllers.*;
import raidenObjects.weapons.bullets.BigBullet;
import utils.Faction;

import java.util.Arrays;

import static world.World.*;

public final class BigShootingAircraft extends BaseShootingAircraft{

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 100, 65, Faction.ENEMY,
                500, 13, 300);
        YAwareMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(1, x - 60f, x + 60f);
        MotionController stageTwoController = XYMotionController.defaultFromXController(
                stageTwoXController, 0.5f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> weaponLaunchController.activate()));
        this.registerWeaponLaunchController(new PeriodicLaunchController(150, 50,
                () -> {
                    interactantList.add(new BigBullet(getX() - 20, getMaxY() - 5,
                            getX() + rand.nextFloat() * 100 - 50, getY() + windowHeight / 2f + rand.nextFloat() * 150 - 75));
                    interactantList.add(new BigBullet(getX() + 20, getMaxY() - 5,
                            getX() + rand.nextFloat() * 100 - 50, getY() + windowHeight / 2f + rand.nextFloat() * 150 - 75));
                },
                Arrays.asList(0, 6, 12)));
    }
}
