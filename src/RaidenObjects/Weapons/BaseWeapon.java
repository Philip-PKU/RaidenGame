package RaidenObjects.Weapons;

import RaidenObjects.Aircrafts.BaseAircraft;
import RaidenObjects.BaseRaidenObject;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static World.World.aircraftList;
import static World.World.interactantList;
import static java.lang.Math.*;

public abstract class BaseWeapon extends BaseRaidenObject {
    protected int damage;

    BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
               RaidenObjectOwner owner, RaidenObjectController controller,
               int damage) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller);
        this.damage = damage;
        speedX = 0;
        speedY = maxSpeed;
        interactantList.add(this);
    }

    BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
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

    BaseWeapon(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
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

    public File getImageFile() {
        return Paths.get("data", "images", getName() + ".png").toFile();
    }

    protected void initSpeed() {}

    public void step() {
        initSpeed();
        move();
        markAsDeadIfOutOfBound();
        aircraftList.forEach(this::interactWith);
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        markAsOffScreen();
    }
}
