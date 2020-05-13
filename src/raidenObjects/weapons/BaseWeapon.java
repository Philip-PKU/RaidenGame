package raidenObjects.weapons;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static world.World.aircraftList;
import static world.World.interactantList;

public abstract class BaseWeapon extends BaseRaidenObject {
    protected int damage;

    public BaseWeapon(String name, float x, float y, int sizeX, int sizeY,
               RaidenObjectOwner owner, int damage) {
        super(name, x, y, sizeX, sizeY, owner);
        this.damage = damage;
        interactantList.add(this);
    }

    public int getDamage() {
        return damage;
    }

    public void interactWith(BaseAircraft aircraft) {
        if (aircraft.isAlive() && this.isAlive() && this.hasHit(aircraft) &&
                this.getOwner().isEnemyTo(aircraft.getOwner())) {
            aircraft.receiveDamage(getDamage());
            this.markAsDead();
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }

    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
    }


    public void step() {
        if (isAlive()) {
            getMotionController().scheduleSpeed();
            move();
            aircraftList.forEach(this::interactWith);
        }
    }
}
