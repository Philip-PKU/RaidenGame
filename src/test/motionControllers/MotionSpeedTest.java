package test.motionControllers;

import org.junit.Test;

import main.raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import main.utils.InitLocation;

import static main.World.aircraftList;
import static org.junit.Assert.assertTrue;

import java.util.Random;

public class MotionSpeedTest {
	
	@Test
	public void Test() {
		MiddleShootingAircraft enemy1 = new MiddleShootingAircraft(InitLocation.LOC_RANDOM);
		float oldX = enemy1.getX();
		float oldY = enemy1.getY();
		enemy1.step();
		float newX = enemy1.getX();
		float newY = enemy1.getY();
		assertTrue(oldX+enemy1.getSpeedX()==newX);
		assertTrue(oldY+enemy1.getSpeedY()==newY);
	}
}
