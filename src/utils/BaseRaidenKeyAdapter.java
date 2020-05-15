package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A key adapter that logs key activity and returns current key state.
 * @author 蔡辉宇
 */
public class BaseRaidenKeyAdapter extends KeyAdapter {
    public final int left = 1, right = 2, up = 4, down = 8;
    int left_key, right_key, up_key, down_key;
    private int arrowState;

    public BaseRaidenKeyAdapter(int left_key, int right_key, int up_key, int down_key) {
        this.left_key = left_key;
        this.right_key = right_key;
        this.up_key = up_key;
        this.down_key = down_key;
    }

    public int getArrowState() {
        return arrowState;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == left_key)
            arrowState |= left;
        else if (keyCode == right_key)
            arrowState |= right;
        else if (keyCode == up_key)
            arrowState |= up;
        else if (keyCode == down_key)
            arrowState |= down;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == left_key)
            arrowState &= ~left;
        else if (keyCode == right_key)
            arrowState &= ~right;
        else if (keyCode == up_key)
            arrowState &= ~up;
        else if (keyCode == down_key)
            arrowState &= ~down;
    }
}
