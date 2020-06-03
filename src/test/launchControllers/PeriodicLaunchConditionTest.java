package test.launchControllers;

import main.launchControllers.LaunchCondition;
import main.launchControllers.PeriodicLaunchCondition;
import org.junit.Test;

import static main.World.rand;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.Constants.numOfTrails;

public class PeriodicLaunchConditionTest {
    @Test
    public void test() {
        int initCooldown = 1 + rand.nextInt(10);
        int eventInterval = 1 + rand.nextInt(2), eventsPerLaunch = 2 + rand.nextInt(2);
        int cooldown = eventInterval * eventsPerLaunch + rand.nextInt(3);
        LaunchCondition launchCondition = new PeriodicLaunchCondition(cooldown, initCooldown, eventInterval, eventsPerLaunch);

        for (int j = 0; j < initCooldown - 1; ++j) {
            assertFalse(launchCondition.shouldLaunchNow());
        }
        for (int i = 0; i < numOfTrails; ++i) {
            for (int event = 0; event < eventsPerLaunch; ++event) {
                assertTrue(launchCondition.shouldLaunchNow());
                for (int j = 0; j < eventInterval - 1; ++j) {
                    assertFalse(launchCondition.shouldLaunchNow());
                }
            }
            for (int j = 0; j < cooldown - eventInterval * eventsPerLaunch; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
        }
    }
}