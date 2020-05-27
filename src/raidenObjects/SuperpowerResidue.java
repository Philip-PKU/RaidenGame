package raidenObjects;

import utils.Faction;

import java.io.File;
import java.nio.file.Paths;

import static world.World.gameStep;

public class SuperpowerResidue extends BaseRaidenObject {
    int stepsAfterBombing;
    static final int maxStepsAfterBombing = 12;
    public SuperpowerResidue(float x, float y, Faction faction) {
        super("Bomb", x, y, 400, 400, faction);
        markAsDead();
    }

    @Override
    public void step() {}

    /**
     * Return the image file.
     * The file name starts with {@code name}, continues with {@code stepsAfterDeath} (0 if the aircraft is alive,
     * 1~maxStepsAfterDeath if the aircraft is dead but still on screen), and ends with suffix ".png".
     * Note: The image files are all stored in "data/images".
     *
     * @return A File object representing current image of this superpower residue.
     * @author 蔡辉宇
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
