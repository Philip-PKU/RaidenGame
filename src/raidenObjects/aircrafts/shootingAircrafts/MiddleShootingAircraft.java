package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import launchControllers.PeriodicLaunchEventScheduler;
import motionControllers.*;
import raidenObjects.weapons.SmallBullet;
import utils.Faction;

import static world.World.*;

public final class MiddleShootingAircraft extends BaseShootingAircraft {
    private static int staticMaxHp = 200;

    public MiddleShootingAircraft(float x, float y) {
        super("MiddleShootingAircraft", x, y, 60, 40, Faction.ENEMY,
                200, 13, 150, 120);
        YAwareMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(0.3f,
                getImgSizeX() / 2f, windowWidth - getImgSizeX() / 2f);
        MotionController stageTwoController = XYMotionController.createFromXController(
                stageTwoXController, 1f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> getWeaponLaunchController().activate()));
        this.registerWeaponLaunchController(new LaunchController<>(
                new PeriodicLaunchEventScheduler(192, 14, 6, 3),
                () -> {
                    PlayerAircraft closestPlayer = getClosestPlayer();
                    interactantList.add(new SmallBullet(getX() - 10, getMaxY(),
                            closestPlayer.getX() + rand.nextFloat() * 20 - 10,
                            closestPlayer.getY() + rand.nextFloat() * 30 - 15));
                    interactantList.add(new SmallBullet(getX() + 10, getMaxY(),
                            closestPlayer.getX() + rand.nextFloat() * 20 - 10,
                            closestPlayer.getY() + rand.nextFloat() * 30 - 15));
                })
        );
    }

    @Override
    public float getHitTopLeftY() {
        return getY() - 51;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        MiddleShootingAircraft.staticMaxHp = staticMaxHp;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            rotateToFaceTargetAircraft(getClosestPlayer());
        }
    }
}
