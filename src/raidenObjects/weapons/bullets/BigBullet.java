package raidenObjects.weapons.bullets;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import raidenObjects.weapons.BaseWeapon;
import utils.Faction;

public final class BigBullet extends BaseWeapon {
    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10,
                Faction.ENEMY, 15);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.fromTargetXY(
                x, y, targetX, targetY, 4.5f));
    }
}
