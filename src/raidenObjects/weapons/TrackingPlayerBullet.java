package raidenObjects.weapons;

import motionControllers.ConstSpeedTargetTrackingMotionController;
import motionControllers.ConstSpeedXYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

public class TrackingPlayerBullet extends BaseWeapon {
    BaseAircraft target;

    public TrackingPlayerBullet(float x, float y, Faction owner, BaseAircraft target, float initAngle) {
        super("TrackingBullet", x, y, 5, 9, owner, 50);
        this.target = target;
        if (target == null) {
            this.registerMotionController(ConstSpeedXYMotionController.createFromAngle(initAngle, 8f));
            setRotation(initAngle);
        } else {
            this.registerMotionController(new ConstSpeedTargetTrackingMotionController(target,
                    0.8f, 8f));
            setRotation(initAngle);
        }
    }

    @Override
    protected void move() {
        if (target == null) {
            setX(getX() + getSpeedX());
            setY(getY() - getSpeedY());
        } else {
            super.move();
        }
    }

    @Override
    public void step() {
        super.step();
        rotateToFaceTargetAircraft(target);
    }
}
