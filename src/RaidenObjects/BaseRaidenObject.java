package RaidenObjects;

import RaidenObjects.Aircrafts.ShootingAircrafts.PlayerAircraft;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import static World.World.*;
import static java.lang.Math.sqrt;

/**
 * Base class of all flying objects in the game, including planes and interactants.
 * Class attributes include:
 *     - file2image - An object-image mapping for loadImage function
 * Object attributes include:
 *     - coordinates of object center
 *     - states (alive? on screen?)
 *     - born time (protected)
 *     - object name
 *     - object owner and controller
 *     - object size
 *     - max speed and current speed (in pixels per step)
 * Object methods include:
 *     - Getters (public) and setters (protected) for some of the attributes
 *     - step (public abstract) - Take a step and modify relative attributes
 *     - getImageFile (public abstract) - Get path to current image of the object
 *     - loadImage (protected static) - Load an image to memory. Used in {@code paint} function.
 *     - paint - Paint the object on screen
 *     - hasHit - Judge if two objects has hit each other
 *     - isOutOfWorld - Judge if the object is out of the Raiden world
 *     - getRandomPlayer (protected) - Get a random player. Used in enemy air crafts / weapons.
 *     - getClosestPlayer (protected) - Get the closest player. Used in enemy air crafts / weapons.
 *     - move (protected) - Move at current speed. Used in {@code step} function.
 */
public abstract class BaseRaidenObject implements Flyable{
    protected String name;
    protected RaidenObjectOwner owner;
    protected RaidenObjectController controller;
    protected int sizeX, sizeY;  // object size
    protected float x, y;  // coordinates of object center
    protected float maxSpeed;  // max speed
    protected float speedX, speedY;  // current speed
    protected int gameStepWhenReady;  // game step when the object is in position
    boolean alive, offScreen;  // states
    static TreeMap<File, BufferedImage> file2image = new TreeMap<>();

    protected BaseRaidenObject(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
                               RaidenObjectOwner owner, RaidenObjectController controller) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxSpeed = maxSpeed;
        this.owner = owner;
        this.alive = true;
        this.offScreen = false;
        this.controller = controller;
        this.gameStepWhenReady = gameStep.intValue();
    }

    public String getName() {
        return name;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getHitSizeX() {
        return sizeX;
    }

    public int getHitSizeY() {
        return sizeY;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isOffScreen() {
        return offScreen;
    }

    public RaidenObjectOwner getOwner() {
        return owner;
    }

    public RaidenObjectController getController() {
        return controller;
    }

    protected void setX(float x) {
        this.x = x;
    }

    protected void setY(float y) {
        this.y = y;
    }

    protected void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    protected void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void markAsDead() {
        this.alive = false;
    }

    public void markAsDeadIfOutOfBound() {
        if (isOutOfWorld(getX(), getY())) {
            markAsDead();
        }
    }

    public void markAsOffScreen() {
        this.offScreen = true;
    }

    public abstract void step();
    public abstract File getImageFile();

    public float getMinX() {
        return getMinX(getX());
    }

    public float getMinY() {
        return getMinY(getY());
    }

    public float getMaxX() {
        return getMaxX(getX());
    }

    public float getMaxY() {
        return getMaxY(getY());
    }

    public float getMinX(float x) {
        return x - getSizeX() / 2.0f;
    }

    public float getMinY(float y) {
        return y - getSizeY() / 2.0f;
    }

    public float getMaxX(float x) {
        return x + getSizeX() / 2.0f;
    }

    public float getMaxY(float y) {
        return y + getSizeY() / 2.0f;
    }

    public float getHitTopLeftX() {
        return getX() - getHitSizeX() / 2.0f;
    }

    public float getHitTopLeftY() {
        return getY() - getHitSizeY() / 2.0f;
    }

    public float getHitBottomRightX() {
        return getX() + getHitSizeX() / 2.0f;
    }

    public float getHitBottomRightY() {
        return getY() + getHitSizeY() / 2.0f;
    }

    public boolean isOutOfWorld(float x, float y) {
        return isOutOfWindow(x, getMaxY(y));
    }

    public void paint(Graphics g) {
        g.drawImage(loadImage(getImageFile()), (int) getMinX(), (int) getMinY(), null);
    }

    public boolean hasHit(Flyable other) {
        return getHitTopLeftX() < other.getHitBottomRightX() &&
               getHitTopLeftY() < other.getHitBottomRightY() &&
               other.getHitTopLeftX() < getHitBottomRightX() &&
               other.getHitTopLeftY() < getHitBottomRightY();
    }

    protected static BufferedImage loadImage(File file) {
        if (file == null)
            return null;
        if (file2image.containsKey(file))
            return file2image.get(file);
        else {
            BufferedImage image;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            file2image.put(file, image);
            return image;
        }
    }

    protected PlayerAircraft getRandomPlayer() {
        if (player1 == null) {
            if (player2 == null) {
                throw new RuntimeException("No player exists.");
            }
            else {
                return player2;
            }
        }
        else {
            if (player2 != null) {
                boolean returnPlayer1 = rand.nextBoolean();
                return returnPlayer1 ? player1 : player2;
            }
            else {
                return player1;
            }
        }
    }

    protected PlayerAircraft getClosestPlayer() {
        float dist1 = Float.POSITIVE_INFINITY, dist2 = Float.POSITIVE_INFINITY;
        if (player1 != null) {
            float dx = player1.getX() - getX(), dy = player1.getY() - getY();
            dist1 = (float) sqrt(dx*dx + dy*dy);
        }
        if (player2 != null) {
            float dx = player2.getX() - getX(), dy = player2.getY() - getY();
            dist2 = (float) sqrt(dx*dx + dy*dy);
        }
        return dist1 < dist2 ? player1 : player2;
    }

    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
    }
}
