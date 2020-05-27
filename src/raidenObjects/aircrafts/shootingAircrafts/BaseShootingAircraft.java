package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import launchControllers.LaunchEventScheduler;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.bonus.*;
import utils.Faction;

import static world.World.*;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 * <p>
 * Difference to BaseAircraft:
 * - has weapon (registered in constructor)
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected LaunchController<? extends LaunchEventScheduler> weaponLaunchController;
    protected float probCoin0, probCoin1, probCoin2, probInvincible, probMagnet, probSuperpower, probWeaponUpgrade, probCure;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                                int maxHp, int maxStepsAfterDeath, int crashDamage, int score) {
        super(name, x, y, sizeX, sizeY, owner,
                maxHp, maxStepsAfterDeath, crashDamage, score);
    }

    public void registerWeaponLaunchController(LaunchController<? extends LaunchEventScheduler> weaponLaunchController) {
        registerWeaponLaunchController(weaponLaunchController, false);
    }

    public void registerWeaponLaunchController(LaunchController<? extends LaunchEventScheduler> weaponLaunchController, boolean activateNow) {
        this.weaponLaunchController = weaponLaunchController;
        if (activateNow)
            weaponLaunchController.activate();
    }

    public LaunchController<? extends LaunchEventScheduler> getWeaponLaunchController() {
        return weaponLaunchController;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            getWeaponLaunchController().launchIfPossible();
        }
    }

    @Override
    public void afterKilledByPlayer() {
        if ((player1 != null && player1.getHp() <= player1.getMaxHp() * 0.4) ||
                (player2 != null && player2.getHp() <= player2.getMaxHp() * 0.4)) {
            probInvincible *= 1.5;
            probCure *= 1.5;
            probWeaponUpgrade *= 1.5;
        }
        float randomFloat = rand.nextFloat();
        if ((randomFloat -= probCure) <= 0)
            interactantList.add(new CureBonus(getX(), getY()));
        else if ((randomFloat -= probInvincible) <= 0)
            interactantList.add(new InvincibleBonus(getX(), getY()));
        else if ((randomFloat -= probWeaponUpgrade) <= 0)
            interactantList.add(new WeaponUpgradeBonus(getX(), getY()));
        else if ((randomFloat -= probCoin0) <= 0)
            interactantList.add(new CoinBonus(getX(), getY(), 0));
        else if ((randomFloat -= probCoin1) <= 0)
            interactantList.add(new CoinBonus(getX(), getY(), 1));
        else if ((randomFloat -= probCoin2) <= 0)
            interactantList.add(new CoinBonus(getX(), getY(), 2));
        else if ((randomFloat -= probMagnet) <= 0)
            interactantList.add(new MagnetBonus(getX(), getY()));
        else if ((randomFloat -= probSuperpower) <= 0)
            interactantList.add(new SuperPowerBonus(getX(), getY()));
    }
}
