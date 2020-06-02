package main.raidenObjects.weapons;

import main.motionControllers.ConstSpeedXYTargetAwareMotionController;
import main.utils.Faction;

/**
 * SmallBullet. A small-damage, fast weapon. Built for surprise attacks!
 * It goes straight for the set target.
 */
public final class SmallBullet extends BaseWeapon {
    private static int staticDamage = 10;

    /**
     * Constructor.
     *
     * @param x       Initial X coordinate.
     * @param y       Initial Y coordinate.
     * @param targetX Target X coordinate.
     * @param targetY Target Y coordinate.
     */
    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(ConstSpeedXYTargetAwareMotionController.createFromXY(
                x, y, targetX, targetY, 5));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
