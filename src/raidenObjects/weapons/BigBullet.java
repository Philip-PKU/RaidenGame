package raidenObjects.weapons;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import utils.Faction;

public final class BigBullet extends BaseWeapon {
    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10,
                Faction.ENEMY, 49);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.createFromXY(
                x, y, targetX, targetY, 4.5f));
    }
}
