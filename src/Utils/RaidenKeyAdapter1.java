package Utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class RaidenKeyAdapter1 extends KeyAdapter {
    public final int left = 1, right = 2, up = 4, down = 8;
    private int arrowState;

    public int getArrowState() {
        return arrowState;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_LEFT:
                arrowState |= left;
                break;
            case VK_RIGHT:
                arrowState |= right;
                break;
            case VK_UP:
                arrowState |= up;
                break;
            case VK_DOWN:
                arrowState |= down;
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_LEFT:
                arrowState &= ~left;
                break;
            case VK_RIGHT:
                arrowState &= ~right;
                break;
            case VK_UP:
                arrowState &= ~up;
                break;
            case VK_DOWN:
                arrowState &= ~down;
                break;
            default:
                break;
        }
    }
}
