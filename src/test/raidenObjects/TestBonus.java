package test.raidenObjects;

import main.launchControllers.LaunchCondition;
import main.launchControllers.SimpleLaunchControllerWithLevel;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonus.*;
import main.raidenObjects.weapons.BigBullet;
import main.utils.Faction;
import main.utils.PlayerController;
import org.junit.Test;

import static main.world.World.windowHeight;
import static main.world.World.windowWidth;
import static org.junit.Assert.*;

/**
 * Some basic tests for raidenObjects.bonus
 * 
 * @author 张哲瑞
 *
 */
class TestBonus {

	@Test
	public void test1() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new CureBonus(x, y).interactWith(player);
		new CoinBonus(x + 200, y + 300, 0).interactWith(player);
		player.receiveDamage(10, Faction.ENEMY);

		assertEquals(player.getHp(), player.getMaxHp() - 10);
		assertEquals(0, player.getCoin());
		
		new CureBonus(x, y).interactWith(player);
		assertEquals(player.getHp(), player.getMaxHp());
	}
	
	@Test
	public void test2() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new CureBonus(x + 200, y + 200).interactWith(player);
		new CoinBonus(x, y, 0).interactWith(player);
		player.receiveDamage(10, Faction.ENEMY);

		assertEquals(player.getHp(), player.getMaxHp() - 10);
		assertEquals(10, player.getCoin());
		assertEquals(player.getHp(), player.getMaxHp() - 10);
	}
	
	@Test
	public void test3() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new BigBullet(x, y, x, y).interactWith(player);
		new InvincibleBonus(player.getX(), player.getY()).interactWith(player);
		new BigBullet(x, y, x + 10, y + 10).interactWith(player);
		
		assertTrue(player.getInvincibleCountdown().isEffective());
		assertEquals(player.getHp(), player.getMaxHp() - BigBullet.getStaticDamage());
	}
	
	@Test
	public void test4() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new MagnetBonus(x, y).interactWith(player);
		InvincibleBonus ib = new InvincibleBonus(player.getX(), player.getY());
		ib.interactWith(player);
		assertTrue(player.getMagnetCountdown().isEffective());
		assertTrue(ib.isAttracted());
		assertFalse(ib.isAlive());
	}
	
	@Test
	public void test5() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new SuperPowerBonus(x, y).interactWith(player);

		assertEquals(1, player.getAvailableSuperpowers());
	}
	
	@Test
	public void test6() {
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
