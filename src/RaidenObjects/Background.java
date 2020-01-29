package RaidenObjects;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class Background extends BaseRaidenObject {
    private float y2;

    public Background() {
        super("Background4", 0, 0, 640, 1260, 1,
                RaidenObjectOwner.NEUTRAL, RaidenObjectController.AI);
        y2 = -sizeY;
    }

    private float getY2() {
        return y2;
    }

    private int getY2AsInt() {
        return (int)y2;
    }

    private void setY2(float y2) {
        this.y2 = y2;
    }

    public void step() {
        setY(getY() + getMaxSpeed());
        setY2(getY2() + getMaxSpeed());
        if (getY() >= getSizeY())
            setY(getY() - 2 * getSizeY());
        if (getY2() >= getSizeY())
            setY2(getY2() - 2 * getSizeY());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(loadImage(getImageFile()), getXAsInt(), getY2AsInt(), null);
    }

    public File getImageFile() {
        return Paths.get("data", "images", name + ".png").toFile();
    }
}
