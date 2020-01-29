package RaidenObjects.Weapons;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

public class HeroBullet extends BaseWeapon {
    public HeroBullet(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller, float theta) {
        super("HeroBullet", x, y, 5, 5, 30, owner, controller, 10, theta);
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
