package main.launchControllers;

import main.utils.RaidenKeyAdapter;

/**
 * A launch condition for superpower launch controlled by keyboard adapter.
 *
 * @author 蔡辉宇
 */
public class KeyboardSuperpowerLaunchCondition implements LaunchCondition {
    final int coolDown;  // to prevent the user from launching a bomb too frequently
    int internalStep;
    RaidenKeyAdapter keyAdapter;
    LaunchCondition launchCondition;

    /**
     * Constructor.
     *
     * @param cooldown Cooldown for superpower launch (to prevent launching multiple superpowers in one key press).
     * @param keyAdapter A {@link RaidenKeyAdapter} controlling this LaunchCondition.
     * @param additionalConstraints Additional constraint that should be satisfied before superpower launch (Written in
     *                              {@link main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft}.
     *
     */
    public KeyboardSuperpowerLaunchCondition(int cooldown, RaidenKeyAdapter keyAdapter,
                                             LaunchCondition additionalConstraints) {
        this.coolDown = cooldown;
        this.internalStep = cooldown;
        this.keyAdapter = keyAdapter;
        this.launchCondition = additionalConstraints;
    }

    boolean step() {
        if (internalStep >= coolDown) {
            return true;
        } else {
            ++internalStep;
            return false;
        }
    }

    @Override
    public boolean shouldLaunchNow() {
        // It is imperative to check the bomb state first. Otherwise the bomb state won't be able to reset,
        // and once the additional constraint is satisfied, a bomb will be launched, even though the player tried
        // to launch it a long time ago and doesn't necessarily wants to launch it now.
        boolean pressedBombKey = (keyAdapter.getBombState() & keyAdapter.BOMB) != 0;
        boolean passedCooldown = step();
        boolean satisfiedConstraint = launchCondition.shouldLaunchNow();
        if (pressedBombKey && passedCooldown && satisfiedConstraint) {
            internalStep = 1;
            return true;
        }
        return false;
    }
}
