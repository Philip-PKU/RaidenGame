package test.launchControllers;

import main.launchControllers.KeyboardSuperpowerLaunchCondition;
import main.launchControllers.LaunchCondition;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static main.World.keyAdapter1;
import static main.World.rand;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.Constants.numOfTrails;

public class KeyboardSuperpowerLaunchConditionTest {
    @Test
    public void test() {
        int cooldown = 1 + rand.nextInt(3);
        LaunchCondition launchCondition = new KeyboardSuperpowerLaunchCondition(cooldown, keyAdapter1, () -> true);
        for (int i = 0; i < numOfTrails; ++i) {
            keyAdapter1.keyPressed(new KeyEvent(new JButton(), KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_PERIOD, KeyEvent.CHAR_UNDEFINED));
            assertTrue(launchCondition.shouldLaunchNow());
            for (int j = 0; j < cooldown - 1; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
            keyAdapter1.keyReleased(new KeyEvent(new JButton(), KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_PERIOD, KeyEvent.CHAR_UNDEFINED));
        }

        for (int i = 0; i < numOfTrails; ++i) {
            for (int j = 0; j < cooldown; ++j) {
                assertFalse(launchCondition.shouldLaunchNow());
            }
        }

        launchCondition = new KeyboardSuperpowerLaunchCondition(cooldown, keyAdapter1, () -> false);
        for (int i = 0; i < numOfTrails; ++i) {
            assertFalse(launchCondition.shouldLaunchNow());
        }
    }
}