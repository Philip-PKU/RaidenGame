package main.raidenObjects.weapons;

import main.motionControllers.ConstSpeedXYTargetAwareMotionController;
import main.utils.Faction;

/**
 * BigBullet. A high-damage, moderately fast weapon. Your choice for conquering the galaxy.
 * It goes straight for the set target.
 *
 * @author 蔡辉宇
 */
public final class BigBullet extends BaseWeapon {
    private static int staticDamage = 50;

    /**
     * Constructor.
     *
     * @param x       Initial X coordinate.
     * @param y       Initial Y coordinate.
     * @param targetX Target X coordinate.
     * @param targetY Target Y coordinate.
     */
    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(ConstSpeedXYTargetAwareMotionController.createFromXY(
                x, y, targetX, targetY, 4f));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
