package test.raidenObjects;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonuses.CoinBonus;
import main.raidenObjects.bonuses.CureBonus;
import main.utils.Faction;
import main.utils.PlayerController;
import org.junit.Test;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertEquals;

public class CoinTest {
	@Test
	public void test() {
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
}
