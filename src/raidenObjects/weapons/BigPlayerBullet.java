package raidenObjects.weapons;

import motionControllers.ConstAccelerationYMotionController;
import utils.Faction;

public final class BigPlayerBullet extends BaseWeapon {
    public BigPlayerBullet(float x, float y, Faction owner) {
        super("BigPlayerBullet", x, y, 5, 5, owner, 20);
        this.registerMotionController(new ConstAccelerationYMotionController(6f, 0.05f));
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
