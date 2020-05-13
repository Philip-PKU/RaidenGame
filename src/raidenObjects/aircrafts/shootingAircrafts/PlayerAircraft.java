package raidenObjects.aircrafts.shootingAircrafts;

import motionControllers.KeyboardMotionController;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.PlayerController;
import utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;
    PlayerController playerController;
    public PlayerAircraft(float x, float y, RaidenObjectOwner owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                100, 0, 100,
                2, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        this.playerController = playerController;
        if (playerController == PlayerController.KEYBOARD1)
            this.registerMotionController(new KeyboardMotionController(keyAdapter1, 5));
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
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), 6);
            new StandardPlayerBullet(getX(), getMinY(), getOwner(), -6);
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
