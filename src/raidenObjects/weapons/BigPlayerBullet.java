package raidenObjects.weapons;

import motionControllers.ConstAccelerationYMotionController;
import utils.Faction;

public final class BigPlayerBullet extends BaseWeapon {
    public BigPlayerBullet(float x, float y, Faction owner) {
        super("BigPlayerBullet", x, y, 5, 5, owner, 15);
        this.registerMotionController(new ConstAccelerationYMotionController(5f, 0.03f));
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
