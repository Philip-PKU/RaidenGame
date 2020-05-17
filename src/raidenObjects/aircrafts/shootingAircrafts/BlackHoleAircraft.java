package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchController;
import motionControllers.*;
import utils.Faction;

import java.util.Arrays;

public final class BlackHoleAircraft extends BaseShootingAircraft{
	public BlackHoleAircraft(float x, float y) {
		super("BlackHoleAircraft", x, y, 65, 65, Faction.BLACKHOLE,
				10000, 0, 1);
		 this.registerMotionController(new ConstSpeedYMotionController(0.5f));
		 this.registerWeaponLaunchController(new PeriodicLaunchController(10000, 0,
	                () -> {},
	                Arrays.asList(0, 6, 12)));
	}
}
