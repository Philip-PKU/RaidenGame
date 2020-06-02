import static org.junit.jupiter.api.Assertions.*;
import static world.World.aircraftList;
import static world.World.interactantList;
import static world.World.windowHeight;
import static world.World.windowWidth;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Test;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft.TrackingBulletLaunchController;
import raidenObjects.weapons.BigBullet;
import raidenObjects.weapons.BigPlayerBullet;
import utils.Faction;
import utils.PlayerController;
import utils.InitLocation;

/**
 * Some basic tests for raidenObjects.weapons
 * 
 * @author 张哲瑞
 *
 */
class TestWeapons {

	@Test
	void test1() {
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
	
	@Test
	void test2() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigPlayerBullet(player.getX(), player.getY(), Faction.ENEMY, 0).interactWith(player);
		new BigPlayerBullet(player.getX(), player.getY(), player.getFaction(), 0).interactWith(player);
		assertTrue(player.getHp() == player.getMaxHp() - BigPlayerBullet.getDefaultDamage());
	}
	
	@Test
	void test3() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigBullet(player.getX(), player.getY(), player.getX(), player.getY()).interactWith(player);
		
		assertTrue(player.getHp() == player.getMaxHp() - BigBullet.getStaticDamage());
	}
}
