package main.raidenObjects.weapons;

import main.raidenObjects.aircrafts.BaseAircraft;
import main.utils.Faction;

import static main.ui.world.World.*;

import java.awt.*;

/**
 * PlayerBeam.
 * A penetrative laser weapon that tortures the farthest enemy, even when they are hiding behind a black hole.
 * Each beam has a lifetime of one game step only. They will be disabled once painted to screen.
 *
 * @see main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.BeamLaunchController
 *
 * @author 蔡辉宇
 */
public class PlayerBeam extends BaseWeapon {
    static int staticDamage = 2;

    /**
     * Constructor.
     *
     * @param x       Initial X coordinate.
     * @param y       Initial Y coordinate.
     * @param faction Faction of the beam.
     */
    public PlayerBeam(float x, float y, Faction faction) {
        super("PlayerBeam", x, y, 26, 290, faction, 2);
    }

    public static int getStaticDamage() {
        return staticDamage;
    }

    public static void setStaticDamage(int staticDamage) {
        PlayerBeam.staticDamage = staticDamage;
    }

    /**
     * Paints the beam and makes it invisible (the beam has a lifetime of one game step).
     * The image is of finite size, so we should dynamically paint the beam all the way to the top of the map.
     * @param g A {@link Graphics} object.
     */
    @Override
    public void paint(Graphics g) {
        int imgSizeY = getImgSizeY();
        for (int y = (int) getMinY() - imgSizeY / 2; y >= -imgSizeY; y -= imgSizeY) {
            g.drawImage(loadImage(getImageFile()), (int) getMinX(), y, null);
        }
        becomeInvisible();
    }

    /**
     * The beam does not have a motion controller. Its only mission is to yield insane damage!
     */
    @Override
    public void step() {
        if (isAlive()) {
            aircraftList.forEach(this::interactWith);
        }
    }

    /**
     * The hitTopLeftY of this weapon is always 0.
     * The beam hits everything right to the top of the map!
     */
    @Override
    public float getHitTopLeftY() {
        return 0;
    }

    /**
     * Interact with an aircraft. Overrides {@link BaseWeapon#interactWith(BaseAircraft)}
     * Difference: Does not become dead on contact with another aircraft.
     * @param aircraft An {@link BaseAircraft}.
     *
     * @see BaseWeapon#interactWith(BaseAircraft)
     */
    public void interactWith(BaseAircraft aircraft) {
        // weapon hits aircraft, aircraft receive damage when they're not invincible
        // Note: this will cause player's weapon to disappear at contact with the black hole
        if (aircraft.isAlive() && this.isAlive() && this.hasHit(aircraft) &&
                this.getFaction().isEnemyTo(aircraft.getFaction())) {
            // if this bullet kill the aircraft, then the score will transform from the aircraft
            // to the player
            aircraft.receiveDamage(getDamage(), getFaction());
            if (!aircraft.isAlive()) {
                if (this.getFaction().isPlayer1())
                    player1.addScore(aircraft.getScore());
                if (this.getFaction().isPlayer2())
                    player2.addScore(aircraft.getScore());
            }
        }
    }
}
