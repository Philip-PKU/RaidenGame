package raidenObjects.aircrafts;

import raidenObjects.BaseRaidenObject;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import motionControllers.TargetTrackingMotionController;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class of all aircrafts in the game,
 * including shooting aircrafts and self-destruct bumping aircrafts.
 *
 * Difference to BaseRaidenObject:
 *  - has HP (hit points), will die if hp <= 0
 *  - cause crashDamages, i.e. if it bumps into enemy aircrafts it will cause damage to them
 *  - has visual effects after death (see {@code getImageFile})
 *  - has isInvincible, isAttractive,  means remaining time of the state
 *  - has isAttracted, true means the aircraft(bonus) is attracted by players
 *  - cause bonus, i.e. if it hits the players, it will add buff to them
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, stepsAfterDeath = 0;
    protected int maxHp, maxStepsAfterDeath, crashDamage;
    protected int isInvincible = 0;
    protected int isAttractive = 0;
    protected int coin = 0;
    protected boolean isAttracted = false;

    protected BaseAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                           int maxHp, int maxStepsAfterDeath, int crashDamage) {
        super(name, x, y, sizeX, sizeY, owner);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxStepsAfterDeath = maxStepsAfterDeath;
        this.crashDamage = crashDamage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getCoin() {
    	return coin;
    }
    
    public void setCoin(int coin) {
    	this.coin = coin;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCrashDamage() {
        return crashDamage;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxStepsAfterDeath() {
        return maxStepsAfterDeath;
    }
    
    public int getisInvincible() {
    	return isInvincible;
    }
    
    protected void desisInvincible() {
    	--isInvincible;
    }
    
    public void setisInvincible(int isInvincible) {
    	this.isInvincible = isInvincible;
    }
    
    public int getisAttractive() {
    	return isAttractive;
    }
    
    protected void desisAttractive() {
    	--isAttractive;
	}
    
    public void setisAttractive(int isAttractive) {
    	this.isAttractive = isAttractive;
    }
    
    public void bonus(BaseAircraft aircraft) {
    	return;
    }
    
    public void setisAttracted(boolean isAttracted) {
    	this.isAttracted = isAttracted;
    }
    
    public boolean getisAttracted() {
    	return isAttracted;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (getOwner().isPlayer())
            System.out.println("hp: " + hp);
        if (hp <= 0)
            markAsDead();
    }
    
    public void receiveCoin(int coin) {
    	this.coin += coin;
    	System.out.println("coin: " + this.coin);
    }

    public void interactWith(BaseAircraft aircraft) {
        if (aircraft == null || aircraft == this)
            return;
        // if bonus hits the player
        if(this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) && this.getOwner().isBonus()) {
        	this.bonus(aircraft);
        	this.markAsDead();
        }
        // if player can attract bonus and the bonus hasn't been attracted
        if(this.isAlive() && aircraft.getisAttractive() > 0 && this.getOwner().isBonus() &&
        		this.getisAttracted() == false) {
        	this.setisAttracted(true);
        	this.registerMotionController(new TargetTrackingMotionController(aircraft, 0.2f, 10f));
        }
        // if palyer hit enemy or blackhole
        if (this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) &&
                (this.getOwner().isEnemyTo(aircraft.getOwner()) ||
                		this.getOwner().isBlackhole())) {
            this.receiveDamage(aircraft.getCrashDamage());
            if(aircraft.isInvincible == 0) {
       		 	aircraft.receiveDamage(this.getCrashDamage());
            }
        }
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, continues with {@code stepsAfterDeath} (0 if the aircraft is alive,
     * 1~maxStepsAfterDeath if the aircraft is dead but still on screen), and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     * @return A File object representing current image of this aircraft.
     * @author 蔡辉宇
     */
    public File getImageFile() {
        if (stepsAfterDeath <= getMaxStepsAfterDeath()) {
            String filename = getName() + stepsAfterDeath;
            // Trick: slow down the visual effect so that aircrafts will not vanish too quickly
            if (!isAlive() && gameStep.intValue() % 2 == 0) {
                ++stepsAfterDeath;
            }
            return Paths.get("data", "images", filename + ".png").toFile();
        }
        else {
            getOffScreen();
            return null;
        }
    }

    /**
     * This function combines all control logic for this aircraft.
     * Note: It is often overridden by children if additional logic is needed.
     * @author 蔡辉宇
     */
    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            moveAndCheckPosition();
        }
        if (isAlive()) {
            interactWith(player1);
            interactWith(player2);
        }
        if(getisInvincible() > 0) {
        	desisInvincible();
        }
        if(getisAttractive() > 0) {
        	desisAttractive();
        }
    }
}
