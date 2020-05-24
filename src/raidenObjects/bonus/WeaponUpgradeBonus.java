package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

import static world.World.windowWidth;

public final class WeaponUpgradeBonus extends BaseBonus {
	static int [] weapons = {1, 2};
	int weapon;
	public WeaponUpgradeBonus(float x, float y,int type) {
		super("WeaponUpgradeBonus" + weapons[type], x, y, 20, 20, Faction.BONUS);
		weapon = weapons[type];
		 MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
		 MotionController XYController = XYMotionController.defaultFromXController(
	                XController, 1.5f);
		 this.registerMotionController(XYController);
	}
	
	@Override
	public void bonus(BaseAircraft aircraft) {
		aircraft.setWeaponType(weapon);
	}
}
