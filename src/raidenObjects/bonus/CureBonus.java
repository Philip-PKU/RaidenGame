package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;
import utils.InitLocation;

import static java.lang.Math.min;
import static world.World.windowWidth;

/**
 * Bonus that gives player HP.
 *
 * @author 张哲瑞
 */
public final class CureBonus extends BaseBonus {
    static int bonusHp = 25;

    public CureBonus() {
        super("CureBonus",20, 20, Faction.BONUS);
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1.5f);
        this.registerMotionController(XYController);
    }

    public CureBonus(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    public CureBonus(InitLocation initLocation) {
        this();
        initXFromLocation(initLocation);
    }

    @Override
    public void bonus(PlayerAircraft aircraft) {
        super.bonus(aircraft);
        aircraft.setHp(min(aircraft.getMaxHp(), aircraft.getHp() + bonusHp));
    }
}
