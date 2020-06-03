package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.PeriodicLaunchCondition;
import main.launchControllers.SimpleLaunchController;
import main.motionControllers.ConstSpeedXYTargetAwareMotionController;
import main.motionControllers.ConstSpeedYMotionController;
import main.motionControllers.TwoStagedMotionController;
import main.raidenObjects.weapons.SmallBullet;
import main.utils.Faction;
import main.utils.InitLocation;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static main.World.*;

/**
 * SmallShootingAircraft. A tiny aircraft that trumps in surprise attacks.
 * Its weapon ({@link SmallBullet}) is shot straight at the player, and does not change course after launch.
 * Its motion is controlled by a {@link TwoStagedMotionController}: the first
 * (a {@link ConstSpeedXYTargetAwareMotionController}) takes it near the player in terms of the Y coordinate, but
 * away from the player in terms of the X coordinate; and the second (a {@link ConstSpeedYMotionController})
 * makes it go straight south.
 *
 * @author 蔡辉宇
 */
public final class SmallShootingAircraft extends BaseShootingAircraft {
    private static int defaultMaxHp = 100, staticMaxHp = defaultMaxHp;

    SmallShootingAircraft() {
        super("SmallShootingAircraft",35, 21, Faction.ENEMY,
                staticMaxHp, 13, 50, 50);
        probCoin0 = 0.05f;
        probCoin1 = 0.05f;
        probCoin2 = 0.05f;
        probMagnet = 0.05f;
    }

    public SmallShootingAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
        init();
    }

    public SmallShootingAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
        init();
    }

    void init() {
        PlayerAircraft target = getClosestPlayer();
        ConstSpeedXYTargetAwareMotionController stageOneController = ConstSpeedXYTargetAwareMotionController.createFromXY(
                getX(),
                getY(),
                target.getX() > windowWidth / 2f ? rand.nextInt(windowWidth / 2) : windowWidth - rand.nextInt(windowWidth / 2),
                max(min(windowHeight * 2f / 3f, target.getY() + rand.nextFloat() * 200f - 100f), windowHeight / 3f),
                7);
        this.registerMotionController(new TwoStagedMotionController(
                stageOneController,
                new ConstSpeedYMotionController(0.7f),
                () -> stageOneController.distToTarget() < stageOneController.getConstSpeed() || distTo(getClosestPlayer()) < 150,
                () -> getWeaponLaunchController().activate()
        ));
        this.registerWeaponLaunchController(new SimpleLaunchController<>(
                "SmallShootingAircraft shoots SmallBullet",
                new PeriodicLaunchCondition(126, 30),
                () -> interactantList.add(new SmallBullet(getX(), getMaxY(), target.getX(), target.getY()))
        ));
    }

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
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