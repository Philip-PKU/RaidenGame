package test.raidenObjects;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.weapons.BigBullet;
import main.utils.Faction;
import main.utils.PlayerController;

public class StaticDamageTest {
	@Test
	public void test() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		new BigBullet(player.getX(), player.getY(), player.getX(), player.getY()).interactWith(player);

		assertEquals(player.getHp(), player.getMaxHp() - BigBullet.getStaticDamage());
	}
}
