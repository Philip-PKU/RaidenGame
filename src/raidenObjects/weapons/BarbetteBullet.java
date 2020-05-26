package raidenObjects.weapons;

import motionControllers.ConstAccelerationTargetTrackingMotionController;
import utils.Faction;


public final class BarbetteBullet extends BaseWeapon {
	public BarbetteBullet(float x, float y, float targetX, float targetY) {
        super("BarbetteBullet", x, y, 15, 15,
                Faction.ENEMY, 25);
        this.registerMotionController(new ConstAccelerationTargetTrackingMotionController(
                getClosestPlayer(), 0.05f, 1.5f, 3.5f));
    }
}
