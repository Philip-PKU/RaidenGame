package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 * <p>
 * Difference to BaseAircraft:
 * - has weapon (registered in constructor)
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected LaunchController weaponLaunchController;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                                int maxHp, int maxStepsAfterDeath, int crashDamage, int score) {
        super(name, x, y, sizeX, sizeY, owner,
                maxHp, maxStepsAfterDeath, crashDamage, score);
    }

    public void registerWeaponLaunchController(LaunchController weaponLaunchController) {
        registerWeaponLaunchController(weaponLaunchController, false);
    }

    public void registerWeaponLaunchController(LaunchController weaponLaunchController, boolean activateNow) {
        this.weaponLaunchController = weaponLaunchController;
        if (activateNow)
            weaponLaunchController.activate();
    }

    public LaunchController getWeaponLaunchController() {
        return weaponLaunchController;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            getWeaponLaunchController().launchIfPossible();
        }
    }
}
