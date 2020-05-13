package raidenObjects.Aircrafts.shootingAircrafts;

import raidenObjects.weapons.bullets.SmallBullet;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

import static world.World.*;

public final class MiddleShootingAircraft extends BaseShootingAircraft {
    private static float maxSpeedX = 0.2f, targetY = 80f;
    boolean movingLeft;

    public MiddleShootingAircraft(float x, float y) {
        super("MiddleShootingAircraft", x, y, 60, 40, 0.8f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                200, 13, 150,
                192, 10, 5f);
        this.movingLeft = rand.nextBoolean();
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

    @Override
    protected void updateSpeed() {
        super.updateSpeed();
        if (!hasReachedTargetPos) {
            if (getY() > targetY) {
                hasReachedTargetPos = true;
                gameStepWhenReady = gameStep.intValue();
            }
            else {
                setSpeedY(getInitMaxSpeed());
                return;
            }
        }
        setSpeedY(getMaxSpeed());

        // x-direction: move across the map
        if (movingLeft) {
            if (getX() > getSizeX())
                setSpeedX(-maxSpeedX);
            else movingLeft = false;
        }
        else {
            if (getX() < windowWidth - getSizeX())
                setSpeedX(maxSpeedX);
            else movingLeft = true;
        }
    }
}
