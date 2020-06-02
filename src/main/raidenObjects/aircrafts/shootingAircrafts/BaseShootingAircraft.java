package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.LaunchController;
import main.raidenObjects.aircrafts.BaseAircraft;
import main.utils.Faction;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 *
 * Difference to BaseAircraft: has weapon (registered in constructor)
 * 
 * @author 蔡辉宇
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected LaunchController weaponLaunchController;

    /**
     * Constructor.
     *
     * @param name Name of the shooting aircraft.
     * @param imgSizeX Image width.
     * @param imgSizeY Image height.
     * @param faction Faction of the object.
     * @param maxHp Maximum HP of the aircraft.
     * @param numOfDeathEffectImages Number of images for death effect.
     * @param crashDamage Damage damage to the other aircraft at crashing.
     * @param score Score to {@link PlayerAircraft} when being shot by the player.
     */
    public BaseShootingAircraft(String name, int imgSizeX, int imgSizeY, Faction faction,
                                int maxHp, int numOfDeathEffectImages, int crashDamage, int score) {
        super(name, imgSizeX, imgSizeY, faction,
                maxHp, numOfDeathEffectImages, crashDamage, score);
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
