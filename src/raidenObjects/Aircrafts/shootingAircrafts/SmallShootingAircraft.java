package raidenObjects.Aircrafts.shootingAircrafts;

import raidenObjects.weapons.bullets.SmallBullet;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static world.World.*;

public final class SmallShootingAircraft extends BaseShootingAircraft {
    private static float maxSpeedX = 3.0f;
    private float targetX, targetY;
    private PlayerAircraft target;

    public SmallShootingAircraft(float x, float y) {
        super("SmallShootingAircraft", x, y, 35, 21, 0.4f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                100, 13, 50,
                126, 30, 8f);
        target = getClosestPlayer();
        if (getX() < windowWidth/2f)
            targetX = rand.nextInt(windowWidth / 3);
        else
            targetX = windowWidth - rand.nextInt(windowWidth / 3);
        float dyToBottom = windowHeight - target.getY();
        if (dyToBottom < windowHeight / 3f)
            targetY = windowHeight - rand.nextFloat() * (windowHeight/3f) - windowHeight/6f;
        else
            targetY = rand.nextFloat() * (dyToBottom - windowHeight/6f) + target.getY();
    }

    public void shootWeapon() {
        int gameStepSinceReady = gameStep.intValue() - gameStepWhenReady - getInitWeaponCoolDown();
        if (hasReachedTargetPos && gameStepSinceReady % getWeaponCoolDown() == 0) {
            if (target == null)
                target = getClosestPlayer();
            new SmallBullet(getX() - 10, getMaxY(), target.getX(), target.getY());
        }
    }

    @Override
    protected void updateSpeed() {
        super.updateSpeed();
        if (!hasReachedTargetPos) {
            if ((abs(getX() - targetX) < getInitMaxSpeed() && abs(getY() - targetY) < getInitMaxSpeed()) ||
                    (abs(getX() - target.getX()) < getSizeX() + target.getSizeX() &&
                            abs(getY() - target.getY()) < getSizeY() + target.getSizeY())) {
                hasReachedTargetPos = true;
                gameStepWhenReady = gameStep.intValue();
            }
            else {
                float dx = targetX - getX(), dy = targetY - getY();
                float speedNorm = (float) sqrt(dx * dx + dy * dy);
                float normalizer = getInitMaxSpeed() / speedNorm;
                setSpeedX(dx * normalizer);
                setSpeedY(dy * normalizer);
                return;
            }
        }
        setSpeedY(getMaxSpeed());
    }
}
