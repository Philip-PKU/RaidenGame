package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchController;
import motionControllers.*;
import utils.Faction;

import java.util.Arrays;

import static world.World.*;

public final class BlackHoleAircraft extends BaseShootingAircraft{
	public BlackHoleAircraft(float x, float y) {
		super("BlackHoleAircraft", x, y, 65, 65, Faction.ENEMY,
				1000000, 0, 100000);
		 YAwareMotionController stageOneController = new ConstSpeedYMotionController(0.5f);
		 MotionController stageTwoXController = new HoveringXMotionController(0, 0, windowWidth);
		 MotionController stageTwoController = XYMotionController.defaultFromXController(
	                stageTwoXController, 0.5f);
		 this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
	                () -> getY() > 80,
	                () -> weaponLaunchController.activate()));
		 this.registerWeaponLaunchController(new PeriodicLaunchController(10000, 0,
	                () -> {},
	                Arrays.asList(0, 6, 12)));
	}
	
}
