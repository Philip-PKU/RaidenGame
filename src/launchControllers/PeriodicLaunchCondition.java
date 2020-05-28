package launchControllers;

/**
 * A periodic launch condition.
 * After activation, it first waits for {@code initCooldown} steps, then commit the first launch.
 * From then on, each launch will have a {@link #cooldown}-step cooldown.
 * Each launch consists of {@link #eventsPerLaunch} launch events, which happens one after another with
 * {@link #eventInterval} steps of interval.
 *
 * @author 蔡辉宇
 */
public class PeriodicLaunchCondition implements LaunchCondition {
    protected int cooldown, curCooldown, eventInterval, eventsPerLaunch, remainingEvents, timeOfNextEvent;
    protected boolean launching = false;

    /**
     * Constructor.
     *
     * @param cooldown        Number of steps between each launch cycle after the first launch. This must be greater than
     *                        {@link #eventInterval} * ({@link #eventsPerLaunch} - 1).
     * @param initCooldown    Number of steps between activation and first launch.
     * @param eventInterval   Number of steps between two launch events in one launch.
     * @param eventsPerLaunch Number of launch events in one launch.
     */
    public PeriodicLaunchCondition(int cooldown, int initCooldown, int eventInterval, int eventsPerLaunch) {
        if (cooldown < 0 || initCooldown < 0 || eventInterval < 0 || eventsPerLaunch < 0) {
            throw new IllegalArgumentException(("cooldown, initCooldown, eventInterval, eventsPerPeriod " +
                    "must be non-negative."));
        }
        if (cooldown < eventInterval * (eventsPerLaunch - 1)) {
            throw new IllegalArgumentException("Unable to complete a launch in " + cooldown + " steps.");
        }
        this.cooldown = cooldown;
        this.curCooldown = initCooldown;
        this.eventInterval = eventInterval;
        this.eventsPerLaunch = eventsPerLaunch;
    }

    public PeriodicLaunchCondition(int cooldown, int initCooldown) {
        this(cooldown, initCooldown, 0, 1);
    }

    public int getCurCooldown() {
        return curCooldown;
    }

    @Override
    public boolean shouldLaunchNow() {
        --curCooldown;
        if (curCooldown <= 0) {
            curCooldown = cooldown;

            launching = true;
            remainingEvents = eventsPerLaunch;
            timeOfNextEvent = cooldown;
        }
        if (launching && remainingEvents > 0 && timeOfNextEvent == curCooldown) {
            --remainingEvents;
            timeOfNextEvent -= eventInterval;
            return true;
        }
        return false;
    }
}
