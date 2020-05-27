package raidenObjects.weapons;

import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

import java.awt.*;

import static world.World.*;

public class PlayerBeam extends BaseWeapon {
    public PlayerBeam(float x, float y, Faction owner) {
        super("PlayerBeam", x, y, 26, 290, owner, 2);
    }

    @Override
    public void paint(Graphics g) {
        int imgSizeY = getImgSizeY();
        for (int y = (int) getMinY() - imgSizeY/2; y >= -imgSizeY; y -= imgSizeY) {
            g.drawImage(loadImage(getImageFile()), (int) getMinX(), y, null);
        }
        becomeInvisible();
    }

    @Override
    public void step() {
        if (isAlive()) {
            aircraftList.forEach(this::interactWith);
        }
    }

    @Override
    public float getHitTopLeftY() {
        return 0;
    }

    public void interactWith(BaseAircraft aircraft) {
        // weapon hits aircraft, aircraft receive damage when they're not invincible
        // Note: this will cause player's weapon to disappear at contact with the black hole
        if (aircraft.isAlive() && this.isAlive() && this.hasHit(aircraft) &&
                this.getFaction().isEnemyTo(aircraft.getFaction())) {
            // if this bullet kill the aircraft, then the score will transform from the aircraft
            // to the player
            aircraft.receiveDamage(getDamage());
            if (!aircraft.isAlive()) {
                if (this.getFaction().isPlayer1())
                    player1.addScore(aircraft.getScore());
                if (this.getFaction().isPlayer2())
                    player2.addScore(aircraft.getScore());
            }
        }
    }
}
