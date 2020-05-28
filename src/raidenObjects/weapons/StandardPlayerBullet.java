package raidenObjects.weapons;

import motionControllers.ConstSpeedXYMotionController;
import utils.Faction;

import static java.lang.Math.toRadians;

/**
 * StandardPlayerBullet. A moderate-damage, high-firing-rate and high-coverage weapon.
 * It is controlled by a {@link motionControllers.ConstSpeedXYTargetAwareMotionController}.
 *
 * @see raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.StandardBulletLaunchScheduler
 *
 * @author 蔡辉宇
 */
public final class StandardPlayerBullet extends BaseWeapon {
    private static int staticDamage = 5;

    /**
     * Constructor.
     *
     * @param x       Initial X coordinate.
     * @param y       Initial Y coordinate.
     * @param faction Faction of the bullet.
     * @param theta   Angle to shoot at. 0 means straight up.
     */
    public StandardPlayerBullet(float x, float y, Faction faction, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, faction, staticDamage);
        // Here 0 angle means straight down, so we need to do a transformation
        this.registerMotionController(ConstSpeedXYMotionController.createFromAngle(180 - theta, 30));
        this.setRotation((float) toRadians(theta));
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
