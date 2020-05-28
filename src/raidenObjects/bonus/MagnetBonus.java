package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import static world.World.windowWidth;

public final class MagnetBonus extends BaseBonus {
    static int effectiveGameSteps = 300;

    public MagnetBonus(float x, float y) {
        super("MagnetBonus", x, y, 20, 20, Faction.BONUS);
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1.5f);
        this.registerMotionController(XYController);
    }

    @Override
    public void bonus(PlayerAircraft aircraft) {
        aircraft.getMagnetCountdown().extendDurationBy(effectiveGameSteps);
    }
}
