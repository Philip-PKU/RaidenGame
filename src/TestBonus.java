import static org.junit.jupiter.api.Assertions.*;
import static world.World.windowHeight;
import static world.World.windowWidth;

import org.junit.jupiter.api.Test;

import launchControllers.LaunchCondition;
import launchControllers.SimpleLaunchControllerWithLevel;
import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import raidenObjects.bonus.BaseBonus;
import raidenObjects.bonus.CoinBonus;
import raidenObjects.bonus.CureBonus;
import raidenObjects.bonus.InvincibleBonus;
import raidenObjects.bonus.MagnetBonus;
import raidenObjects.bonus.SuperPowerBonus;
import raidenObjects.bonus.WeaponUpgradeBonus;
import raidenObjects.weapons.BigBullet;
import utils.Faction;
import utils.PlayerController;

/**
 * Some basic tests for raidenObjects.bonus
 * 
 * @author 张哲瑞
 *
 */
class TestBonus {

	@Test
	void test1() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new CureBonus(x, y).interactWith(player);
		new CoinBonus(x + 200, y + 300, 0).interactWith(player);
		player.receiveDamage(10, Faction.ENEMY);
		
		assertTrue(player.getHp() == player.getMaxHp() - 10);
		assertTrue(player.getCoin() == 0);
		
		new CureBonus(x, y).interactWith(player);
		assertTrue(player.getHp() == player.getMaxHp());
	}
	
	@Test
	void test2() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new CureBonus(x + 200, y + 200).interactWith(player);
		new CoinBonus(x, y, 0).interactWith(player);
		player.receiveDamage(10, Faction.ENEMY);
		
		assertTrue(player.getHp() == player.getMaxHp() - 10);
		assertTrue(player.getCoin() == 10);
		assertTrue(player.getHp() == player.getMaxHp() - 10);
	}
	
	@Test
	void test3() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new BigBullet(x, y, x, y).interactWith(player);
		new InvincibleBonus(player.getX(), player.getY()).interactWith(player);
		new BigBullet(x, y, x + 10, y + 10).interactWith(player);
		
		assertTrue(player.getInvincibleCountdown().isEffective());
		assertTrue(player.getHp() == player.getMaxHp() - BigBullet.getStaticDamage());
	}
	
	@Test
	void test4() {
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
	void test5() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new SuperPowerBonus(x, y).interactWith(player);
		
		assertTrue(player.getAvailableSuperpowers() == 1);
	}
	
	@Test
	void test6() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new WeaponUpgradeBonus(x, y, WeaponUpgradeBonus.UPDATE_WEAPON_TRACKING - 1).interactWith(player);

		SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController = (SimpleLaunchControllerWithLevel<? extends LaunchCondition>) player.getTrackingBulletLaunchController();
		
		System.out.print(trackingBulletLaunchController.getLevel());
		assertTrue(trackingBulletLaunchController.getLevel() == 0);
	}
}
