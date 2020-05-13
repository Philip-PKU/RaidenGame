package raidenObjects.Aircrafts.shootingAircrafts;

import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;
    public PlayerAircraft(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller) {
        super("Player0", x, y, 50, 40, 5,
                owner, controller, 100, 0, 100,
                2, 0, 0f);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        hasReachedTargetPos = true;
    }

    @Override
    public int getHitSizeX() {
        return hitSizeX;
    }

    @Override
    public int getHitSizeY() {
        return hitSizeY;
    }

    @Override
    public File getImageFile() {
        return Paths.get("data", "images", "Player0.png").toFile();
    }

    public void shootWeapon() {
        if ((gameStep.intValue() - gameStepWhenReady) % getWeaponCoolDown() == 0) {
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), 0.0f);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), 6.0f);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), -6.0f);
        }
    }

    @Override
    protected void updateSpeed() {
        super.updateSpeed();
        if (controller.isKeyboard()) {
            int arrowState = keyAdapter.getArrowState();
            if ((arrowState & keyAdapter.left) != 0 &&
                    !isOutOfWindow(getMinX() - getMaxSpeed(), getMinY()) &&
                    !isOutOfWindow(getMinX() - getMaxSpeed(), getMaxY()))
                setSpeedX(-getMaxSpeed());
            if ((arrowState & keyAdapter.right) != 0 &&
                    !isOutOfWindow(getMaxX() + getMaxSpeed(), getMinY()) &&
                    !isOutOfWindow(getMaxX() + getMaxSpeed(), getMaxY()))
                setSpeedX(getMaxSpeed());
            if ((arrowState & keyAdapter.up) != 0 &&
                    !isOutOfWindow(getMinX(), getMinY() - getMaxSpeed()) &&
                    !isOutOfWindow(getMaxX(), getMinY() - getMaxSpeed()))
                setSpeedY(-getMaxSpeed());
            if ((arrowState & keyAdapter.down) != 0 &&
                    !isOutOfWindow(getMinX(), getMaxY() + getMaxSpeed()) &&
                    !isOutOfWindow(getMaxX(), getMaxY() + getMaxSpeed()))
                setSpeedY(getMaxSpeed());
        }
        else {
            if ((gameStep.intValue() - gameStepWhenReady) % 10 < 5)
                setSpeedX(-getMaxSpeed());
            else
                setSpeedX(getMaxSpeed());
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
        if (getOwner().isPlayer1())
            player1 = null;
        else if (getOwner().isPlayer2())
            player2 = null;
    }
}
