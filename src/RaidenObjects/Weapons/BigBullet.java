package RaidenObjects.Weapons;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

public class BigBullet extends BaseWeapon {

    public BigBullet(float x, float y) {
        super("BigBullet", x, y, 10, 10, 3,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 5);
    }

    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10, 3,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 5, targetX, targetY);
    }
}
