package raidenObjects.weapons;

import motionControllers.ConstSpeedTargetTrackingMotionController;
import motionControllers.ConstSpeedXYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

/**
 * TrackingPlayerBullet. A small yet effective killing device that knows the enemy and how to track them down.
 * When no target is found, it simply flies at preset angle (controlled by {@link motionControllers.ConstSpeedXYTargetAwareMotionController}).
 * When a target is given, it tracks the target in full speed (controlled by {@link ConstSpeedTargetTrackingMotionController}).
 *
 * @see raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.TrackingBulletLaunchController
 *
 * @author 蔡辉宇
 */
public class TrackingPlayerBullet extends BaseWeapon {
    private static int staticDamage = 30;
    BaseAircraft target;

    public TrackingPlayerBullet(float x, float y, Faction faction, BaseAircraft target, float initAngle) {
        super("TrackingBullet", x, y, 5, 9, faction, staticDamage);
        this.target = target;
        if (target == null) {
            this.registerMotionController(ConstSpeedXYMotionController.createFromAngle(180 - initAngle, 8f));
            setRotation(initAngle);
        } else {
            this.registerMotionController(new ConstSpeedTargetTrackingMotionController(target,
                    0.8f, 8f, 180 - initAngle));
            setRotation(initAngle);
        }
    }

    @Override
    public void step() {
        super.step();
        rotateToFaceTargetAircraft(target);
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage1) {
        staticDamage = staticDamage1;
    }
}
