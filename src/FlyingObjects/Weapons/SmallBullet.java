package FlyingObjects.Weapons;

import org.apache.commons.lang3.mutable.MutableInt;

public class SmallBullet extends BaseWeapon {
    public SmallBullet(int x, int y, MutableInt gameStep) {
        super(5, 5, x, y, 1, 1, gameStep, 1);
    }
    public String getImagePath() {
        return null;
    }
    public void step() {

    }
}
