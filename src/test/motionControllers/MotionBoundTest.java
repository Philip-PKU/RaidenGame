package test.motionControllers;


import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.BumpingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.BigShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.Faction;
import main.utils.InitLocation;
import main.utils.PlayerController;
import org.junit.Test;

import static main.World.*;
import static org.junit.Assert.assertFalse;
import static test.Constants.numOfTrails;
public class MotionBoundTest {
	
	@Test
	public void Test() {
		PlayerAircraft player = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
        player.getInvincibleCountdown().extendDurationBy(9999);
		MiddleShootingAircraft enemy1 = new MiddleShootingAircraft(InitLocation.LOC_RANDOM);
		BigShootingAircraft enemy2 = new BigShootingAircraft(InitLocation.LOC_RANDOM);
		BumpingAircraft enemy3 = new BumpingAircraft(InitLocation.LOC_RANDOM);
		aircraftList.add(enemy1);
		aircraftList.add(enemy2);
		aircraftList.add(enemy3);
		for(int timeStep = 0; timeStep < numOfTrails; timeStep++) {
			player.step();
            interactantList.forEach(BaseRaidenObject::step);
            interactantList.removeIf(BaseRaidenObject::isInvisibleOrOutOfWorld);
            for (int i = 0;i < aircraftList.size() ; i++) {
            	aircraftList.forEach(aircraft -> {
                    float X = aircraft.getX();
                    float Y = aircraft.getY();
                    boolean flag = X < 0 || X >= windowWidth || Y < 0 || Y >= windowHeight;
                    assertFalse(flag);
                });
            }
		}
	}
}
