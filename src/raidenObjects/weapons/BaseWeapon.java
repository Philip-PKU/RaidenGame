package raidenObjects.weapons;

import raidenObjects.Aircrafts.BaseAircraft;
import raidenObjects.BaseRaidenObject;
import utils.RaidenObjectController;
import utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static java.lang.Math.*;
import static world.World.aircraftList;
import static world.World.interactantList;

public abstract class BaseWeapon extends BaseRaidenObject {
    protected int damage;

    public BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
               RaidenObjectOwner owner, RaidenObjectController controller,
               int damage) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller);
        this.damage = damage;
        speedX = 0;
        speedY = maxSpeed;
        interactantList.add(this);
    }

    public BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
               RaidenObjectOwner owner, RaidenObjectController controller,
               int damage, float targetX, float targetY) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller);
        this.damage = damage;
        float targetDX = targetX - x;
        float targetDY = targetY - y;
        float targetDistance = (float) sqrt(targetDX * targetDX + targetDY * targetDY);
        speedX = targetDX / targetDistance * maxSpeed;
        speedY = targetDY / targetDistance * maxSpeed;
        interactantList.add(this);
    }

    public BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
               RaidenObjectOwner owner, RaidenObjectController controller,
               int damage, float theta) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller);
        this.damage = damage;
        float thetaInRad = (float) toRadians(theta);
        speedX = (float) sin(thetaInRad) * maxSpeed;
        speedY = (float) cos(thetaInRad) * maxSpeed;
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

    protected void updateSpeed() {}

    public void step() {
        if (isAlive()) {
            updateSpeed();
            move();
            aircraftList.forEach(this::interactWith);
        }
    }
}
