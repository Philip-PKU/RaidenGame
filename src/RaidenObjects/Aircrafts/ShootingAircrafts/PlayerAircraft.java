package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.StandardPlayerBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static World.World.*;

public class PlayerAircraft extends BaseShootingAircraft {
    private static final int hitSizeX = 25, hitSizeY = 20;
    public PlayerAircraft(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller) {
        super("Player0", x, y, 50, 40, 8,
        owner, controller, 100, 0, 100, 2, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
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
        if ((gameStep.intValue() - gameStepAtBirth) % getWeaponCoolDown() == 0) {
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), 0.0f);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), 6.0f);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), getController(), -6.0f);
        }
    }

    @Override
    protected void initSpeed() {
        super.initSpeed();
        if (controller.isKeyboard()) {
            int arrowState = keyAdapter.getArrowState();
            if ((arrowState & keyAdapter.left) != 0 && !isOutOfWorld(getX() - getMaxSpeed(), getY()))
                setSpeedX(-getMaxSpeed());
            if ((arrowState & keyAdapter.right) != 0 && !isOutOfWorld(getX() + getMaxSpeed(), getY()))
                setSpeedX(getMaxSpeed());
            if ((arrowState & keyAdapter.up) != 0 && !isOutOfWorld(getX(), getY() - getMaxSpeed()))
                setSpeedY(-getMaxSpeed());
            if ((arrowState & keyAdapter.down) != 0 && !isOutOfWorld(getX(), getY() + getMaxSpeed()))
                setSpeedY(getMaxSpeed());
        }
        else {
            if ((gameStep.intValue() - gameStepAtBirth) % 10 < 5)
                setSpeedX(-getMaxSpeed());
            else
                setSpeedX(getMaxSpeed());
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        markAsOffScreen();
        if (getOwner().isPlayer1())
            player1 = null;
        else if (getOwner().isPlayer2())
            player2 = null;
    }
}
