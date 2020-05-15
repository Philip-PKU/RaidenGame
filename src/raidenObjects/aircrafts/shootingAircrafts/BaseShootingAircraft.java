package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 *
 * Difference to BaseAircraft:
 *  - has weapon (registered in constructor)
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected LaunchController weaponLaunchController;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                                int maxHp, int maxStepsAfterDeath, int crashDamage) {
        super(name, x, y, sizeX, sizeY, owner,
                maxHp, maxStepsAfterDeath, crashDamage);
    }

    public void registerWeaponLaunchController(LaunchController weaponLaunchController) {
        this.weaponLaunchController = weaponLaunchController;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            weaponLaunchController.launchIfPossible();
        }
    }
}
