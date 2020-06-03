package main.raidenObjects.bonus;

import main.World;
import main.motionControllers.ConstSpeedTargetTrackingMotionController;
import main.motionControllers.ConstSpeedYMotionController;
import main.motionControllers.HoveringXMotionController;
import main.motionControllers.XYMotionController;
import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static main.World.player1;
import static main.World.player2;

/**
 * Base class for all bonuses.
 * Bonus is a small floating object that, once picked up by a player, gives it additional strength or abilities.
 * All bonuses are controlled by an {@link XYMotionController}, which consists of a
 * {@link HoveringXMotionController} and a {@link ConstSpeedYMotionController},
 * which makes the bonus hover around, waiting to be picked up.
 *
 * @author 张哲瑞
 */
public abstract class BaseBonus extends BaseRaidenObject {
    /**
     * Most bonuses (except {@link WeaponUpgradeBonus}) can be attracted by the player's magnet.
     *
     * @see PlayerAircraft#getMagnetCountdown()
     * @see MagnetBonus
     */
    boolean attracted = false;
    String soundEffectPath;

    /**
     * Constructor.
     *
     * @param name     Name of the bonus.
     * @param imgSizeX Image width.
     * @param imgSizeY Image height.
     * @param faction  Faction of the bonus, usually {@link Faction#BONUS}.
     */
    protected BaseBonus(String name, int imgSizeX, int imgSizeY, Faction faction) {
        super(name, imgSizeX, imgSizeY, faction);
        this.soundEffectPath = Paths.get("data", "sound effects", name + ".mp3").toString();
    }

    public boolean isAttracted() {
        return attracted;
    }

    public void becomesAttracted() {
        attracted = true;
    }


    public String getSoundEffectPath() {
        return soundEffectPath;
    }

    /**
     * Logic for the effect of this bonus.
     * @param aircraft The aircraft receiving this bonus.
     */
    public void bonus(PlayerAircraft aircraft) {
        World.playSoundEffect(getSoundEffectPath());
    }

    /**
     * Interact with {code aircraft}.
     * @param aircraft An aircraft interacting with this bonus.
     */
    public void interactWith(PlayerAircraft aircraft) {
        if (!(this.isAlive() && aircraft.isAlive()))
            return;

        // if bonus hits the player
        if (this.hasHit(aircraft)) {
            this.bonus(aircraft);
            this.markAsDead();
        }
        // if player is attractive (has magnet) and the bonus hasn't been attracted
        if (aircraft.getMagnetCountdown().isEffective() && !this.isAttracted()) {
            becomesAttracted();
            if (isAttracted())
                this.registerMotionController(new ConstSpeedTargetTrackingMotionController(aircraft, 0.3f, 10f));
        }
    }

    /**
     * Mark this aircraft as dead.
     * There is no visual effect for {@link BaseBonus} and its children,
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
     * @return A File object representing current image of this bonus object.
     */
    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
    }

    /**
     * This function combines all control logic for this bonus.
     * Note: It can be overridden by children if additional logic is needed.
     */
    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
        }
        if (isAlive()) {
            if (player1 != null)
                interactWith(player1);
            if (player2 != null)
                interactWith(player2);
        }
    }
}
