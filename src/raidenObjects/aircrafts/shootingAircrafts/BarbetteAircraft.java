package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchController;
import motionControllers.*;
import raidenObjects.weapons.bullets.BarbetteBullet;
import utils.Faction;

import java.util.Arrays;

import static world.World.*;

public final class BarbetteAircraft extends BaseShootingAircraft{
	public BarbetteAircraft(float x, float y) {
		super("BarbetteAircraft", x, y, 75, 160, Faction.ENEMY, 
				500, 13, 300);
		YAwareMotionController stageOneController = new ConstSpeedYMotionController(2.5f);
		MotionController stageTwoXController = new HoveringXMotionController(0, 0, windowWidth);
		MotionController stageTwoController = XYMotionController.defaultFromXController(
	                stageTwoXController, 1.5f);
		 this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
	                () -> getY() > 80,
	                () -> weaponLaunchController.activate()));
		 
		 this.registerWeaponLaunchController(new PeriodicLaunchController(250, 50,
	                () -> {
	                    PlayerAircraft closestPlayer = getClosestPlayer();
	                    interactantList.add(new BarbetteBullet(getX() - 10, getMaxY(),
	                            closestPlayer.getX() + rand.nextFloat() * 20 - 10,
	                            closestPlayer.getY() + rand.nextFloat() * 30 - 15));
	                    interactantList.add(new BarbetteBullet(getX() + 10, getMaxY(),
	                            closestPlayer.getX() + rand.nextFloat() * 20 - 10,
	                            closestPlayer.getY() + rand.nextFloat() * 30 - 15));
	                }, Arrays.asList(0, 18)));
	}
}
