package RaidenObjects;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import static World.World.gameStep;
import static World.World.isOutOfWindow;

/**
 * Base class of all flying objects in the game, including planes and interactants.
 * Class attributes include:
 *     - file2image - An object-image mapping for loadImage function
 * Object attributes include:
 *     - coordinates
 *     - states (alive? on screen?)
 *     - born time (protected final)
 *     - object name
 *     - object owner and controller
 *     - object size
 *     - max speed in pixels per step
 * Object methods include:
 *     - Getters (public) and setters (protected) for some of the attributes
 *     - step (public abstract) - Take a step and modify relative attributes
 *     - getImageFile (public abstract) - Get path to current image of the object
 *     - loadImage (protected static) - Load an image to memory. Used in {@code paint} function.
 *     - paint - Paint the object on screen
 *     - hasHit - Judge if two objects has hit each other
 *     - isOutOfWorld - Judge if the object is out of the Raiden world
 */
public abstract class BaseRaidenObject {
    protected String name;
    protected RaidenObjectOwner owner;
    protected RaidenObjectController controller;
    protected int sizeX, sizeY;  // object size
    protected float x, y;  // coordinates
    protected float maxSpeed;  // max speed
    protected final int gameStepWhenBorn;  // game step when the object is constructed
    boolean alive, onScreen;  // states
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
        this.onScreen = true;
        this.controller = controller;
        this.gameStepWhenBorn = gameStep.intValue();
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

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getXAsInt() {
        return (int)x;
    }

    public int getYAsInt() {
        return (int)y;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isOnScreen() {
        return onScreen;
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

    public void markAsDead() {
        this.alive = false;
    }

    public void markAsDeadIfOutOfBound() {
        if (isOutOfWorld(getX(), getY())) {
            markAsDead();
        }
    }

    public void markAsOffScreen() {
        this.onScreen = false;
    }

    public abstract void step();
    public abstract File getImageFile();

    public boolean isOutOfWorld(float x, float y) {
        return isOutOfWindow(x, y) ||
               isOutOfWindow(x + getSizeX(), y + getSizeY());
    }

        public void paint(Graphics g) {
        g.drawImage(loadImage(getImageFile()), getXAsInt(), getYAsInt(), null);
    }

    public boolean hasHit(BaseRaidenObject other) {
        return getX() < other.getX()+other.getSizeX() &&
               getY() < other.getY()+other.getSizeY() &&
               other.getX() < getX()+getSizeX() &&
               other.getY() < getY()+getSizeY();
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
}
