package raidenObjects.weapons.bullets;

import raidenObjects.weapons.BaseWeapon;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

public final class StandardPlayerBullet extends BaseWeapon {
    public StandardPlayerBullet(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, 30, owner, controller, 6, theta);
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
