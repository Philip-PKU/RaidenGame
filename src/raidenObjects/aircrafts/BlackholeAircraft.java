package raidenObjects.aircrafts;

import motionControllers.ConstSpeedYMotionController;
import utils.Faction;
import utils.InitLocation;
import world.World;

import java.nio.file.Paths;

/**
 * BlackholeAircraft. The nightmare of all {@link raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft}s.
 * Not only can it suck in any player without a shield who dare to step in the event horizon,
 * it also sucks in the player's bullets, blocking them from reaching the enemy. The only player's weapon
 * that comes through the black hole is the {@link raidenObjects.weapons.PlayerBeam}.
 * Its motion is controller by a {@link ConstSpeedYMotionController} which makes it go straight south.
 *
 * @author 张哲瑞
 */
public final class BlackholeAircraft extends BaseAircraft {
    public BlackholeAircraft() {
        super("BlackHoleAircraft", 65, 65, Faction.ENEMY,
                Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0);
        this.registerMotionController(new ConstSpeedYMotionController(0.5f));
        World.playSoundEffect(Paths.get("data", "sound effects", "Blackhole.mp3").toString());
    }

    public BlackholeAircraft(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public BlackholeAircraft(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    /**
     * Ignore any damage received. This is a blackhole!
     */
    @Override
    public void receiveDamage(int damage, Faction source) {
    }
}
