package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.SmallBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.gameStep;

public class SmallShootingAircraft extends BaseShootingAircraft {
    static float maxSpeedX = 3.0f;

    public SmallShootingAircraft(float x, float y) {
        super("SmallShootingAircraft", x, y, 35, 21, 0.3f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                100, 13, 50, 76, 30);
    }

    public void shootWeapon() {
        int gameStepSinceBirth = gameStep.intValue() - gameStepAtBirth;
        if (gameStepSinceBirth % getWeaponCoolDown() == 0) {
            new SmallBullet(getX() - 10, getMaxY());
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
