package raidenObjects.weapons;

import motionControllers.ConstSpeedXYMotionController;
import utils.Faction;

import static java.lang.Math.toRadians;

public final class StandardPlayerBullet extends BaseWeapon {
    public StandardPlayerBullet(float x, float y, Faction owner, float theta) {
        super("StandardPlayerBullet", x, y, 5, 5, owner, 5);
        this.registerMotionController(ConstSpeedXYMotionController.createFromAngle(theta, 25));
        this.setRotation((float) toRadians(theta));
    }

    @Override
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() - getSpeedY());
    }
}
