package main.launchControllers;

import main.utils.Condition;

/**
 * Condition for aircraft / weapon / bonus launch.
 *
 * @see LaunchController
 *
 * @author  蔡辉宇
 */
public interface LaunchCondition extends Condition {
    /**
     * An alias method to {@link #conditionSatisfied()}.
     * @return If the launch condition is satisfied.
     */
    boolean shouldLaunchNow();

    /**
     * Inherited from {@link Condition} that evaluates the launch condition.
     * @return If the launch condition is satisfied.
     */
    default boolean conditionSatisfied() {
        return shouldLaunchNow();
    }
}
