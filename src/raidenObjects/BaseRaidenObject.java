package raidenObjects;

import motionControllers.MotionController;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Bivector;
import utils.RaidenObjectOwner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import static world.World.*;

/**
 * Base class of all flying objects in the game, including planes and interactants.
 */
public abstract class BaseRaidenObject{
    protected String name;
    protected RaidenObjectOwner owner;
    protected MotionController motionController;
    protected int sizeX, sizeY;  // object size
    protected float x, y;  // coordinates of object center
    protected float speedX, speedY;  // current speed
    protected int gameStepWhenReady;  // game step when the object is in position
    boolean alive, offScreen;  // states
    static TreeMap<File, BufferedImage> file2image = new TreeMap<>();

    protected BaseRaidenObject(String name, float x, float y, int sizeX, int sizeY,
                               RaidenObjectOwner owner) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.owner = owner;
        this.alive = true;
        this.offScreen = false;
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

    public MotionController getMotionController() {
        return motionController;
    }

    public void registerMotionController(MotionController motionController){
        this.motionController = motionController;
        motionController.registerParent(this);
    }

    protected void setX(float x) {
        this.x = x;
    }

    protected void setY(float y) {
        this.y = y;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
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

    public void getOffScreen() {
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

    public boolean hasHit(BaseRaidenObject other) {
        return getHitTopLeftX() < other.getHitBottomRightX() &&
               getHitTopLeftY() < other.getHitBottomRightY() &&
               other.getHitTopLeftX() < getHitBottomRightX() &&
               other.getHitTopLeftY() < getHitBottomRightY();
    }

    public float distTo(BaseRaidenObject other) {
        return new Bivector(getX() - other.getX(), getY() - other.getY()).getNorm();
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

    public PlayerAircraft getRandomPlayer() {
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

    public PlayerAircraft getClosestPlayer() {
        float dist1 = Float.POSITIVE_INFINITY, dist2 = Float.POSITIVE_INFINITY;
        if (player1 != null) {
            float dx = player1.getX() - getX(), dy = player1.getY() - getY();
            dist1 = new Bivector(dx, dy).getNorm();
        }
        if (player2 != null) {
            float dx = player2.getX() - getX(), dy = player2.getY() - getY();
            dist2 = new Bivector(dx, dy).getNorm();
        }
        return dist1 < dist2 ? player1 : player2;
    }

    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        markAsDeadIfOutOfBound();
    }
}
