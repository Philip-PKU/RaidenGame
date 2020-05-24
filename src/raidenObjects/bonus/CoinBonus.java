package raidenObjects.bonus;

import motionControllers.HoveringXMotionController;
import motionControllers.MotionController;
import motionControllers.XYMotionController;
import raidenObjects.aircrafts.BaseAircraft;
import utils.Faction;

import static world.World.windowWidth;

public final class CoinBonus extends BaseBonus {
	static int [] coins = {10, 20, 40};
	static int [] sizes = {10, 18, 25};
	int coin;
	public CoinBonus(float x, float y, int coinIndex) {
		super("CoinBonus" + coins[coinIndex], x, y, sizes[coinIndex], sizes[coinIndex], Faction.BONUS);
		this.coin = coins[coinIndex];
		 MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
		 MotionController XYController = XYMotionController.defaultFromXController(
	                XController, 1.5f);
		 this.registerMotionController(XYController);
	}

	@Override
	public void bonus(BaseAircraft aircraft) {
		aircraft.receiveCoin(coin);
	}
}
