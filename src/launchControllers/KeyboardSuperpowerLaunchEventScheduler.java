package launchControllers;

import utils.keyAdapters.BaseRaidenKeyAdapter;

import static world.World.gameStep;

public class KeyboardSuperpowerLaunchEventScheduler implements LaunchEventScheduler {
    final int coolDown;  // to prevent the user from launching a bomb too frequently
    int lastLaunch = 0;
    BaseRaidenKeyAdapter keyAdapter;
    LaunchEventScheduler launchEventScheduler;

    public KeyboardSuperpowerLaunchEventScheduler(int coolDown, BaseRaidenKeyAdapter keyAdapter,
                                                  LaunchEventScheduler additionalConstraints) {
        this.coolDown = coolDown;
        this.keyAdapter = keyAdapter;
        this.launchEventScheduler = additionalConstraints;
    }

    @Override
    public boolean shouldLaunch() {
        // It is imperative to check the bomb state first. Otherwise the bomb state won't be able to reset,
        // and once the additional constraint is satisfied, a bomb will be launched, even though the player tried
        // to launch it a long time ago and doesn't necessarily wants to launch it now.
        boolean pressedBombKey = (keyAdapter.getAndResetBombState() & keyAdapter.BOMB) != 0;
        boolean passedCooldown = gameStep.intValue() - lastLaunch >= coolDown;
        boolean satisfiedConstraint = launchEventScheduler.shouldLaunch();
        if (pressedBombKey && passedCooldown && satisfiedConstraint) {
            lastLaunch = gameStep.intValue();
            return true;
        }
        return false;
    }
}
