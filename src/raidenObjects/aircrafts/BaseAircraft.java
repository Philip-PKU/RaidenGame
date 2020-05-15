package raidenObjects.aircrafts;

import raidenObjects.BaseRaidenObject;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class of all aircrafts in the game,
 * including shooting aircrafts and self-destruct bumping aircrafts.
 *
 * Difference to BaseRaidenObject:
 *  - has HP (hit points), will die if hp <= 0
 *  - cause crashDamages, i.e. if it bumps into enemy aircrafts it will cause damage to them
 *  - has visual effects after death (see {@code getImageFile})
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, stepsAfterDeath = 0;
    protected int maxHp, maxStepsAfterDeath, crashDamage;

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

    public void receiveDamage(int damage) {
        hp -= damage;
        if (getOwner().isPlayer())
            System.out.println(hp);
        if (hp <= 0)
            markAsDead();
    }

    public void interactWith(BaseAircraft aircraft) {
        if (aircraft == null || aircraft == this)
            return;
        if (this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) &&
                this.getOwner().isEnemyTo(aircraft.getOwner())) {
            this.receiveDamage(aircraft.getCrashDamage());
            aircraft.receiveDamage(this.getCrashDamage());
        }
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, continues with {@code stepsAfterDeath} (0 if the aircraft is alive,
     * 1~maxStepsAfterDeath if the aircraft is dead but still on screen), and ends with suffix ".jpg".
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
    }
}
