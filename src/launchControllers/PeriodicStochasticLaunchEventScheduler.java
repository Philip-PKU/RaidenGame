package launchControllers;

import utils.GameLevel;

import static world.World.rand;

public class PeriodicStochasticLaunchEventScheduler implements LaunchEventScheduler {
    int cooldown, curCooldown;
    float prob;
    boolean firstRelease = true;

    public PeriodicStochasticLaunchEventScheduler(int cooldown, int initCooldown, float prob) {
        this.cooldown = cooldown;
        this.curCooldown = initCooldown;
        this.prob = prob;
    }

    @Override
    public boolean shouldLaunchNow() {
        --curCooldown;
        if (curCooldown > 0) {
            return false;
        }
        curCooldown = cooldown;
        if (firstRelease) {
            firstRelease = false;
            return true;
        }
        float randomFloat = rand.nextFloat();
        return randomFloat < prob;
    }

    public void scaleByGameLevel(GameLevel gameLevel) {
        float[] coeffs = {0.7f, 1f, 1.5f, 2f};
        float coeff = coeffs[gameLevel.ordinal()];
        cooldown = (int) (cooldown * coeff);
        curCooldown = (int) (curCooldown * coeff);
        prob /= coeff;
    }
}
