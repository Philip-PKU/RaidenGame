package test.raidenObjects;

import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.raidenObjects.bonuses.InvincibleBonus;
import main.raidenObjects.bonuses.MagnetBonus;
import main.utils.Faction;
import main.utils.PlayerController;
import org.junit.Test;

import static main.World.windowHeight;
import static main.World.windowWidth;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MagnetTest {
	@Test
	public void test() {
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
}
