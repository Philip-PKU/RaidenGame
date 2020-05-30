package utils;

import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;

import static world.World.desiredFPS;

/**
 * A countdown utility for {@link raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft} effects.
 *
 * @see PlayerAircraft#getInvincibleCountdown()
 * @see PlayerAircraft#getMagnetCountdown()
 *
 * @author 蔡辉宇
 */
public class EffectCountdown {
    int remainingSteps;
    String soundEffectPath;

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
