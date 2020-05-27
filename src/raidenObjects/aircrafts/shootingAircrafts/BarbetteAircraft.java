package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.LaunchController;
import launchControllers.PeriodicLaunchEventScheduler;
import motionControllers.*;
import raidenObjects.weapons.BarbetteBullet;
import utils.Faction;

import static world.World.*;

public final class BarbetteAircraft extends BaseShootingAircraft{
	private static final float hitSizeY = 140f;
	private static int staticMaxHp = 600;
	public BarbetteAircraft(float x, float y) {
		super("BarbetteAircraft", x, y, 75, 160, Faction.ENEMY,
				staticMaxHp, 13, 300, 200);
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
		 probCoin1 = 0.4f;
		 probCoin2 = 0.28f;
		 probMagnet = 0.1f;
		 probInvincible = 0.05f;
		 probCure = 0.05f;
		 probWeaponUpgrade = 0.1f;
		 probSuperpower = 0.02f;
	}

	@Override
	public float getHitBottomRightY() {
		return getY() + hitSizeY / 2f;
	}

	public static int getStaticMaxHp() {
		return staticMaxHp;
	}

	public static void setStaticMaxHp(int staticMaxHp) {
		BarbetteAircraft.staticMaxHp = staticMaxHp;
	}
}
