package raidenObjects.aircrafts.shootingAircrafts;

import motionControllers.*;
import raidenObjects.weapons.bullets.BigBullet;
import utils.RaidenObjectOwner;

import static world.World.*;

public final class BigShootingAircraft extends BaseShootingAircraft{

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 100, 65, RaidenObjectOwner.BOSS,
                500, 13, 300,
                150, 50);
        YAwareMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(1, x - 60f, x + 60f);
        MotionController stageTwoController = XYMotionController.defaultFromXController(
                stageTwoXController, 0.5f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> hasReachedTargetPos = true));
    }

    public void shootWeapon() {
        int gameStepSinceReady = gameStep.intValue() - gameStepWhenReady - getInitWeaponCoolDown();
        if (gameStepSinceReady % getWeaponCoolDown() < 18 && gameStepSinceReady % 6 == 0) {
                new BigBullet(getX() - 20, getMaxY() - 5,
                        getX() + rand.nextFloat() * 100, getY() + windowHeight/2f + rand.nextFloat() * 100);
                new BigBullet(getX() + 20, getMaxY() - 5,
                        getX() + rand.nextFloat() * 100, getY() + windowHeight/2f + rand.nextFloat() * 100);
        }
    }
}
