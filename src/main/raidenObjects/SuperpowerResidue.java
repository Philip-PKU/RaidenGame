package main.raidenObjects;

import main.utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static main.World.gameStep;

/**
 * An object whose sole purpose is to display the residue of superpower firing.
 *
 * @author 蔡辉宇
 */
public class SuperpowerResidue extends BaseRaidenObject {
    int stepsAfterBombing;
    static final int maxStepsAfterBombing = 12;

    /**
     * Constructor.
     *
     * @param x       Initial X coordinate.
     * @param y       Initial Y coordinate.
     * @param faction Factor of this object.
     */
    public SuperpowerResidue(float x, float y, Faction faction) {
        super("Bomb", 400, 400, faction);
        setX(x);
        setY(y);
        markAsDead();
    }

    @Override
    public void step() {
    }

    /**
     * Return the image file.
     * The file name starts with {@code name}, continues with {@code stepsAfterDeath} (0 if the aircraft is alive,
     * 1~maxStepsAfterDeath if the aircraft is dead but still on screen), and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     *
     * @return A File object representing current image of this superpower residue.
     */
    public File getImageFile() {
        if (stepsAfterBombing <= maxStepsAfterBombing) {
            String filename = getName() + stepsAfterBombing;
            // Trick: slow down the visual effect so that aircrafts will not vanish too quickly
            if (gameStep.intValue() % 2 == 0) {
                ++stepsAfterBombing;
            }
            return Paths.get("data", "images", filename + ".png").toFile();
        } else {
            becomeInvisible();
            return null;
        }
    }
}
