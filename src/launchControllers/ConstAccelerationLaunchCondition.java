package launchControllers;

import utils.GameLevel;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static world.World.rand;

public class ConstAccelerationLaunchCondition implements LaunchCondition {
    int eventInterval, count, remainingEvents, timeOfNextEvent;
    float curDoubleProb, probAcceleration, maxDoubleProb, cooldownDeceleration, minCooldown, curCooldown;
    boolean launching;

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

            launching = true;
            remainingEvents = getEventsPerLaunch();
            timeOfNextEvent = count;
        }
        if (launching && remainingEvents > 0 && timeOfNextEvent == count) {
            --remainingEvents;
            timeOfNextEvent -= eventInterval;
            return true;
        }
        return false;
    }
}
