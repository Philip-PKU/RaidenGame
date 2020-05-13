package raidenObjects.aircrafts.shootingAircrafts;

import motionControllers.*;
import raidenObjects.weapons.bullets.SmallBullet;
import utils.RaidenObjectOwner;

import static world.World.gameStep;
import static world.World.windowWidth;

public final class MiddleShootingAircraft extends BaseShootingAircraft {

    public MiddleShootingAircraft(float x, float y) {
        super("MiddleShootingAircraft", x, y, 60, 40, RaidenObjectOwner.BOSS,
                200, 13, 150,
                192, 10);
        YAwareMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(0.3f, getSizeX() / 2f, windowWidth - getSizeX() / 2f);
        MotionController stageTwoController = XYMotionController.defaultFromXController(
                stageTwoXController, 0.8f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> hasReachedTargetPos = true));
    }

    public void shootWeapon() {
        int gameStepSinceReady = gameStep.intValue() - gameStepWhenReady - getInitWeaponCoolDown();
        if (gameStepSinceReady % getWeaponCoolDown() < 18 && gameStepSinceReady % 6 == 0) {
            PlayerAircraft closestPlayer = getClosestPlayer();
            new SmallBullet(getX() - 10, getMaxY(),
                    closestPlayer.getX(), closestPlayer.getY());
            new SmallBullet(getX() + 10, getMaxY(),
                    closestPlayer.getX(), closestPlayer.getY());
        }
    }
}
