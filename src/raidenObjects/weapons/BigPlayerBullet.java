package raidenObjects.weapons;

import motionControllers.ConstAccelerationXMotionController;
import motionControllers.XYMotionController;
import utils.Faction;

import static java.lang.Math.atan;

/**
 * BigPlayerBullet. A heavily-focused, high-damage, moderate-fire-rate weapon.
 * It has constant speed in the Y direction, and moves with the player aircraft in the X direction with constant
 * acceleration. It is controlled by a {@link XYMotionController} which consists of a
 * {@link ConstAccelerationXMotionController} and a {@link motionControllers.ConstSpeedYMotionController}.
 *
 * @see raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.BigBulletLaunchController
 *
 * @author 唐宇豪 蔡辉宇
 */
public final class BigPlayerBullet extends BaseWeapon {
    private static int defaultDamage = 28, staticDamage = defaultDamage;

    /**
     * Constructor.
     *
     * @param x         Initial X coordinate.
     * @param y         Initial Y coordinate.
     * @param faction   Faction of the bullet.
     * @param direction Direction of the bullet. Can only be -1, 0 or 1, which indicates left, middle (no X motion) and
     *                  right, respectively.
     */
    public BigPlayerBullet(float x, float y, Faction faction, float direction) {
        super("BigPlayerBullet", x, y, 5, 5, faction, staticDamage);
        this.registerMotionController(XYMotionController.createFromXController(
                new ConstAccelerationXMotionController(0, direction * 0.3f),
                12f
        ));
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
        if (getSpeedX() != 0)
            setRotation((float) atan(getSpeedX() / getSpeedY()));
    }

    public static int getDefaultDamage() {
        return defaultDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
