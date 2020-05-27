package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;

import static world.World.rand;
import static world.World.windowWidth;

public final class WeaponUpgradeBonus extends BaseBonus {
	public static int [] weapons = {1, 2, 3};
	int weapon;
	public WeaponUpgradeBonus(float x, float y,int type) {
		super("WeaponUpgradeBonus" + weapons[type], x, y, 20, 20, Faction.BONUS);
		weapon = weapons[type];
		 MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
		 MotionController XYController = XYMotionController.createFromXController(
	                XController, 1.5f);
		 this.registerMotionController(XYController);
	}

	public WeaponUpgradeBonus(float x, float y) {
		this(x, y, rand.nextInt(weapons.length));
	}
	
	@Override
	public void bonus(PlayerAircraft aircraft) {
		aircraft.updateWeapon(weapon);
	}

	/**
	 * This bonus cannot be attracted. Otherwise, player may end up having a weapon they don't want.
	 */
	@Override
	public void becomesAttracted() {
	}
}
