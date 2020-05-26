package launchControllers;

public class LaunchController {
    LaunchEventScheduler launchEventScheduler;
    Launchable launchable;
    boolean activated = false;

    public LaunchController(LaunchEventScheduler launchEventScheduler,
                            Launchable launchable) {
        this.launchEventScheduler = launchEventScheduler;
        this.launchable = launchable;
    }

    public LaunchController() {
    }

    public void setLaunchEventScheduler(LaunchEventScheduler launchEventScheduler) {
        this.launchEventScheduler = launchEventScheduler;
    }

    public void setLaunchable(Launchable launchable) {
        this.launchable = launchable;
    }

    public void launchIfPossible() {
        if (activated && launchEventScheduler.shouldLaunch()) {
            launchable.issueLaunchEvent();
        }
    }

    public void activate() {
        activated = true;
    }
}
