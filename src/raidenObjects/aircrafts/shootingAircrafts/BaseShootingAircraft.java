package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.KeyboardLaunchController;
import launchControllers.LaunchController;
import motionControllers.KeyboardMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.weapons.bullets.BigPlayerBullet;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.BaseRaidenKeyAdapter;
import utils.Faction;

import static world.World.interactantList;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 * <p>
 * Difference to BaseAircraft:
 * - has weapon (registered in constructor)
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected LaunchController weaponLaunchController;
    public final int bombCost = 200;
    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                                int maxHp, int maxStepsAfterDeath, int crashDamage, int score) {
        super(name, x, y, sizeX, sizeY, owner,
                maxHp, maxStepsAfterDeath, crashDamage, score);
    }

    public void registerWeaponLaunchController(LaunchController weaponLaunchController) {
        this.weaponLaunchController = weaponLaunchController;
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
        	this.setWeaponTime(this.getWeaponTime()-1);
            if (this.getWeaponType() > 0 || this.getWeaponTime() == 0) {
                updateWeapon();
            }
            if (this.GetKeyAdapter() != null) {
            	if (this.getPowerUse() == 0 && this.GetKeyAdapter().getBombState() != 0 && this.getCoin() >= bombCost) {
            		this.setPowerUse(1);
            		this.setSuperPower(1);
            		this.receiveCoin(-bombCost);
            	}
            	if (this.GetKeyAdapter().getBombState() == 0)
            		this.setPowerUse(0);
            }
            if (this.getSuperPower() > 0) {
                useSuperPower();
            }
            weaponLaunchController.launchIfPossible();
        }
    }

    private void updateWeapon() {
    	BaseRaidenKeyAdapter keyAdapter = this.GetKeyAdapter();
   	 	this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        if (this.getWeaponType() == 1) {
             this.registerWeaponLaunchController(new KeyboardLaunchController(
                     2, keyAdapter,  () -> {
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 4));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -4));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 8));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -8));
             }));
            this.setWeaponTime(1000);
        } else if (this.getWeaponType() == 2) {
             this.registerWeaponLaunchController(new KeyboardLaunchController(
                     2, keyAdapter,  () -> {
                 interactantList.add(new BigPlayerBullet(getX(), getMinY(), getOwner(), 0));
             }));
            this.setWeaponTime(1000);
        }
        else {
             this.registerWeaponLaunchController(new KeyboardLaunchController(
                     2, keyAdapter,  () -> {
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 8));
                 interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -8));
             }));
            this.setWeaponTime(99999999);
        }
        this.weaponLaunchController.activate();
        this.setWeaponType(0);
    }
}
