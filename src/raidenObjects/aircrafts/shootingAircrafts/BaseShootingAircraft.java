package raidenObjects.aircrafts.shootingAircrafts;

import static world.World.interactantList;
import static world.World.aircraftList;

import launchControllers.LaunchController;
import launchControllers.PeriodicLaunchController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import raidenObjects.weapons.bullets.BigPlayerBullet;
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
        	if (this.getWeaponType()>0) {
        		updateWeapon();
        	}
        	if (this.getSuperPower() > 0 ) {
        		useSuperPower();
        	}
            weaponLaunchController.launchIfPossible();
        }
    }
    private void updateWeapon() {
    	if (this.getWeaponType() == 1) {
    		registerWeaponLaunchController(new PeriodicLaunchController(2, 0, () -> {
    			interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0));
    			interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 4));
    			interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -4));
    			interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 8));
    			interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -8));
    		}));
    		this.weaponLaunchController.activate();
    	}
    	else if (this.getWeaponType() == 2) {
    		registerWeaponLaunchController(new PeriodicLaunchController(2, 0, () -> {
    			interactantList.add(new BigPlayerBullet(getX(), getMinY(), getOwner(), 0));
    		}));
    		this.weaponLaunchController.activate();
    	}
    	this.setWeaponType(0);
    }
    private void useSuperPower() {
    	this.setSuperPower(0);
    	interactantList.clear();
    	for (int i=0;i<aircraftList.size();i++) {
    		if (aircraftList.get(i) instanceof PlayerAircraft) {
    			continue;
    		}
    		aircraftList.remove(i);
    		i--;
    	}
    }
}
