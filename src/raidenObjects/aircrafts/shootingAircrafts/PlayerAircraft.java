package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.PeriodicLaunchController;
import motionControllers.KeyboardMotionController;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.Faction;
import utils.PlayerController;

import java.io.File;
import java.nio.file.Paths;

import static world.World.interactantList;
import static world.World.keyAdapter1;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;
    PlayerController playerController;
    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                100, 0, 100);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        this.playerController = playerController;
        if (playerController == PlayerController.KEYBOARD1)
            this.registerMotionController(new KeyboardMotionController(keyAdapter1, 5));
        this.registerWeaponLaunchController(new PeriodicLaunchController(2, 0, () -> {
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0));
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 8));
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -8));
        }));
        this.weaponLaunchController.activate();
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

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }
}
