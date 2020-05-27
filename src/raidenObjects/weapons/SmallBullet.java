package raidenObjects.weapons;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import utils.Faction;

public final class SmallBullet extends BaseWeapon {

    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5,
                Faction.ENEMY, 10);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.createFromXY(
                x, y, targetX, targetY, 5));
    }
}
