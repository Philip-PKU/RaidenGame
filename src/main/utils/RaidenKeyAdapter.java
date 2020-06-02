package main.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A key adapter that logs key activity and returns current key state.
 *
 * @author 蔡辉宇
 */
public class RaidenKeyAdapter extends KeyAdapter {
    public final int LEFT = 1, RIGHT = 2, UP = 4, DOWN = 8;
    public final int SHOOT = 1;
    public final int BOMB = 1;
    int leftKey, rightKey, upKey, downKey, shootKey, bombKey;
    private int motionState, weaponState, bombState;

    public RaidenKeyAdapter(int leftKey, int rightKey, int upKey, int downKey, int shootKey, int bombKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.downKey = downKey;
        this.shootKey = shootKey;
        this.bombKey = bombKey;
    }

    public int getMotionState() {
        return motionState;
    }

    public int getWeaponState() {
        return weaponState;
    }

    public int getBombState() {
        return bombState;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == leftKey)
            motionState |= LEFT;
        else if (keyCode == rightKey)
            motionState |= RIGHT;
        else if (keyCode == upKey)
            motionState |= UP;
        else if (keyCode == downKey)
            motionState |= DOWN;
        else if (keyCode == shootKey)
            weaponState |= SHOOT;
        else if (keyCode == bombKey)
            bombState |= BOMB;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == leftKey)
            motionState &= ~LEFT;
        else if (keyCode == rightKey)
            motionState &= ~RIGHT;
        else if (keyCode == upKey)
            motionState &= ~UP;
        else if (keyCode == downKey)
            motionState &= ~DOWN;
        else if (keyCode == shootKey)
            weaponState &= ~SHOOT;
        else if (keyCode == bombKey)
            bombState &= ~BOMB;
    }
}
