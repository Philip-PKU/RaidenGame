package raidenObjects.bonus;

import motionControllers.ConstSpeedTargetTrackingMotionController;
import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.player1;
import static world.World.player2;

public abstract class BaseBonus extends BaseRaidenObject {
    boolean attracted = false;

    protected BaseBonus(String name, float x, float y, int imgSizeX, int imgSizeY, Faction owner) {
        super(name, x, y, imgSizeX, imgSizeY, owner);
    }

    public boolean isAttracted() {
        return attracted;
    }

    public void becomesAttracted() {
        attracted = true;
    }

    public abstract void bonus(PlayerAircraft aircraft);

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

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     *
     * @return A File object representing current image of this bonus object.
     * @author 蔡辉宇
     */
    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
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
