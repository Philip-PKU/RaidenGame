package main.raidenObjects.weapons;

import main.motionControllers.ConstAccelerationTargetTrackingMotionController;
import main.utils.Faction;


/**
 * BarbetteBullet. A huge-damage, slow weapon that makes the weak retreat or bombard the bold to death.
 * It moves slightly towards the closest player, and stops changing course once it reaches maximum speed.
 *
 * @author 张哲瑞
 */
public final class BarbetteBullet extends BaseWeapon {
    private static int staticDamage = 89;

    /**
     * Constructor.
     *
     * @param x Initial X coordinate.
     * @param y Initial Y coordinate.
     */
    public BarbetteBullet(float x, float y) {
        super("BarbetteBullet", x, y, 15, 15,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(new ConstAccelerationTargetTrackingMotionController(
                getClosestPlayer(), 0.08f, 1.5f, 2.8f));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
