package raidenObjects.weapons;

import motionControllers.ConstAccelerationTargetTrackingMotionController;
import utils.Faction;


public final class BarbetteBullet extends BaseWeapon {
    private static int staticDamage = 89;
	public BarbetteBullet(float x, float y, float targetX, float targetY) {
        super("BarbetteBullet", x, y, 15, 15,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(new ConstAccelerationTargetTrackingMotionController(
                getClosestPlayer(), 1.5f, 1.5f, 3.5f));
    }

    public static int getStaticDamage() {
	    return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
	    staticDamage = staticDamage1;
    }
}
