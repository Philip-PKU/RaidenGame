package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Aircrafts.BaseAircraft;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including Player, SmallShootingAircraft, BigShootingAircraft, etc.
 * Class attributes include:
 *     - file2image - An object-image mapping for loadImage function
 * Object attributes include:
 *     - coordinates
 *     - states (alive?)
 *     - reference to global game step (protected) & born time (protected final)
 *     - object name
 *     - object owner
 *     - object size
 *     - max speed in pixels per step
 *     - max and current hp
 *     - max and current steps after death- for death effects
 *     - list of shooted weapons
 * Object methods include:
 *     - Getters (public) and setters (protected) for some of the attributes
 *     - step (public) - Take a step and modify relative attributes
 *     - getImageFile (public) - Get path to current image of the object
 *     - loadImage (protected static) - Load an image to memory. Used in {@code paint} function.
 *     - paint - Paint the object on screen
 *     - hasHit - Judge if two objects has hit each other
 *     - isOutOfWorld - Judge if the object is out of the Raiden world
 *     - receiveDamage - Receive damage and change state if dead
 *     - shootWeapon (public abstract) - Shoot weapon when cool down time has passed.
 *                                       Called by the {@code step} function.
 *     - move (public abstract) - Move the air craft when taking a step. Called by the {@code step} function.
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected int weaponCoolDown;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
                                RaidenObjectOwner owner, RaidenObjectController controller,
                                int maxHp, int maxStepsAfterDeath, int crashDamage,
                                int weaponCoolDown) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller,
                maxHp, maxStepsAfterDeath, crashDamage);
        this.weaponCoolDown = weaponCoolDown;
    }

    public abstract void shootWeapon();
    public abstract void move();

    public int getWeaponCoolDown() {
        return weaponCoolDown;
    }

    public void step() {
        if (isAlive()) {
            move();
            markAsDeadIfOutOfBound();
            shootWeapon();
        }
    }
}
