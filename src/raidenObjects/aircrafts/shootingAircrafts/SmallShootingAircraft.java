package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import launchControllers.PeriodicLaunchEventScheduler;
import motionControllers.ConstSpeedYMotionController;
import motionControllers.TargetAwareConstSpeedXYMotionController;
import motionControllers.TwoStagedMotionController;
import raidenObjects.weapons.SmallBullet;
import utils.Faction;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static world.World.*;

public final class SmallShootingAircraft extends BaseShootingAircraft {
    private static int staticMaxHp = 100;

    public SmallShootingAircraft(float x, float y) {
        super("SmallShootingAircraft", x, y, 35, 21, Faction.ENEMY,
                staticMaxHp, 13, 50, 50);
        PlayerAircraft target = getClosestPlayer();
        TargetAwareConstSpeedXYMotionController stageOneController = TargetAwareConstSpeedXYMotionController.createFromXY(
                x,
                y,
                target.getX() > windowWidth / 2f ? rand.nextInt(windowWidth / 2) : windowWidth - rand.nextInt(windowWidth / 2),
                max(min(windowHeight * 2f / 3f, target.getY() + rand.nextFloat() * 200f - 100f), windowHeight / 3f),
                7);
        this.registerMotionController(new TwoStagedMotionController(
                stageOneController,
                new ConstSpeedYMotionController(0.4f),
                () -> stageOneController.distToTarget() < stageOneController.getMaxSpeed() || distTo(getClosestPlayer()) < 150,
                () -> getWeaponLaunchController().activate()
        ));
        this.registerWeaponLaunchController(new LaunchController<>(
                new PeriodicLaunchEventScheduler(126, 60),
                () -> interactantList.add(new SmallBullet(getX(), getMaxY(), target.getX(), target.getY()))
        ));
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        SmallShootingAircraft.staticMaxHp = staticMaxHp;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            rotateToFaceTargetAircraft(getClosestPlayer());
        }
    }
}