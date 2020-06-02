package test.launchControllers;

import main.launchControllers.ConstAccelerationLaunchCondition;
import main.launchControllers.LaunchCondition;
import org.junit.Test;

import java.util.Random;

import static main.world.World.rand;
import static org.junit.Assert.*;
import static test.Constants.numOfTrails;

public class ConstAccelerationLaunchConditionTest {
    @Test
    public void test() {
        Random myRand = new Random();
        myRand.setSeed(1);

        int cooldown = 2 + rand.nextInt(3);
        float doubleProb = 0.5f - 0.1f * rand.nextFloat(), probAcceleration = 0.01f + 0.05f * rand.nextFloat(), maxDoubleProb = 0.6f + 0.1f * rand.nextFloat();
        LaunchCondition launchCondition = new ConstAccelerationLaunchCondition(
                cooldown, 1, 2, 1, 1,
                doubleProb, probAcceleration, maxDoubleProb);

        rand.setSeed(1);
        assertTrue(launchCondition.shouldLaunchNow());  // initCooldown is 1

        for (int i = 0; i < numOfTrails; ++i) {
            assertEquals(launchCondition.shouldLaunchNow(), myRand.nextFloat() < doubleProb);
            doubleProb = Math.min(maxDoubleProb, doubleProb + probAcceleration);
            for (int j = 0; j < cooldown - 2; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
            assertTrue(String.format("%d", cooldown), launchCondition.shouldLaunchNow());
            cooldown = Math.max(2, cooldown - 1);
        }
    }
}
