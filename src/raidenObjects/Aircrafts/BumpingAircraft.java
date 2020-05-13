package raidenObjects.Aircrafts;

import utils.RaidenObjectController;
import utils.RaidenObjectOwner;
import world.World;

import static world.World.gameStep;

public final class BumpingAircraft extends BaseAircraft {
    private static float initMaxSpeed = 2f, targetY = 60;  // after reaching target, begin bumping
    private boolean hasReachedTargetPos;

    public BumpingAircraft(float x, float y, float bumpingMaxSpeed) {
        super("BumpingAircraft", x, y, 30, 23, 14f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                100, 13, 50);
    }

    public BumpingAircraft(float x, float y) {
        this(x, y, 12f);
    }

    private float getInitMaxSpeed() {
        return initMaxSpeed;
    }

    protected void updateSpeed() {
        setSpeedX(0f);
        if (!hasReachedTargetPos) {
            if (getY() > targetY) {
                hasReachedTargetPos = true;
                gameStepWhenReady = gameStep.intValue();
            }
            else {
                setSpeedY(getInitMaxSpeed());
                return;
            }
        }
        float currentSpeed = (getY() - targetY) / (World.windowHeight - targetY)
                * (getMaxSpeed() - getInitMaxSpeed()) + getInitMaxSpeed();
        setSpeedY(currentSpeed);
    }
}
