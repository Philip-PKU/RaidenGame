package raidenObjects;

import motionControllers.MotionController;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Bivector;
import utils.Faction;
import utils.InitLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static world.World.*;

/**
 * Base class of all flying objects in the game, including planes and interactants.
 *
 * @author 蔡辉宇
 */
public abstract class BaseRaidenObject {
    protected String name;
    protected Faction faction;
    protected MotionController motionController;
    protected int imgSizeX, imgSizeY;  // object size
    protected float x, y;  // coordinates of object center
    protected float speedX, speedY;  // current speed
    protected float rotation;  // rotation angle (in radians)
    boolean alive, invisible;  // states
    static TreeMap<File, BufferedImage> file2image = new TreeMap<>();

    /**
     * Constructor.
     *
     * @param name Name of the object (used to fetch image of the object).
     * @param imgSizeX Image width.
     * @param imgSizeY Image height.
     * @param faction Faction of the object.
     */
    protected BaseRaidenObject(String name, int imgSizeX, int imgSizeY, Faction faction) {
        this.name = name;
        this.imgSizeX = imgSizeX;
        this.imgSizeY = imgSizeY;
        this.faction = faction;
        this.alive = true;
        this.invisible = false;
    }

    /**
     * Initialize X location from given hint.
     *
     * @param initLocation A hint for initializing X location.
     */
    public void initXFromLocation(InitLocation initLocation) {
        int halfImgSizeX = imgSizeX / 2;
        if (initLocation.equals(InitLocation.LOC_LEFT)) {
            setX(rand.nextInt(windowWidth / 2 - halfImgSizeX) + halfImgSizeX);
        } else if (initLocation.equals(InitLocation.LOC_RANDOM)) {
            setX(rand.nextInt(windowWidth - imgSizeX) + halfImgSizeX);
        } else if (initLocation.equals(InitLocation.LOC_RIGHT)) {
            setX(rand.nextInt(windowWidth / 2 - halfImgSizeX) + windowWidth);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns X image size of current object. Img size is the size of the image of the object.
     *
     * @return imgSizeX
     */
    public int getImgSizeX() {
        return imgSizeX;
    }

    public int getImgSizeY() {
        return imgSizeY;
    }

    public void setImgSizeX(int imgSizeX) {
        this.imgSizeX = imgSizeX;
    }

    public void setImgSizeY(int imgSizeY) {
        this.imgSizeY = imgSizeY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns X hit size of current object. Hit size is the size of the rectangle centered at (x, y)
     * that is used to judge if two objects (e.g. plane/plane and plane/weapon) have hit each other.
     * In default, this is just {@code imgSizeX}, but subclasses can override this behavior.
     *
     * @return hitSizeX, which defaults to imgSizeX.
     */
    public int getHitSizeX() {
        return imgSizeX;
    }

    public int getHitSizeY() {
        return imgSizeY;
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInvisibleOrOutOfWorld() {
        if (isOutOfWorld(getX(), getMinY())) {
            markAsDead();
            return true;
        }
        return invisible;
    }

    public Faction getFaction() {
        return faction;
    }

    public MotionController getMotionController() {
        return motionController;
    }

    /**
     * Set MotionController for the current object, and set parent of motionController to the current object.
     *
     * @param motionController A motionController object that will control the movement of the current object.
     */
    public void registerMotionController(MotionController motionController) {
        this.motionController = motionController;
        motionController.registerParent(this);
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

    public void becomeInvisible() {
        this.invisible = true;
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
        return x - getImgSizeX() / 2.0f;
    }

    public float getMinY(float y) {
        return y - getImgSizeY() / 2.0f;
    }

    public float getMaxX(float x) {
        return x + getImgSizeX() / 2.0f;
    }

    public float getMaxY(float y) {
        return y + getImgSizeY() / 2.0f;
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
        return x < 0 || x >= windowWidth || y >= windowHeight;
    }

    /**
     * Paints the object.
     * @param g A {@link Graphics} object.
     */
    public void paint(Graphics g) {
        if (rotation != 0) {
            Graphics2D g2d = (Graphics2D) g;
            Image image = loadImage(getImageFile());
            if (image != null) {
                AffineTransform backup = g2d.getTransform();
                AffineTransform a = AffineTransform.getRotateInstance(rotation, getX(), getY());
                //Set our Graphics2D object to the transform
                g2d.setTransform(a);
                //Draw our image like normal
                g2d.drawImage(image, (int) getMinX(), (int) getMinY(), null);
                g2d.setTransform(backup);
            }
        } else
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

    public static BufferedImage loadImage(File file) {
        if (file == null)
            return null;
        if (file2image.containsKey(file))
            return file2image.get(file);
        else {
            BufferedImage image;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                System.out.println(file.toString());
                e.printStackTrace();
                throw new RuntimeException();
            }
            file2image.put(file, image);
            return image;
        }
    }

    /**
     * Return a random player.
     *
     * @return A random PlayerAircraft
     */
    public PlayerAircraft getRandomPlayer() {
        if (player1 == null) {
            if (player2 == null) {
                throw new RuntimeException("No player exists.");
            } else {
                return player2;
            }
        } else {
            if (player2 != null) {
                boolean returnPlayer1 = rand.nextBoolean();
                return returnPlayer1 ? player1 : player2;
            } else {
                return player1;
            }
        }
    }

    /**
     * Return the closest player.
     *
     * @return The closest PlayerAircraft
     */
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

    /**
     * Move according to the scheduled speed, and mark as dead if the current object is out of bound after moving.
     */
    protected void move() {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
    }

    /**
     * Rotate this object to face the given target aircraft.
     *
     * @param target The target to face by this object.
     */
    public void rotateToFaceTargetAircraft(BaseAircraft target) {
        if (target == null)
            return;

        //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
        //is the angle to rotate the image. If you want to rotate around the center of an image,
        //use the image's center x and y coordinates for rx and ry.
        float dx = target.getX() - getX(), dy = target.getY() - getY(), theta;
        if (dy == 0) {
            theta = dx > 0 ? -(float) PI / 2f : (float) PI / 2f;
        } else {
            theta = (float) -atan(dx / dy);
            if (dy < 0)
                theta += (float) PI;
        }
        setRotation(theta);
    }
}
