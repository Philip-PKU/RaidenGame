package utils.keyAdapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A key adapter that logs key activity and returns current key state.
 * @author 钄¤緣瀹�
 */
public class BaseRaidenKeyAdapter extends KeyAdapter {
    public final int LEFT = 1, RIGHT = 2, UP = 4, DOWN = 8;
    public final int SHOOT = 1;
    public final int BOMB = 1;
    int leftKey, rightKey, upKey, downKey, shootKey, bombChar;
    private int motionState, weaponState, bombState;

    public BaseRaidenKeyAdapter(int leftKey, int rightKey, int upKey, int downKey, int shootKey, int bombChar) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.downKey = downKey;
        this.shootKey = shootKey;
        this.bombChar = bombChar;
    }

    public int getMotionState() {
        return motionState;
    }

    public int getWeaponState() {
        return weaponState;
    }

    public int getAndResetBombState() {
        int bombState = this.bombState;
        this.bombState = 0;
        return bombState;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyCode = e.getKeyChar();
        if (keyCode == bombChar)
            bombState |= BOMB;
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
    }
}
