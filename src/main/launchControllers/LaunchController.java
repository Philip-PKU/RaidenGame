package main.launchControllers;

import main.utils.Launchable;

/**
 * Controller for aircraft / weapon / bonus launch.
 * Though not required, the implementation usually involves a {@link LaunchCondition} and a {@link Launchable}.
 *
 * See {@link SimpleLaunchController} for more details.
 *
 * @author 蔡辉宇
 */
public interface LaunchController {
    /**
     * Launch if it is time to do so.
     * Each time the method is called on an activated LaunchController, it invokes
     * {@link LaunchCondition#shouldLaunchNow()}, and if the latter returns true,
     * issues a launch event by calling {@link Launchable#issueLaunchEvent()}.
     */
    void launchIfPossible();

    /**
     * Activate the launch controller.
     */
    void activate();

    /**
     * Deactivate the launch controller.
     */
    void deactivate();

    /**
     * Get the name (description) of the LaunchController. Utility for logging.
     * @return The name of the LaunchController.
     */
    String getName();
}
