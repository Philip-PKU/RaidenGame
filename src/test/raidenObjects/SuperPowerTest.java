package test.raidenObjects;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonuses.SuperPowerBonus;
import main.utils.Faction;
import main.utils.PlayerController;
import org.junit.Test;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;

public class SuperPowerTest {
	@Test
	public void test() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
		float x = player.getX();
		float y = player.getY();
		new SuperPowerBonus(x, y).interactWith(player);

		assertEquals(1, player.getAvailableSuperpowers());
	}
}
