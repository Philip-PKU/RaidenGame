package main.launchControllers;

import main.utils.Callback;

/**
 * A two-staged periodic Launch Condition.
 * The two stages usually have different cooldown and initCooldown values.
 * Stage transition happens after {@link #numOfStageOneLaunches} launches.
 *
 * @see PeriodicLaunchCondition
 *
 * @author 蔡辉宇
 */
public class TwoStagedPeriodicLaunchCondition implements LaunchCondition {
    protected int cooldown1, cooldown2, curCooldown, count, initCooldown2, numOfStageOneLaunches;
    protected Callback stageTransitionCallback;

    /**
     * Constructor.
     *
     * @param cooldown1 Number of steps between each launch cycle after the first launch in stage 1.
     * @param initCooldown1 Number of steps between activation and first launch in stage 1.
     * @param cooldown2 Number of steps between each launch cycle after the first launch in stage 2.
     * @param initCooldown2 Number of steps between activation and first launch in stage 2.
     * @param numOfStageOneLaunches Number of stage 1 launches before stage transition happens.
     * @param stageTransitionCallback A {@link Callback} object containing logic when stage transition occurs.
     */
    public TwoStagedPeriodicLaunchCondition(int cooldown1, int initCooldown1, int cooldown2, int initCooldown2,
                                            int numOfStageOneLaunches, Callback stageTransitionCallback) {
        if (cooldown1 < 0 || initCooldown1 < 0 || cooldown2 < 0 || initCooldown2 < 0 || numOfStageOneLaunches < 0) {
            throw new IllegalArgumentException(("cooldowns, initCooldowns, numOfStageOneLaunches must be non-negative."));
        }
        this.curCooldown = this.cooldown1 = cooldown1;
        this.count = initCooldown1;
        this.cooldown2 = cooldown2;
        this.initCooldown2 = initCooldown2;
        this.numOfStageOneLaunches = numOfStageOneLaunches;
        this.stageTransitionCallback = stageTransitionCallback;
    }

    public TwoStagedPeriodicLaunchCondition(int cooldown1, int initCooldown1, int cooldown2, int initCooldown2,
                                            int numOfStageOneLaunches) {
        this(cooldown1, initCooldown1, cooldown2, initCooldown2, numOfStageOneLaunches, null);
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
                if (numOfStageOneLaunches == 0) {
                    if (stageTransitionCallback != null)
                        stageTransitionCallback.callback();
                    curCooldown = initCooldown2;
                }
            } else {
                curCooldown = cooldown2;
            }
            count = curCooldown;
            return true;
        }
        return false;
    }
}
