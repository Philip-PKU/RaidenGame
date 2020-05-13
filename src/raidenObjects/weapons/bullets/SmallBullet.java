package raidenObjects.weapons.bullets;

import raidenObjects.weapons.BaseWeapon;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

public final class SmallBullet extends BaseWeapon {

    public SmallBullet(float x, float y) {
        super("SmallBullet", x, y, 5, 5, 6,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 5);
    }

    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5, 6,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 5, targetX, targetY);
    }
}
