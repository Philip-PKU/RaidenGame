package test.launchControllers;

import main.launchControllers.LaunchCondition;
import main.launchControllers.TwoStagedPeriodicLaunchCondition;
import org.junit.Test;

import static main.World.rand;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.Constants.numOfTrails;

public class TwoStagedPeriodicLaunchConditionTest {
    volatile boolean stageTransitioned;

    @Test
    public void test() {
        int cooldown1 = rand.nextInt(5), initCooldown1 = rand.nextInt(3) + 1;
        int cooldown2 = rand.nextInt(5), initCooldown2 = rand.nextInt(3) + 1;
        LaunchCondition launchCondition = new TwoStagedPeriodicLaunchCondition(cooldown1, initCooldown1, cooldown2, initCooldown2, numOfTrails,
                () -> stageTransitioned = true);
        assertFalse(stageTransitioned);

        for (int j = 0; j < initCooldown1 - 1; ++j) {
            assertFalse(launchCondition.shouldLaunchNow());
        }
        assertTrue(launchCondition.shouldLaunchNow());

        for (int i = 0; i < numOfTrails - 1; ++i) {
            for (int j = 0; j < cooldown1 - 1; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
            assertTrue(launchCondition.shouldLaunchNow());
        }

        assertTrue(stageTransitioned);

        for (int j = 0; j < initCooldown2 - 1; ++j) {
            assertFalse(launchCondition.shouldLaunchNow());
        }
        assertTrue(launchCondition.shouldLaunchNow());

        for (int i = 0; i < numOfTrails - 1; ++i) {
            for (int j = 0; j < cooldown2 - 1; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
            assertTrue(launchCondition.shouldLaunchNow());
        }
    }
}
