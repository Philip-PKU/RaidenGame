package raidenObjects.weapons;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import utils.Faction;

public final class SmallBullet extends BaseWeapon {
    private static int staticDamage = 10;

    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.createFromXY(
                x, y, targetX, targetY, 5));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
