package FlyingObjects;

import org.apache.commons.lang3.mutable.MutableInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Base class of all flying objects in the game, including planes, bullets and rewards.
 * Class attributes include:
 *     1. (protected static) path2image - An object-image mapping
 * Object attributes include:
 *     1. size
 *     2. coordinates
 *     3. states (alive?)
 *     4. max x/y speed
 *     5. (protected) reference to global game step
 * Object methods include:
 *     1. Getters (public) and setters (protected) for some of the attributes
 *     3. (public abstract) step - Take a step and modify relative attributes
 *     4. (public abstract) getImagePath - Get path to current image of the object
 *     5. (protected static) loadImage - Load an image to memory. Used in {@code paint} function.
 *     6. paint - Paint the object on screen
 *     7. hasHit - Judge if two objects has hit each other
 */
public abstract class BaseFlyingObject {
    int sizeX, sizeY;  // size
    int x, y;  // coordinates
    boolean alive;  // states
    float maxSpeedX, maxSpeedY;  // max speed
    protected MutableInt gameStep;  // reference to global gamestep
    static TreeMap<String, BufferedImage> path2image = new TreeMap<>();

    protected BaseFlyingObject(int sizeX, int sizeY, int x, int y,
                               float maxSpeedX, float maxSpeedY, MutableInt gameStep) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.y = y;
        this.alive = true;
        this.maxSpeedX = maxSpeedX;
        this.maxSpeedY = maxSpeedY;
        this.gameStep = gameStep;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getMaxSpeedX() {
        return maxSpeedX;
    }

    public float getMaxSpeedY() {
        return maxSpeedY;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public void markAsDead() {
        this.alive = false;
    }

    protected void setMaxSpeedX(float maxSpeedX) {
        this.maxSpeedX = maxSpeedX;
    }

    protected void setMaxSpeedY(float maxSpeedY) {
        this.maxSpeedY = maxSpeedY;
    }

    public abstract void step();
    public abstract String getImagePath();

    public void paint(Graphics g) {
        g.drawImage(loadImage(getImagePath()), this.x, this.y, null);
    }

    public boolean hasHit(BaseFlyingObject other) {
        return x < other.x+other.sizeX && y < other.y+other.sizeY && other.x < x+ sizeX && other.y < y+ sizeY;
    }

    private static BufferedImage loadImage(String path) {
        if (path == null)
            return null;
        if (path2image.containsKey(path))
            return path2image.get(path);
        else {
            BufferedImage image;
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            path2image.put(path, image);
            return image;
        }
    }
}
