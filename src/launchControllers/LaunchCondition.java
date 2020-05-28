package launchControllers;

import utils.Condition;

public interface LaunchCondition extends Condition {
    boolean shouldLaunchNow();

    default boolean conditionSatisfied() {
        return shouldLaunchNow();
    }
}
