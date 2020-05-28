package launchControllers;

public class SimpleLaunchController<T extends LaunchCondition> implements LaunchController {
    String name;
    T LaunchCondition;
    Launchable launchable;
    boolean activated = false;

    public SimpleLaunchController(String name, T LaunchCondition, Launchable launchable) {
        this.name = name;
        this.LaunchCondition = LaunchCondition;
        this.launchable = launchable;
    }

    public SimpleLaunchController(String name) {
        this.name = name;
    }

    public T getLaunchCondition() {
        return LaunchCondition;
    }

    @Override
    public String getName() {
        return name;
    }

    public Launchable getLaunchable() {
        return launchable;
    }

    public void setLaunchCondition(T launchCondition) {
        this.LaunchCondition = launchCondition;
    }

    public void setLaunchable(Launchable launchable) {
        this.launchable = launchable;
    }

    @Override
    public void launchIfPossible() {
        if (activated && LaunchCondition.shouldLaunchNow()) {
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
