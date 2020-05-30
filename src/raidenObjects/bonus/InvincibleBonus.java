package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;
import utils.InitLocation;

import static world.World.windowWidth;


/**
 * Bonus that makes the player invincible to weapon damage for a while.
 * If the player bumps into another aircraft, the effect of this bonus will weaken and the player will become
 * vulnerable in a short period of time.
 *
 * @see PlayerAircraft#getInvincibleCountdown()
 * @see raidenObjects.aircrafts.BaseAircraft#interactWith(PlayerAircraft)
 * @see raidenObjects.weapons.BaseWeapon#interactWith(BaseAircraft)
 *
 * @author 张哲瑞
 */
public final class InvincibleBonus extends BaseBonus {
    static int effectiveGameSteps = 250;

    public InvincibleBonus() {
        super("InvincibleBonus", 20, 20, Faction.BONUS);
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1.5f);
        this.registerMotionController(XYController);
    }

    public InvincibleBonus(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public InvincibleBonus(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public void bonus(PlayerAircraft aircraft) {
        super.bonus(aircraft);
        aircraft.getInvincibleCountdown().extendDurationBy(effectiveGameSteps);
    }

    public static int getEffectiveGameSteps() {
        return effectiveGameSteps;
    }
}
