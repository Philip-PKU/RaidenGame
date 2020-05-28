package raidenObjects.weapons;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.aircraftList;

/**
 * Subclass of BaseRaidenObject, base class for all weapons in the game.
 * <p>
 * Difference to BaseRaidenObject:
 * - has damage property
 * - able to interact with aircrafts and inflict damage upon them
 * </p>
 * @author 蔡辉宇
 */
public abstract class BaseWeapon extends BaseRaidenObject {
    protected int damage;

    public BaseWeapon(String name, float x, float y, int imgSizeX, int imgSizeY,
                      Faction faction, int damage) {
        super(name, imgSizeX, imgSizeY, faction);
        setX(x);
        setY(y);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * Interact with an aircraft.
     * If the bullet hit the aircraft, it disappears (unless it is a {@link PlayerBeam}) and the aircraft receives
     * damage (unless it is invincible).
     * @param aircraft An {@link BaseAircraft}.
     *
     * @see raidenObjects.bonus.InvincibleBonus
     * @see BaseAircraft#interactWith(PlayerAircraft)
     * @see PlayerAircraft#getInvincibleCountdown()
     */
    public void interactWith(BaseAircraft aircraft) {
        // weapon hits aircraft, aircraft receive damage when they're not invincible
        // Note: this will cause player's weapon to disappear at contact with the black hole
        if (aircraft.isAlive() && this.isAlive() && this.hasHit(aircraft) &&
                this.getFaction().isEnemyTo(aircraft.getFaction())) {
            if (aircraft instanceof PlayerAircraft && ((PlayerAircraft) aircraft).getInvincibleCountdown().isEffective()) {
                this.markAsDead();
                return;
            }
            // if this bullet kill the aircraft, then the score will transform from the aircraft
            // to the player
            aircraft.receiveDamage(getDamage(), getFaction());
            this.markAsDead();
        }
    }

    /**
     * Mark this weapon as dead.
     * There is no visual effect for {@link BaseWeapon} and its children,
     * so we make it disappear from the screen immediately.
     */
    @Override
    public void markAsDead() {
        super.markAsDead();
        becomeInvisible();
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     *
     * @return A File object representing current image of this weapon object.
     */
    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
    }


    /**
     * This function combines all control logic for this weapon.
     * Note: It can be overridden by children if additional logic is needed.
     */
    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
        }
        if (isAlive())
            aircraftList.forEach(this::interactWith);
    }
}
