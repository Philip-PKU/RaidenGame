package launchControllers;

import utils.Callback;
import utils.keyAdapters.BaseRaidenKeyAdapter;

/**
 * A wrapper for creating keyboard controlled weapon launch controller.
 * @param <T> Type of the underlying {@link LaunchCondition}.
 *
 * @author 蔡辉宇
 */
public class KeyboardWeaponLaunchCondition<T extends LaunchCondition> implements LaunchCondition {
    protected BaseRaidenKeyAdapter keyAdapter;
    protected T launchCondition;

    /**
     * Constructor.
     *
     * @param keyAdapter A {@link BaseRaidenKeyAdapter} controlling this LaunchCondition.
     * @param launchCondition The underlying {@link LaunchCondition} that must be satisfied when the corresponding key is
     *                        pressed before a launch could be issued.
     */
    public KeyboardWeaponLaunchCondition(BaseRaidenKeyAdapter keyAdapter, T launchCondition) {
        this.keyAdapter = keyAdapter;
        this.launchCondition = launchCondition;
    }

    /**
     * Construct a {@link KeyboardWeaponLaunchCondition} wrapping a {@link PeriodicLaunchCondition}.
     *
     * @param cooldown   Cooldown of the {@link PeriodicLaunchCondition}.
     * @param keyAdapter A {@link BaseRaidenKeyAdapter} controlling this LaunchCondition.
     * @return A new {@link KeyboardWeaponLaunchCondition} object.
     *
     * @see PeriodicLaunchCondition
     */
    public static KeyboardWeaponLaunchCondition<PeriodicLaunchCondition> createFromPeriodicLaunchCondition(int cooldown, BaseRaidenKeyAdapter keyAdapter) {
        return new KeyboardWeaponLaunchCondition<>(keyAdapter, new PeriodicLaunchCondition(cooldown, 0));
    }

    /**
     * Construct a {@link KeyboardWeaponLaunchCondition} wrapping a {@link TwoStagedPeriodicLaunchCondition}.
     *
     * @param cooldown1             Cooldown of the {@link PeriodicLaunchCondition} in stage 1.
     * @param cooldown2             Cooldown of the {@link PeriodicLaunchCondition} in stage 2.
     * @param numOfStageOneLaunches Number of stage one launches before transitioning to stage 2.
     * @param keyAdapter            A {@link BaseRaidenKeyAdapter} controlling this LaunchCondition.
     * @return A new {@link KeyboardWeaponLaunchCondition} object.
     *
     * @see TwoStagedPeriodicLaunchCondition
     */
    public static KeyboardWeaponLaunchCondition<TwoStagedPeriodicLaunchCondition> createFromTwoStagedPeriodicLaunchCondition(int cooldown1, int cooldown2, int numOfStageOneLaunches,
                                                                                                                             BaseRaidenKeyAdapter keyAdapter, Callback stageTransitionCallback) {
        return new KeyboardWeaponLaunchCondition<>(keyAdapter, new TwoStagedPeriodicLaunchCondition(cooldown1, 0, cooldown2, 0, numOfStageOneLaunches, stageTransitionCallback));
    }

    public static KeyboardWeaponLaunchCondition<TwoStagedPeriodicLaunchCondition> createFromTwoStagedPeriodicLaunchCondition(int cooldown1, int cooldown2, int numOfStageOneLaunches,
                                                                                                                             BaseRaidenKeyAdapter keyAdapter) {
        return createFromTwoStagedPeriodicLaunchCondition(cooldown1, cooldown2, numOfStageOneLaunches, keyAdapter, null);
    }

    @Override
    public boolean shouldLaunchNow() {
        return launchCondition.shouldLaunchNow() && (keyAdapter.getWeaponState() & keyAdapter.SHOOT) != 0;
    }
}
