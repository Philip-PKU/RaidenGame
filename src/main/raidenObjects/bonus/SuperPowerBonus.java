package main.raidenObjects.bonus;

import main.motionControllers.HoveringXMotionController;
import main.motionControllers.MotionController;
import main.motionControllers.XYMotionController;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.Faction;
import main.utils.InitLocation;

import static main.world.World.windowWidth;

/**
 * Bonus that gives the player an extra superpower.
 *
 * @see PlayerAircraft.SuperpowerLaunchController
 *
 * @author 唐宇豪
 */
public final class SuperPowerBonus extends BaseBonus {
    public SuperPowerBonus() {
        super("SuperPowerBonus",20, 20, Faction.BONUS);
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1.5f);
        this.registerMotionController(XYController);
    }

    public SuperPowerBonus(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public SuperPowerBonus(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public void bonus(PlayerAircraft aircraft) {
        super.bonus(aircraft);
        aircraft.incrAvailableSuperpowers();
    }
}
