package raidenObjects.weapons.bullets;

import motionControllers.ConstSpeedXYMotionController;
import raidenObjects.weapons.BaseWeapon;
import utils.Faction;

public final class BigPlayerBullet extends BaseWeapon {
    public BigPlayerBullet(float x, float y, Faction owner, float theta) {
        super("BigPlayerBullet", x, y, 5, 5, owner, 1000);
        this.registerMotionController(ConstSpeedXYMotionController.fromTargetAngle(theta, 10));
    }

    @Override
    protected void moveAndCheckPosition() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
