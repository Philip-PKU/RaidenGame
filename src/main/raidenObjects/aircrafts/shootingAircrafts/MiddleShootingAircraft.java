package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.PeriodicLaunchCondition;
import main.launchControllers.SimpleLaunchController;
import main.motionControllers.*;
import main.raidenObjects.weapons.SmallBullet;
import main.utils.Faction;
import main.utils.InitLocation;

import static main.world.World.*;

/**
 * MiddleShootingAircraft. A middle-sized, player-aiming aircraft skilled at limiting the enemy's position.
 * Its weapon ({@link SmallBullet}) is shot to locations very close to the player, and does not change course after launch.
 * Its motion is controlled by a {@link TwoStagedMotionController}: the first takes it into the map, and the second
 * (a {@link XYMotionController} created from {@link HoveringXMotionController}) makes it hover around, moving across
 * the whole map in the X direction.
 *
 * @author 蔡辉宇
 */
public final class MiddleShootingAircraft extends BaseShootingAircraft {
    private static int defaultMaxHp = 200, staticMaxHp = defaultMaxHp;

    public MiddleShootingAircraft() {
        super("MiddleShootingAircraft",60, 40, Faction.ENEMY,
                200, 13, 150, 120);
        ConstSpeedYMotionController stageOneController = new ConstSpeedYMotionController(5);
        MotionController stageTwoXController = new HoveringXMotionController(0.3f,
                getImgSizeX() / 2f, windowWidth - getImgSizeX() / 2f);
        MotionController stageTwoController = XYMotionController.createFromXController(
                stageTwoXController, 1f);
        this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
                () -> getY() > 80,
                () -> getWeaponLaunchController().activate()));
        this.registerWeaponLaunchController(new SimpleLaunchController<>(
                "MiddleShootingAircraft shoots SmallBullet",
                new PeriodicLaunchCondition(192, 50, 6, 3),
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

    public MiddleShootingAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public MiddleShootingAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public float getHitTopLeftY() {
        return getY() - 51;
    }

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
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
