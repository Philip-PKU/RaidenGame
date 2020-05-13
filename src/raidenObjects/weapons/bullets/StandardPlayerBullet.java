package raidenObjects.weapons.bullets;

import motionControllers.ConstSpeedXYMotionController;
import raidenObjects.weapons.BaseWeapon;
import utils.RaidenObjectOwner;

public final class StandardPlayerBullet extends BaseWeapon {
    public StandardPlayerBullet(float x, float y, RaidenObjectOwner owner, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, owner, 5);
        this.registerMotionController(ConstSpeedXYMotionController.fromTargetAngle(theta, 25));
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
