package raidenObjects.weapons;

import motionControllers.ConstSpeedXYMotionController;
import utils.Faction;

import static java.lang.Math.toRadians;

public final class StandardPlayerBullet extends BaseWeapon {
    private static int staticDamage = 5;

    public StandardPlayerBullet(float x, float y, Faction owner, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, owner, staticDamage);
        this.registerMotionController(ConstSpeedXYMotionController.createFromAngle(180 - theta, 25));
        this.setRotation((float) toRadians(theta));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
