package utils;

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

    public void extendDurationBy(int additionalSteps) {
        remainingSteps += additionalSteps;
    }
}
