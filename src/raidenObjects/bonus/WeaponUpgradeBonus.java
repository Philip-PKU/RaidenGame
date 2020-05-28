package raidenObjects.bonus;

import launchControllers.PeriodicLaunchCondition;
import launchControllers.SimpleLaunchController;
import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;
import utils.InitLocation;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Bonus that gives the player weapon updates.
 * During its float in space, it automatically and periodically changes type, so that players can get their desired
 * weapon upgrade by waiting for the right type to appear before picking up this bonus.
 *
 * @see PlayerAircraft#updateWeapon(int)
 * @see PlayerAircraft.BigBulletLaunchController
 * @see PlayerAircraft.StandardBulletLaunchScheduler
 * @see PlayerAircraft.TrackingBulletLaunchController
 *
 * @author 唐宇豪
 */
public final class WeaponUpgradeBonus extends BaseBonus {
    /**
     * The indices of current weapon in the weapons list.
     * Can be referenced by other classes when calling the {@link CoinBonus} constructor.
     */
    public static int UPDATE_WEAPON_NONE = 0, UPDATE_WEAPON_STANDARD = 1, UPDATE_WEAPON_BIG = 2, UPDATE_WEAPON_TRACKING = 3;
    public static int[] weapons = {UPDATE_WEAPON_STANDARD, UPDATE_WEAPON_BIG, UPDATE_WEAPON_TRACKING};
    SimpleLaunchController<PeriodicLaunchCondition> updateTypeController;
    int weapon, idx;

    public WeaponUpgradeBonus(int type) {
        super("WeaponUpgradeBonus",20, 20, Faction.BONUS);
        weapon = weapons[type];
        idx = type;
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 0.7f);
        this.registerMotionController(XYController);
        this.registerUpdateTypeController(new SimpleLaunchController<>(
                "WeaponUpgradeBonus changes type",
                new PeriodicLaunchCondition(200, 200),
                () -> {
                    ++idx;
                    if (idx == weapons.length)
                        idx = 0;
                    weapon = weapons[idx];
                }), true);
    }

    public WeaponUpgradeBonus(float x, float y, int type) {
        this(type);
        setX(x);
        setY(y);
    }

    public WeaponUpgradeBonus(InitLocation initLocation, int type) {
        this(type);
        initXFromLocation(initLocation);
    }

    public WeaponUpgradeBonus(float x, float y) {
        this(x, y, rand.nextInt(weapons.length));
    }

    public WeaponUpgradeBonus(InitLocation initLocation) {
        this(initLocation, rand.nextInt(weapons.length));
    }

    @Override
    public File getImageFile() {
        int curCooldown = getUpdateTypeController().getLaunchCondition().getCurCooldown();
        if (curCooldown <= desiredFPS && (curCooldown / (desiredFPS / 10)) % 2 == 0)
            return null;
        return Paths.get("data", "images", getName() + weapon + ".png").toFile();
    }

    public SimpleLaunchController<PeriodicLaunchCondition> getUpdateTypeController() {
        return updateTypeController;
    }

    public void registerUpdateTypeController(SimpleLaunchController<PeriodicLaunchCondition> updateTypeController,
                                             boolean activateNow) {
        this.updateTypeController = updateTypeController;
        if (activateNow)
            updateTypeController.activate();
    }


    @Override
    public void bonus(PlayerAircraft aircraft) {
        aircraft.updateWeapon(weapon);
    }

    /**
     * This bonus cannot be attracted. Otherwise, player may end up having a weapon they don't want.
     */
    @Override
    public void becomesAttracted() {
    }

    @Override
    public void step() {
        super.step();
        if (isAlive())
            getUpdateTypeController().launchIfPossible();
    }
}
