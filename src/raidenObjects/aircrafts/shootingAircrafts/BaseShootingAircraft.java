package raidenObjects.aircrafts.shootingAircrafts;

import raidenObjects.aircrafts.BaseAircraft;
import utils.RaidenObjectOwner;

import static world.World.gameStep;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including PlayerAircraft, SmallShootingAircraft, MiddleShootingAircraft, BigShootingAircraft.
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected int weaponCoolDown, initWeaponCoolDown;
    protected boolean hasReachedTargetPos;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, RaidenObjectOwner owner,
                                int maxHp, int maxStepsAfterDeath, int crashDamage,
                                int weaponCoolDown, int initWeaponCoolDown) {
        super(name, x, y, sizeX, sizeY, owner,
                maxHp, maxStepsAfterDeath, crashDamage);
        this.weaponCoolDown = weaponCoolDown;
        this.initWeaponCoolDown = initWeaponCoolDown;
    }

    public abstract void shootWeapon();

    public int getWeaponCoolDown() {
        return weaponCoolDown;
    }

    public int getInitWeaponCoolDown() {
        return initWeaponCoolDown;
    }


    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            if (hasReachedTargetPos && gameStep.intValue() - gameStepWhenReady >= getInitWeaponCoolDown())
                shootWeapon();
        }
    }
}
