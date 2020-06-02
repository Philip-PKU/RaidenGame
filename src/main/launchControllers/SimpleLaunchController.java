package main.launchControllers;

import main.utils.Launchable;

/**
 * A simple implementation of {@link LaunchController}.
 * @param <T> Type of {@link #launchCondition}.
 *
 * @author 蔡辉宇
 */
public class SimpleLaunchController<T extends LaunchCondition> implements LaunchController {
    String name;
    T launchCondition;
    Launchable launchable;
    boolean activated = false;

    /**
     * Constructor.
     *
     * @param name Name (description) of the launch controller, for logging purposes.
     * @param launchCondition A {@link LaunchCondition} object judging when to launch.
     * @param launchable A {@link Launchable} object implementing the launch.
     */
    public SimpleLaunchController(String name, T launchCondition, Launchable launchable) {
        this.name = name;
        this.launchCondition = launchCondition;
        this.launchable = launchable;
    }

    public SimpleLaunchController(String name) {
        this.name = name;
    }

    public T getLaunchCondition() {
        return launchCondition;
    }

    @Override
    public String getName() {
        return name;
    }

    public Launchable getLaunchable() {
        return launchable;
    }

    public void setLaunchCondition(T launchCondition) {
        this.launchCondition = launchCondition;
    }

    public void setLaunchable(Launchable launchable) {
        this.launchable = launchable;
    }

    @Override
    public void launchIfPossible() {
        if (activated && launchCondition.shouldLaunchNow()) {
            launchable.issueLaunchEvent();
        }
    }

    @Override
    public void activate() {
        activated = true;
    }

    @Override
    public void deactivate() {
        activated = false;
    }
}
