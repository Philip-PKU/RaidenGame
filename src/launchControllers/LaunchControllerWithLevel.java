package launchControllers;

public class LaunchControllerWithLevel <T extends LaunchEventScheduler> extends LaunchController<T> {
    public final int level;

    public LaunchControllerWithLevel(int level) {
        super();
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
