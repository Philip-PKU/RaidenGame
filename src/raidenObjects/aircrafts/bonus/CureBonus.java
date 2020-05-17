package raidenObjects.aircrafts.bonus;

import static world.World.windowWidth;
import static java.lang.Math.min;

import java.util.Arrays;

import launchControllers.PeriodicLaunchController;
import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BaseShootingAircraft;
import utils.Faction;

public final class CureBonus extends BaseShootingAircraft{
	public CureBonus(float x, float y) {
		super("CureBonus", x, y, 20, 20, Faction.BOUNS,
				1, 0, 0);
		 MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
		 MotionController XYController = XYMotionController.defaultFromXController(
	                XController, 1.5f);
		 this.registerMotionController(XYController);
		 this.registerWeaponLaunchController(new PeriodicLaunchController(10000, 0,
	                () -> {},
	                Arrays.asList(0, 6, 12)));
	}
	
	@Override
	public void bonus(BaseAircraft aircraft) {
		aircraft.setHp(min(aircraft.getMaxHp(), aircraft.getHp() + 25));
	}
}
