package raidenObjects.aircrafts;

import motionControllers.ConstSpeedYMotionController;
import utils.Faction;

public final class BlackHoleAircraft extends BaseAircraft {
	private static int staticMaxHp = Integer.MAX_VALUE;

	public BlackHoleAircraft(float x, float y) {
		super("BlackHoleAircraft", x, y, 65, 65, Faction.ENEMY,
				staticMaxHp, 0, Integer.MAX_VALUE, 0);
		 this.registerMotionController(new ConstSpeedYMotionController(0.5f));
	}

	public static int getStaticMaxHp() {
		return staticMaxHp;
	}

	public static void setStaticMaxHp(int staticMaxHp) {
		BlackHoleAircraft.staticMaxHp = staticMaxHp;
	}

	/**
	 * Ignore any damage received. This is a blackhole!
	 */
	@Override
	public void receiveDamage(int damage) {
	}
}
