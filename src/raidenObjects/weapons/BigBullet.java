package raidenObjects.weapons;

import motionControllers.TargetAwareConstSpeedXYMotionController;
import utils.Faction;

public final class BigBullet extends BaseWeapon {
    private static int staticDamage = 49;
    public BigBullet(float x, float y, float targetX, float targetY) {
        super("BigBullet", x, y, 10, 10,
                Faction.ENEMY, staticDamage);
        this.registerMotionController(TargetAwareConstSpeedXYMotionController.createFromXY(
                x, y, targetX, targetY, 4.5f));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
