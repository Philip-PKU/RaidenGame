package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Aircrafts.BaseAircraft;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.gameStep;

/**
 * Subclass of BaseAircraft, base class of all shooting air crafts in the game,
 * including Player, SmallShootingAircraft, MiddleShootingAircraft, etc.
 * Class attributes include:
 *     - file2image - An object-image mapping for loadImage function
 * Object attributes include:
 *     - coordinates of object center
 *     - states (alive? on screen?)
 *     - born time (protected)
 *     - object name
 *     - object owner and controller
 *     - object size
 *     - max speed and current speed (in pixels per step)
 *     - max and current hp
 *     - max and current steps after death - for death effects
 *     - crash damage - for air craft crashes
 *     - weapon cooldown - minimum steps between two weapon shoot
 *     - initial weapon cooldown - minimum steps before weapons can be shoot
 * Object methods include:
 *     - Getters (public) and setters (protected) for some of the attributes
 *     - step (public) - Take a step and modify relative attributes
 *     - getImageFile (public) - Get path to current image of the object
 *     - loadImage (protected static) - Load an image to memory. Used in {@code paint} function.
 *     - paint - Paint the object on screen
 *     - hasHit - Judge if two objects has hit each other
 *     - isOutOfWorld - Judge if the object is out of the Raiden world
 *     - getRandomPlayer (protected) - Get a random player. Used in enemy air crafts / weapons.
 *     - getClosestPlayer (protected) - Get the closest player. Used in enemy air crafts / weapons.
 *     - move (protected) - Move at current speed. Used in {@code step} function.
 *     - receiveDamage - Receive damage and change state if dead
 *     - shootWeapon (public abstract) - Shoot weapon when cool down time has passed.
 *                                       Called by the {@code step} function.
 *     - initSpeed (protected abstract) - Initialize current speed. Called before {@code move}.
 */
public abstract class BaseShootingAircraft extends BaseAircraft {
    protected int weaponCoolDown, initWeaponCoolDown;
    protected float initMaxSpeed;
    protected boolean hasReachedTarget;

    public BaseShootingAircraft(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
                                RaidenObjectOwner owner, RaidenObjectController controller,
                                int maxHp, int maxStepsAfterDeath, int crashDamage,
                                int weaponCoolDown, int initWeaponCoolDown, float initMaxSpeed) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller,
                maxHp, maxStepsAfterDeath, crashDamage);
        this.weaponCoolDown = weaponCoolDown;
        this.initWeaponCoolDown = initWeaponCoolDown;
        this.initMaxSpeed = initMaxSpeed;
    }

    public abstract void shootWeapon();

    protected void initSpeed() {
        speedX = 0.0f;
        speedY = 0.0f;

    }

    public int getWeaponCoolDown() {
        return weaponCoolDown;
    }

    public int getInitWeaponCoolDown() {
        return initWeaponCoolDown;
    }

    public float getInitMaxSpeed() {
        return initMaxSpeed;
    }

    public void step() {
        if (isAlive()) {
            initSpeed();
            move();
            markAsDeadIfOutOfBound();
            if (hasReachedTarget && gameStep.intValue() - gameStepWhenReady >= getInitWeaponCoolDown())
                shootWeapon();
        }
    }
}
