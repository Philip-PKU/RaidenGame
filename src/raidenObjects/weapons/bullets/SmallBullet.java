package raidenObjects.weapons.bullets;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import raidenObjects.weapons.BaseWeapon;
import utils.RaidenObjectOwner;

public final class SmallBullet extends BaseWeapon {

    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5,
                RaidenObjectOwner.BOSS, 5);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.fromTargetXY(
                x, y, targetX, targetY, 5));
    }
}
