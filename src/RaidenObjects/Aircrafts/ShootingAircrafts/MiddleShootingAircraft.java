package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.SmallBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.gameStep;

public class MiddleShootingAircraft extends BaseShootingAircraft {
    static float maxSpeedX = 2.0f;

    public MiddleShootingAircraft(float x, float y) {
        super("MiddleShootingAircraft", x, y, 60, 40, 0.5f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                200, 13, 150, 256, 50);
    }

    public void shootWeapon() {
        int gameStepSinceBirth = gameStep.intValue() - gameStepAtBirth;
        if (gameStepSinceBirth % getWeaponCoolDown() < 15 && gameStepSinceBirth % 5 == 0) {
            PlayerAircraft closestPlayer = getClosestPlayer();
            new SmallBullet(getX() - 10, getMaxY(),
                    closestPlayer.getX(), closestPlayer.getY());
            new SmallBullet(getX() + 10, getMaxY(),
                    closestPlayer.getX(), closestPlayer.getY());
        }
    }

    @Override
    protected void initSpeed() {
        super.initSpeed();
        setSpeedY(getMaxSpeed());
        if ((gameStep.intValue() - gameStepAtBirth) % 100 < 50 && !isOutOfWorld(getX() - maxSpeedX, getY()))
            setSpeedX(-maxSpeedX);
        else if (!isOutOfWorld(getX() + maxSpeedX, getY()))
            setSpeedX(maxSpeedX);
    }
}
