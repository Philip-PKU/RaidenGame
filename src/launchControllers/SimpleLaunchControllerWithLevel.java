package launchControllers;

/**
 * A simple implementation of {@link LaunchController}, with an additional field: level.
 * @param <T> Type of {@link #launchCondition}.
 *
 * @author 蔡辉宇
 */
public class SimpleLaunchControllerWithLevel<T extends LaunchCondition> extends SimpleLaunchController<T> {
    public final int level;

    /**
     * Constructor.
     *
     * @param name Name (description) of the launch controller, for logging purposes.
     * @param level An integer denoting the level of the LaunchController.
     */
    public SimpleLaunchControllerWithLevel(String name, int level) {
        super(name);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
