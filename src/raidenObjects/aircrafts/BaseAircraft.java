package raidenObjects.aircrafts;

import raidenObjects.BaseRaidenObject;
import utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class of all air crafts in the game,
 * including shooting air crafts and self-destruct air crafts.
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, stepsAfterDeath = 0;
    protected int maxHp, maxStepsAfterDeath, crashDamage;

    protected BaseAircraft(String name, float x, float y, int sizeX, int sizeY, RaidenObjectOwner owner,
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

    public int getStepsAfterDeath() {
        return stepsAfterDeath;
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

    protected void incrStepsAfterDeath() {
        ++stepsAfterDeath;
    }

    public File getImageFile() {
        int stepsAfterDeath = getStepsAfterDeath();
        if (stepsAfterDeath <= getMaxStepsAfterDeath()) {
            String filename = getName() + stepsAfterDeath;
            if (!isAlive() && gameStep.intValue() % 2 == 0) {
                incrStepsAfterDeath();
            }
            return Paths.get("data", "images", filename + ".png").toFile();
        }
        else {
            getOffScreen();
            return null;
        }
    }

    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
            interactWith(player1);
            interactWith(player2);
        }
    }
}
