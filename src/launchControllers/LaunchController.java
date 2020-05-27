package launchControllers;

public class LaunchController <T extends LaunchEventScheduler> {
    T launchEventScheduler;
    Launchable launchable;
    boolean activated = false;

    public LaunchController(T launchEventScheduler,
                            Launchable launchable) {
        this.launchEventScheduler = launchEventScheduler;
        this.launchable = launchable;
    }

    public LaunchController() {
    }

    public T getLaunchEventScheduler() {
        return launchEventScheduler;
    }

    public Launchable getLaunchable() {
        return getLaunchable();
    }

    public void setLaunchEventScheduler(T launchEventScheduler) {
        this.launchEventScheduler = launchEventScheduler;
    }

    public void setLaunchable(Launchable launchable) {
        this.launchable = launchable;
    }

    public void launchIfPossible() {
        if (activated && launchEventScheduler.shouldLaunchNow()) {
            launchable.issueLaunchEvent();
        }
    }

    public void activate() {
        activated = true;
    }

    public void deactivate() {
        activated = false;
    }
}
