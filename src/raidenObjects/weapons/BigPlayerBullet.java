package raidenObjects.weapons;

import motionControllers.ConstAccelerationXMotionController;
import motionControllers.XYMotionController;
import utils.Faction;

import static java.lang.Math.atan;

public final class BigPlayerBullet extends BaseWeapon {
    private static int staticDamage = 28;

    public BigPlayerBullet(float x, float y, Faction owner, float direction) {
        super("BigPlayerBullet", x, y, 5, 5, owner, staticDamage);
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

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
