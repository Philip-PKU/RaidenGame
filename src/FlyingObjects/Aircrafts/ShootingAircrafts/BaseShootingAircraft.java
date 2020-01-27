package FlyingObjects.Aircrafts.ShootingAircrafts;

import FlyingObjects.Aircrafts.BaseAircraft;
import FlyingObjects.Weapons.BaseWeapon;
import org.apache.commons.lang3.mutable.MutableInt;

public class BaseShootingAircraft extends BaseAircraft {
    BaseWeapon weapon;
    public BaseShootingAircraft(int sizeX, int sizeY, int x, int y, int maxSpeedX, int maxSpeedY, MutableInt gameStep,
                                int maxHP, int maxStepsAfterDeath, String name,
                                BaseWeapon weapon) {
        super(sizeX, sizeY, x, y, maxSpeedX, maxSpeedY, gameStep, maxHP, maxStepsAfterDeath, name);
        this.weapon = weapon;
    }

    public BaseWeapon getWeapon() {
        return weapon;
    }

    void setWeapon(BaseWeapon weapon) {
        this.weapon = weapon;
    }

    public void step() {
        if (isAlive()) {
            move();
            shootWeapon();
        }
    }

    public void move() {
        if (gameStep.intValue() % 10 == 0)
            setY(getY() + (int)getMaxSpeedY());
    }

    public void shootWeapon() {
        weapon.shoot();
    }
}
