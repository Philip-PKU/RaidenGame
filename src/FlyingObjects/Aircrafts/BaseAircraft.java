package FlyingObjects.Aircrafts;

import FlyingObjects.BaseFlyingObject;
import org.apache.commons.lang3.mutable.MutableInt;

import java.nio.file.Paths;

/**
 * Subclass of BaseFlyingObject, base class of all air crafts in the game,
 * including shooting air crafts and self-destruct aircrafts.
 * Class attributes include:
 *     1. (default static) path2image - An object-image mapping
 * Object attributes include:
 *     1. size
 *     2. coordinates
 *     3. states (alive?)
 *     4. max x/y speed
 *     5. (protected) reference to global game step
 *     6. max hp and current hp
 *     7. steps after death & max steps after death - for death effects
 * Object methods include:
 *     1. Getters (public) and setters (default) for some of the attributes
 *     3. (public abstract) step - Take a step and modify relative attributes
 *     4. (public abstract) getImagePath - Get path to current image of the object
 *     5. (default static) loadImage - Load an image to memory. Used in {@code paint} function.
 *     6. paint - Paint the object on screen
 *     7. hasHit - Judge if two objects has hit each other
 *     8. receiveDamage - receive damage and change state if dead
 */
public abstract class BaseAircraft extends BaseFlyingObject {
    int hp;
    int stepsAfterDeath = 0, maxStepsAfterDeath;
    String name;

    protected BaseAircraft(int sizeX, int sizeY, int x, int y, float maxSpeedX, float maxSpeedY, MutableInt gameStep,
                           int maxHP, int maxStepsAfterDeath, String name) {
        super(sizeX, sizeY, x, y, maxSpeedX, maxSpeedY, gameStep);
        this.hp = maxHP;
        this.maxStepsAfterDeath = maxStepsAfterDeath;
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int getStepsAfterDeath() {
        return stepsAfterDeath;
    }

    protected void incrStepsAfterDeath() {
        ++stepsAfterDeath;
    }

    protected void receiveDamage(int damage) {
        hp -= damage;
        if (hp <= 0)
            markAsDead();
    }

    public String getImagePath() {
        int stepsAfterDeath = getStepsAfterDeath();
        if (stepsAfterDeath <= maxStepsAfterDeath) {
            String filename = name + String.valueOf(stepsAfterDeath);
            if (!isAlive()) {
                incrStepsAfterDeath();
            }
            return Paths.get("data", "images", filename + ".png").toString();
        }
        else
            return null;
    }
}
