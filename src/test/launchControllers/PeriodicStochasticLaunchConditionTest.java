package test.launchControllers;

import main.launchControllers.LaunchCondition;
import main.launchControllers.PeriodicStochasticLaunchCondition;
import org.junit.Test;

import java.util.Random;

import static main.world.World.rand;
import static org.junit.Assert.*;
import static test.Constants.numOfTrails;

public class PeriodicStochasticLaunchConditionTest {
    @Test
    public void test() {
        int cooldown = 1 + rand.nextInt(4);
        int initCooldown = 1 + rand.nextInt(10);
        float prob = 0.3f + 0.4f * rand.nextFloat();
        LaunchCondition launchCondition = new PeriodicStochasticLaunchCondition(cooldown, initCooldown, prob);

        rand.setSeed(1);
        Random myRand = new Random();
        myRand.setSeed(1);

        for (int j = 0; j < initCooldown - 1; ++j) {
            assertFalse(launchCondition.shouldLaunchNow());
        }
        assertTrue(launchCondition.shouldLaunchNow());
        for (int i = 0; i < numOfTrails; ++i) {
            for (int j = 0; j < cooldown - 1; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
            assertEquals(launchCondition.shouldLaunchNow(), myRand.nextFloat() < prob);
        }
    }
}