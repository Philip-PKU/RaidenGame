package main.launchControllers;

import main.utils.GameLevel;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static main.world.World.rand;

/**
 * Issue launch faster and faster, with a constant acceleration.
 *
 * @author 蔡辉宇
 */
public class ConstAccelerationLaunchCondition implements LaunchCondition {
    public int eventInterval, count, remainingEvents, timeOfNextEvent;
    public float curDoubleProb, probAcceleration, maxDoubleProb, cooldownDeceleration, minCooldown, curCooldown;

    /**
     * Constructor.
     *
     * @param maxCooldown Maximum cooldown.
     * @param cooldownDeceleration Decrement of cooldown after each launch.
     * @param minCooldown Minimum cooldown.
     * @param initCooldown Number of steps between activation and first launch.
     * @param eventInterval Number of steps between two launch events in one launch.
     * @param minDoubleProb Minimum probability of issuing two launch events in one launch.
     * @param probAcceleration Increment of doubleProb after each launch.
     * @param maxDoubleProb Maximum probability of issuing two launch events in one launch.
     */
    public ConstAccelerationLaunchCondition(float maxCooldown, float cooldownDeceleration, float minCooldown,
                                            int initCooldown, int eventInterval,
                                            float minDoubleProb, float probAcceleration, float maxDoubleProb) {
        this.cooldownDeceleration = cooldownDeceleration;
        this.minCooldown = minCooldown;
        this.curCooldown = maxCooldown;
        this.count = initCooldown;
        this.eventInterval = eventInterval;
        this.curDoubleProb = minDoubleProb;
        this.probAcceleration = probAcceleration;
        this.maxDoubleProb = maxDoubleProb;
    }

    public ConstAccelerationLaunchCondition(float maxCooldown, int initCooldown, float minDoubleProb,
                                            float probAcceleration, float maxDoubleProb) {
        this(maxCooldown, maxCooldown / 36f, maxCooldown / 2f,
                initCooldown, (int) (maxCooldown / 3f),
                minDoubleProb, probAcceleration, maxDoubleProb);
    }

    public void scaleByGameLevel(GameLevel gameLevel) {
        float[] coeffs = {1.5f, 1f, 0.7f, 0.5f};
        float coeff = coeffs[gameLevel.ordinal()];
        cooldownDeceleration *= coeff;
        minCooldown *= coeff;
        curCooldown *= coeff;
        count = (int) (count * coeff);
        eventInterval = (int) (eventInterval * coeff);
        curDoubleProb /= coeff;
        probAcceleration /= coeff;
        maxDoubleProb /= coeff;
    }

    int getCooldown() {
        float cooldown = curCooldown;
        curCooldown = max(cooldown - cooldownDeceleration, minCooldown);
        return (int) cooldown;
    }

    int getEventsPerLaunch() {
        float prob = curDoubleProb;
        curDoubleProb = min(curDoubleProb + probAcceleration, maxDoubleProb);
        return rand.nextFloat() < prob ? 2 : 1;
    }

    @Override
    public boolean shouldLaunchNow() {
        --count;
        if (count <= 0) {
            count = getCooldown();

            remainingEvents = getEventsPerLaunch();
            timeOfNextEvent = count;
        }
        if (remainingEvents > 0 && timeOfNextEvent == count) {
            --remainingEvents;
            timeOfNextEvent -= eventInterval;
            return true;
        }
        return false;
    }
}
