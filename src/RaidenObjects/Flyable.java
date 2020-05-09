package RaidenObjects;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.awt.*;
import java.io.File;

public interface Flyable {
    String getName();
    int getSizeX();
    int getSizeY();
    int getHitSizeX();
    int getHitSizeY();
    float getMaxSpeed();
    float getSpeedX();
    float getSpeedY();
    float getX();
    float getY();
    boolean isAlive();
    boolean isOffScreen();
    RaidenObjectOwner getOwner();
    RaidenObjectController getController();
    void markAsDead();
    void markAsDeadIfOutOfBound();
    void markAsOffScreen();
    void step();
    File getImageFile();
    float getMinX();
    float getMinY();
    float getMaxX();
    float getMaxY() ;
    float getMinX(float x);
    float getMinY(float y);
    float getMaxX(float x);
    float getMaxY(float y);
    float getHitTopLeftX();
    float getHitTopLeftY();
    float getHitBottomRightX();
    float getHitBottomRightY();
    boolean isOutOfWorld(float x, float y);
    void paint(Graphics g);
    boolean hasHit(Flyable other);
}
