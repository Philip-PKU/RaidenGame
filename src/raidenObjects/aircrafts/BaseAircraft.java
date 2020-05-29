package raidenObjects.aircrafts;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import raidenObjects.bonus.*;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class of all aircrafts in the game,
 * including shooting aircrafts and self-destruct bumping aircrafts.
 *
 * Difference to BaseRaidenObject:
 * <p>has HP (hit points), will die if hp &le; 0</p>
 * <p>cause crashDamages, i.e. if it bumps into enemy aircrafts it will cause damage to them</p>
 * <p>has visual effects after death (see {@code getImageFile})</p>
 * <p>has isInvincible, isAttractive,  means remaining time of the state</p>
 * <p>has isAttracted, true means the aircraft(bonus) is attracted by players</p>
 * <p>cause bonus, i.e. if it hits the players, it will add buff to them</p>
 *
 *
 * @author 蔡辉宇 张哲瑞
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, maxHp, stepsAfterDeath = 0;
    protected int numOfDeathEffectImages, crashDamage;
    protected int score;
    protected float probCoin0, probCoin1, probCoin2, probInvincible, probMagnet, probSuperpower, probWeaponUpgrade, probCure;

    protected BaseAircraft(String name, int imgSizeX, int imgSizeY, Faction faction,
                           int maxHp, int numOfDeathEffectImages, int crashDamage, int score) {
        super(name, imgSizeX, imgSizeY, faction);
        this.hp = this.maxHp = maxHp;
        this.numOfDeathEffectImages = numOfDeathEffectImages;
        this.crashDamage = crashDamage;
        this.score = score;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCrashDamage() {
        return crashDamage;
    }

    public int getNumOfDeathEffectImages() {
        return numOfDeathEffectImages;
    }


    public void receiveDamage(int damage, Faction source) {
        hp -= damage;
        if (getFaction().isPlayer())
            System.out.println("hp: " + hp);
        if (hp <= 0) {
            markAsDead();
            afterKilledByPlayer(source);
        }
    }

    /**
     * Interact with a {@link PlayerAircraft}.
     * If two aircrafts crash, they both get crash damage, unless the player aircraft has shield.
     * @param aircraft A {@link PlayerAircraft}.
     *                 
     * @see InvincibleBonus
     * @see PlayerAircraft#getInvincibleCountdown()
     * @see raidenObjects.weapons.BaseWeapon#interactWith(BaseAircraft)
     */
    public void interactWith(PlayerAircraft aircraft) {
        if (aircraft == null || aircraft == this)
            return;

        if (this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) &&
                this.getFaction().isEnemyTo(aircraft.getFaction())) {
            this.receiveDamage(aircraft.getCrashDamage(), aircraft.getFaction());
            if (!aircraft.getInvincibleCountdown().isEffective()) {
                aircraft.receiveDamage(this.getCrashDamage(), this.getFaction());
            } else {
                aircraft.getInvincibleCountdown().reset();
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
     */
    public File getImageFile() {
        if (stepsAfterDeath <= getNumOfDeathEffectImages()) {
            String filename = getName() + stepsAfterDeath;
            // Trick: slow down the visual effect so that aircrafts will not vanish too quickly
            if (!isAlive() && gameStep.intValue() % 2 == 0) {
                ++stepsAfterDeath;
            }
            return Paths.get("data", "images", filename + ".png").toFile();
        } else {
            becomeInvisible();
            return null;
        }
    }

    /**
     * This function combines all control logic for this aircraft.
     * Note: It can be overridden by children if additional logic is needed.
     */
    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
        }
        if (isAlive()) {
            interactWith(player1);
            interactWith(player2);
        }
    }

    public void afterKilledByPlayer(Faction source) {
        if (source.isPlayer1() && player1 != null)
            player1.addScore(getScore());
        if (source.isPlayer2() && player2 != null)
            player2.addScore(getScore());

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
