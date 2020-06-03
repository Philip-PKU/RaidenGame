package test.raidenObjects;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonus.InvincibleBonus;
import main.raidenObjects.weapons.BigBullet;
import main.utils.Faction;
import main.utils.PlayerController;

public class BigBulletTest {
	@Test
	public void test() {
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
}
