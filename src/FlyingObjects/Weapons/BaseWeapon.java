package FlyingObjects.Weapons;

import FlyingObjects.BaseFlyingObject;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class BaseWeapon extends BaseFlyingObject {
    int damage;
    BaseWeapon(int sizeX, int sizeY, int x, int y, float maxSpeedX, float maxSpeedY, MutableInt gameStep,
               int damage) {
        super(sizeX, sizeY, x, y, maxSpeedX, maxSpeedY, gameStep);
        this.damage = damage;
    }

    public void shoot() {

    }
}
