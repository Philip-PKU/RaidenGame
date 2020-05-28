package launchControllers;

import utils.keyAdapters.BaseRaidenKeyAdapter;

public class KeyboardWeaponLaunchConditionWrapper<T extends LaunchCondition> implements LaunchCondition {
    protected BaseRaidenKeyAdapter keyAdapter;
    protected T launchCondition;

    public KeyboardWeaponLaunchConditionWrapper(BaseRaidenKeyAdapter keyAdapter, T launchCondition) {
        this.keyAdapter = keyAdapter;
        this.launchCondition = launchCondition;
    }

    public static KeyboardWeaponLaunchConditionWrapper<PeriodicLaunchCondition> createFromPeriodicLaunchCondition(int cooldown, BaseRaidenKeyAdapter keyAdapter) {
        return new KeyboardWeaponLaunchConditionWrapper<>(keyAdapter, new PeriodicLaunchCondition(cooldown, 0));
    }

    public static KeyboardWeaponLaunchConditionWrapper<TwoStagedPeriodicLaunchCondition> createFromTwoStagedPeriodicLaunchCondition(int cooldown1, int cooldown2, int numOfStageOneLaunches,
                                                                                                                                    BaseRaidenKeyAdapter keyAdapter) {
        return new KeyboardWeaponLaunchConditionWrapper<>(keyAdapter, new TwoStagedPeriodicLaunchCondition(cooldown1, 0, cooldown2, 0, numOfStageOneLaunches));
    }

    @Override
    public boolean shouldLaunchNow() {
        return launchCondition.shouldLaunchNow() && (keyAdapter.getWeaponState() & keyAdapter.SHOOT) != 0;
    }
}
