package raidenObjects.weapons;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

/**
 * Subclass of BaseRaidenObject, base class for all weapons in the game.
 * <p>
 * Difference to BaseRaidenObject:
 * - has damage property
 * - able to interact with aircrafts and inflict damage upon them
 */
public abstract class BaseWeapon extends BaseRaidenObject {
    protected int damage;

    public BaseWeapon(String name, float x, float y, int sizeX, int sizeY,
                      Faction owner, int damage) {
        super(name, x, y, sizeX, sizeY, owner);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

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
            aircraft.receiveDamage(getDamage());
            if (!aircraft.isAlive()) {
                if (this.getFaction().isPlayer1())
                    player1.addScore(aircraft.getScore());
                if (this.getFaction().isPlayer2())
                    player2.addScore(aircraft.getScore());
            }
            this.markAsDead();
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }

    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
    }


    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
        }
        if (isAlive())
            aircraftList.forEach(this::interactWith);
    }
}
