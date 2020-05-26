package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import launchControllers.PeriodicLaunchEventScheduler;
import motionControllers.*;
import raidenObjects.weapons.BarbetteBullet;
import utils.Faction;

import static world.World.*;

public final class BarbetteAircraft extends BaseShootingAircraft{
	private static final float hitSizeY = 140f;
	public BarbetteAircraft(float x, float y) {
		super("BarbetteAircraft", x, y, 75, 160, Faction.ENEMY, 
				600, 13, 300, 200);
		YAwareMotionController stageOneController = new ConstSpeedYMotionController(2.5f);
		MotionController stageTwoXController = new HoveringXMotionController(0, 0, windowWidth);
		MotionController stageTwoController = XYMotionController.createFromXController(
	                stageTwoXController, 1.5f);
		 this.registerMotionController(new TwoStagedMotionController(stageOneController, stageTwoController,
	                () -> getY() > 80,
	                () -> getWeaponLaunchController().activate()));
		 
		 this.registerWeaponLaunchController(new LaunchController(
		 		new PeriodicLaunchEventScheduler(250, 50, 18, 6),
				() -> {
					PlayerAircraft closestPlayer = getClosestPlayer();
					interactantList.add(new BarbetteBullet(getX() - 10, getMaxY(),
							closestPlayer.getX() + rand.nextFloat() * 20 - 10,
							closestPlayer.getY() + rand.nextFloat() * 30 - 15));
					interactantList.add(new BarbetteBullet(getX() + 10, getMaxY(),
							closestPlayer.getX() + rand.nextFloat() * 20 - 10,
							closestPlayer.getY() + rand.nextFloat() * 30 - 15));
				})
		 );
	}

	@Override
	public float getHitBottomRightY() {
		return getY() + hitSizeY / 2f;
	}
}
