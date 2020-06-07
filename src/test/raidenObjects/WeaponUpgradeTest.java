package test.raidenObjects;

import main.launchControllers.LaunchCondition;
import main.launchControllers.SimpleLaunchControllerWithLevel;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonuses.WeaponUpgradeBonus;
import main.utils.Faction;
import main.utils.PlayerController;
import org.junit.Test;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;

public class WeaponUpgradeTest {
	@Test
	public void test() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new WeaponUpgradeBonus(x, y, WeaponUpgradeBonus.UPDATE_WEAPON_TRACKING - 1).interactWith(player);

		SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController = (SimpleLaunchControllerWithLevel<? extends LaunchCondition>) player.getTrackingBulletLaunchController();
		
		System.out.print(trackingBulletLaunchController.getLevel());
		assertEquals(0, trackingBulletLaunchController.getLevel());
	}
}
