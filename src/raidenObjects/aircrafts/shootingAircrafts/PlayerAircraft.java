package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.KeyboardLaunchController;
import motionControllers.KeyboardMotionController;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.BaseRaidenKeyAdapter;
import utils.Faction;
import utils.PlayerController;

import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;

    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                100, 0, 100, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        BaseRaidenKeyAdapter keyAdapter = null;
        if (playerController == PlayerController.KEYBOARD1)
            keyAdapter = keyAdapter1;
        else if (playerController == PlayerController.KEYBOARD2)
            keyAdapter = keyAdapter2;
        this.setKeyAdapter(keyAdapter);
        this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        this.registerWeaponLaunchController(new KeyboardLaunchController(
                2, keyAdapter,  () -> {
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
        if (!isAlive())
            return null;

        if (owner.isPlayer1()) {
            if (getInvincibleCountdown().isEffective()) {
                return Paths.get("data", "images", "Player0WithShield.png").toFile();
            } else {
                return Paths.get("data", "images", "Player0.png").toFile();
            }
        }
        else {
            if (getInvincibleCountdown().isEffective()) {
                return Paths.get("data", "images", "Player1WithShield.png").toFile();
            } else {
                return Paths.get("data", "images", "Player1.png").toFile();
            }
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }
}
