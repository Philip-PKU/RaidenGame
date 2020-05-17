package raidenObjects.aircrafts.bonus;

import static world.World.windowWidth;

import java.util.Arrays;

import launchControllers.PeriodicLaunchController;
import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BaseShootingAircraft;
import utils.Faction;

public final class CoinBonus extends BaseShootingAircraft{
	static int [] coins = {10, 20, 40};
	static int [] sizes = {10, 18, 25};
	int coin;
	public CoinBonus(float x, float y, int coin) {
		super("CoinBonus" + coins[coin], x, y, sizes[coin], sizes[coin], Faction.BOUNS,
				1, 0, 0);
		this.coin = coins[coin];
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
		aircraft.receiveCoin(coin);
	}
}
