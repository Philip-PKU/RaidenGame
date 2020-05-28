package launchControllers;

public class TwoStagedPeriodicLaunchCondition implements LaunchCondition {
    protected int cooldown1, cooldown2, curCooldown, count, initCooldown2, numOfStageOneLaunches;

    public TwoStagedPeriodicLaunchCondition(int cooldown1, int initCooldown1, int cooldown2, int initCooldown2,
                                            int numOfStageOneLaunches) {
        if (cooldown1 < 0 || initCooldown1 < 0 || cooldown2 < 0 || initCooldown2 < 0 || numOfStageOneLaunches < 0) {
            throw new IllegalArgumentException(("cooldowns, initCooldowns, numOfStageOneLaunches must be non-negative."));
        }
        this.curCooldown = this.cooldown1 = cooldown1;
        this.count = initCooldown1;
        this.cooldown2 = cooldown2;
        this.initCooldown2 = initCooldown2;
        this.numOfStageOneLaunches = numOfStageOneLaunches;
    }

    public int getCurCooldown() {
        return curCooldown;
    }

    @Override
    public boolean shouldLaunchNow() {
        --count;
        if (count <= 0) {
            if (numOfStageOneLaunches > 0) {
                --numOfStageOneLaunches;
            } else if (numOfStageOneLaunches == 0) {
                --numOfStageOneLaunches;
                curCooldown = initCooldown2;
            } else {
                curCooldown = cooldown2;
            }
            count = curCooldown;
            return true;
        }
        return false;
    }
}
