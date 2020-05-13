package raidenObjects.weapons.bullets;

import raidenObjects.weapons.BaseWeapon;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

public final class BigBullet extends BaseWeapon {
    public BigBullet(float x, float y) {
        super("BigBullet", x, y, 10, 10, 5,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 15);
    }

    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10, 5,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 15, targetX, targetY);
    }
}
