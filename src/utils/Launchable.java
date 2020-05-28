package utils;

import launchControllers.LaunchController;

/**
 * A special callback that issues a launch event.
 *
 * @see LaunchController
 *
 * @author 蔡辉宇
 */
public interface Launchable extends Callback {
    /**
     * Logic for a launch event goes here.
     */
    void issueLaunchEvent();

    default void callback() {
        issueLaunchEvent();
    }
}
