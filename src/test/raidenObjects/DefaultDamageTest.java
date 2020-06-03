package test.raidenObjects;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.weapons.BigPlayerBullet;
import main.utils.Faction;
import main.utils.PlayerController;

public class DefaultDamageTest {
	@Test
	public void test2() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigPlayerBullet(player.getX(), player.getY(), Faction.ENEMY, 0).interactWith(player);
		new BigPlayerBullet(player.getX(), player.getY(), player.getFaction(), 0).interactWith(player);
		assertEquals(player.getHp(), player.getMaxHp() - BigPlayerBullet.getDefaultDamage());
	}
}
