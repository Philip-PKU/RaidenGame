package launchControllers;

import utils.keyAdapters.BaseRaidenKeyAdapter;

public class KeyboardWeaponLaunchEventScheduler extends PeriodicLaunchEventScheduler {
    protected BaseRaidenKeyAdapter keyAdapter;

    public KeyboardWeaponLaunchEventScheduler(int cooldown, BaseRaidenKeyAdapter keyAdapter) {
        super(cooldown, 0);
        this.keyAdapter = keyAdapter;
    }

    @Override
    public boolean shouldLaunch() {
        return super.shouldLaunch() && (keyAdapter.getWeaponState() & keyAdapter.SHOOT) != 0;
    }
}
