package test.raidenObjects;

import static main.World.aircraftList;
import static main.World.interactantList;
import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.TrackingBulletLaunchController;
import main.utils.Faction;
import main.utils.InitLocation;
import main.utils.PlayerController;

public class TrackingBulletTest {
	@Test
	public void test() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		MiddleShootingAircraft enemy = new MiddleShootingAircraft(InitLocation.LOC_RANDOM);
		aircraftList.add(enemy);
		player.registerTrackingBulletLaunchController(player.new TrackingBulletLaunchController(0), true);
		for(int timeStep = 0; timeStep < 2000; timeStep++) {
			player.step();
            interactantList.forEach(BaseRaidenObject::step);
            interactantList.removeIf(BaseRaidenObject::isInvisibleOrOutOfWorld);
		}
		assertFalse(enemy.isAlive());
	}
}
