package launchControllers;

public class SimpleLaunchControllerWithLevel<T extends LaunchCondition> extends SimpleLaunchController<T> {
    public final int level;

    public SimpleLaunchControllerWithLevel(String name, int level) {
        super(name);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
