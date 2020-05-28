package raidenObjects.bonus;

import launchControllers.PeriodicLaunchCondition;
import launchControllers.SimpleLaunchController;
import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.*;
import static world.World.*;

public final class WeaponUpgradeBonus extends BaseBonus {
    public static int[] weapons = {UPDATE_WEAPON_MULTI, UPDATE_WEAPON_SINGLE, UPDATE_WEAPON_TRACKING};
    SimpleLaunchController<PeriodicLaunchCondition> updateTypeController;
    int weapon, idx;

    public WeaponUpgradeBonus(float x, float y, int type) {
        super("WeaponUpgradeBonus", x, y, 20, 20, Faction.BONUS);
        weapon = weapons[type];
        idx = type;
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1f);
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

    public WeaponUpgradeBonus(float x, float y) {
        this(x, y, rand.nextInt(weapons.length));
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
