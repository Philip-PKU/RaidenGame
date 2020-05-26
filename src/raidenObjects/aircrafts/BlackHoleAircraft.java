package raidenObjects.aircrafts;

import motionControllers.ConstSpeedYMotionController;
import utils.Faction;

public final class BlackHoleAircraft extends BaseAircraft {
	public BlackHoleAircraft(float x, float y) {
		super("BlackHoleAircraft", x, y, 65, 65, Faction.ENEMY,
				Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0);
		 this.registerMotionController(new ConstSpeedYMotionController(0.5f));
	}

	/**
	 * Ignore any damage received. This is a blackhole!
	 */
	@Override
	public void receiveDamage(int damage) {
	}
}
