package raidenObjects.aircrafts;

import raidenObjects.BaseRaidenObject;
import utils.BaseRaidenKeyAdapter;
import utils.EffectCountdown;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class of all aircrafts in the game,
 * including shooting aircrafts and self-destruct bumping aircrafts.
 * <p>
 * Difference to BaseRaidenObject:
 * - has HP (hit points), will die if hp <= 0
 * - cause crashDamages, i.e. if it bumps into enemy aircrafts it will cause damage to them
 * - has visual effects after death (see {@code getImageFile})
 * - has isInvincible, isAttractive,  means remaining time of the state
 * - has isAttracted, true means the aircraft(bonus) is attracted by players
 * - cause bonus, i.e. if it hits the players, it will add buff to them
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, stepsAfterDeath = 0;
    protected int maxHp, maxStepsAfterDeath, crashDamage;
    protected int coin = 0;
    protected int score = 0;
    protected int weaponType = 0;
    protected int superPower = 0;
    protected int weaponTime = 99999999;
    protected int powerUse = 0;
    protected BaseRaidenKeyAdapter keyAdapter = null;
    protected EffectCountdown invincibleCountdown = new EffectCountdown(), magnetCountdown = new EffectCountdown();

    protected BaseAircraft(String name, float x, float y, int sizeX, int sizeY, Faction owner,
                           int maxHp, int maxStepsAfterDeath, int crashDamage, int score) {
        super(name, x, y, sizeX, sizeY, owner);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxStepsAfterDeath = maxStepsAfterDeath;
        this.crashDamage = crashDamage;
        this.score = score;
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
    
    public int getScore() {
    	return score;
    }
    public void addScore(int score) {
    	this.score += score;
    }
    
    public int calculateScore() {
    	return score + coin * 10;
    }
    public BaseRaidenKeyAdapter GetKeyAdapter() {
    	return keyAdapter;
    }
    public void setKeyAdapter(BaseRaidenKeyAdapter keyAdapter) {
    	this.keyAdapter = keyAdapter;
    }
    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }
    public int getWeaponTime() {
        return weaponTime;
    }
    public void setPowerUse(int powerUse) {
        this.powerUse = powerUse;
    }
    public int getPowerUse() {
        return powerUse;
    }
    public void setWeaponTime(int weaponTime) {
        this.weaponTime = weaponTime;
    }
    public int getSuperPower() {
        return superPower;
    }

    public void setSuperPower(int superPower) {
        this.superPower = superPower;
    }

    public void useSuperPower() {
        this.setSuperPower(0);
        interactantList.clear();
        for (int i = 0; i < aircraftList.size(); i++) {
            if (aircraftList.get(i).getOwner().isEnemyTo(this.getOwner())) {
                //aircraftList.remove(i);
            	aircraftList.get(i).receiveDamage(1000);
                //i--;
            }
        }
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

    public EffectCountdown getInvincibleCountdown() {
        return invincibleCountdown;
    }

    public EffectCountdown getMagnetCountdown() {
        return magnetCountdown;
    }
    
    // If this.hp <= 0, the return true
    public boolean receiveDamage(int damage) {
        hp -= damage;
        if (getOwner().isPlayer())
            System.out.println("hp: " + hp);
        if (hp <= 0) {
            markAsDead();
            return true;
        }
        return false;
    }

    public void receiveCoin(int coin) {
        this.coin += coin;
        System.out.println("coin: " + this.coin);
    }

    public void interactWith(BaseAircraft aircraft) {
        if (aircraft == null || aircraft == this)
            return;

        // if player hit enemy
        if (this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) &&
                this.getOwner().isEnemyTo(aircraft.getOwner())) {
        	// if the player kill this aircraft, he gets score.
        	if(this.receiveDamage(aircraft.getCrashDamage()))
        		aircraft.addScore(this.getScore());
            if (!aircraft.getInvincibleCountdown().isEffective()) {
                aircraft.receiveDamage(this.getCrashDamage());
            }
        }
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, continues with {@code stepsAfterDeath} (0 if the aircraft is alive,
     * 1~maxStepsAfterDeath if the aircraft is dead but still on screen), and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     *
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
        } else {
            getOffScreen();
            return null;
        }
    }

    /**
     * This function combines all control logic for this aircraft.
     * Note: It can be overridden by children if additional logic is needed.
     *
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
        invincibleCountdown.step();
        magnetCountdown.step();
    }
}
