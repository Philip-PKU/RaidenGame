package RaidenObjects.Weapons;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

public class StandardPlayerBullet extends BaseWeapon {
    public StandardPlayerBullet(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, 30, owner, controller, 5, theta);
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
