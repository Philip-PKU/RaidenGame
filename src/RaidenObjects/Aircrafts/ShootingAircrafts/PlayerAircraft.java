package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.HeroBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static World.World.*;

public class PlayerAircraft extends BaseShootingAircraft {
    public PlayerAircraft(float x, float y, RaidenObjectOwner owner, RaidenObjectController controller) {
        super("Player0", x, y, 50, 50, 9,
        owner, controller, 100, 0, 100, 2);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
    }

    @Override
    public File getImageFile() {
        return Paths.get("data", "images", "Player0.png").toFile();
    }

    public void shootWeapon() {
        if ((gameStep.intValue() - gameStepWhenBorn) % getWeaponCoolDown() == 0) {
            new HeroBullet(getX() + getSizeX() / 2.0f, getY(), getOwner(), getController(), 0.0f);
            new HeroBullet(getX() + getSizeX() / 2.0f, getY(), getOwner(), getController(), 6.0f);
            new HeroBullet(getX() + getSizeX() / 2.0f, getY(), getOwner(), getController(), -6.0f);
        }
    }

    public void move() {
        if (controller.isKeyboard()) {
            int arrowState = keyAdapter.getArrowState();
            if ((arrowState & keyAdapter.left) != 0 && !isOutOfWorld(getX() - getMaxSpeed(), getY()))
                setX(getX() - getMaxSpeed());
            if ((arrowState & keyAdapter.right) != 0 && !isOutOfWorld(getX() + getMaxSpeed(), getY()))
                setX(getX() + getMaxSpeed());
            if ((arrowState & keyAdapter.up) != 0 && !isOutOfWorld(getX(), getY() - getMaxSpeed()))
                setY(getY() - getMaxSpeed());
            if ((arrowState & keyAdapter.down) != 0 && !isOutOfWorld(getX(), getY() + getMaxSpeed()))
                setY(getY() + getMaxSpeed());
        }
        else {
            if ((gameStep.intValue() - gameStepWhenBorn) % 10 < 5)
                setX(getX() - getMaxSpeed());
            else
                setX(getX() + getMaxSpeed());
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
