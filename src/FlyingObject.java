/**
 * Basic
 * @
 */
public abstract class FlyingObject {
    int x, y;  // 坐标

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public abstract void step();
}
