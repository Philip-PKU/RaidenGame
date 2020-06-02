package test;

import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.weapons.BigBullet;
import main.raidenObjects.weapons.BigPlayerBullet;
import main.utils.Faction;
import main.utils.InitLocation;
import main.utils.PlayerController;
import org.junit.Test;

import static main.world.World.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Some basic tests for raidenObjects.weapons
 * 
 * @author 张哲瑞
 *
 */
class TestWeapons {

	@Test
	public void test1() {
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
	public void test2() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigPlayerBullet(player.getX(), player.getY(), Faction.ENEMY, 0).interactWith(player);
		new BigPlayerBullet(player.getX(), player.getY(), player.getFaction(), 0).interactWith(player);
		assertEquals(player.getHp(), player.getMaxHp() - BigPlayerBullet.getDefaultDamage());
	}
	
	@Test
	public void test3() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigBullet(player.getX(), player.getY(), player.getX(), player.getY()).interactWith(player);

		assertEquals(player.getHp(), player.getMaxHp() - BigBullet.getStaticDamage());
	}
}
