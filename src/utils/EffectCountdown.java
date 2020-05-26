package utils;

import static world.World.desiredFPS;

public class EffectCountdown {
    int remainingSteps;

    public EffectCountdown() {
        this(0);
    }

    public EffectCountdown(int initCountdown) {
        remainingSteps = initCountdown;
    }

    public void step() {
        if (isEffective()) {
            --remainingSteps;
        }
    }

    public boolean isEffective() {
        return remainingSteps > 0;
    }

    public int getRemainingSteps() {
        return remainingSteps;
    }

    public void extendDurationBy(int additionalSteps) {
        remainingSteps += additionalSteps;
    }

    public void reset() {
        if (remainingSteps > desiredFPS)
            remainingSteps = desiredFPS;
    }
}
