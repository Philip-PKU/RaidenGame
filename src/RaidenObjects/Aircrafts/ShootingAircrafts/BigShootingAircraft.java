package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.BigBullet;
import RaidenObjects.Weapons.SmallBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.*;

public class BigShootingAircraft extends BaseShootingAircraft {
    static float maxSpeedX = 1.0f;

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 60, 40, 0.5f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                500, 13, 200, 108);
    }

    public void shootWeapon() {
        if ((gameStep.intValue() - gameStepWhenBorn) % getWeaponCoolDown() == 0) {
            new SmallBullet(getX() + getSizeX() / 2.0f, getY() + getSizeY());
            if (player1 != null)
                new BigBullet(getX() + getSizeX() / 2.0f, getY() + getSizeY(), player1.getX(), player1.getY());
            if (player2 != null)
                new BigBullet(getX() + getSizeX() / 2.0f, getY() + getSizeY(), player2.getX(), player2.getY());
        }
    }

    public void move() {
        setY(getY() + getMaxSpeed());
        if ((gameStep.intValue() - gameStepWhenBorn) % 100 < 50 && !isOutOfWorld(getX() - maxSpeedX, getY()))
            setX(getX() - maxSpeedX);
        else if (!isOutOfWorld(getX() + maxSpeedX, getY()))
            setX(getX() + maxSpeedX);
    }
}
