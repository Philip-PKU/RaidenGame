package raidenObjects.weapons.bullets;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import raidenObjects.weapons.BaseWeapon;
import utils.Faction;


public final class BarbetteBullet extends BaseWeapon {
	public BarbetteBullet(float x, float y, float targetX, float targetY) {
        super("BarbetteBullet", x, y, 15, 15,
                Faction.ENEMY, 25);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.fromTargetXY(
                x, y, targetX, targetY, 3.5f));
    }
}
