package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.GameMode.SURVIVAL;
import static utils.GameMode.TIMER;
import static utils.PageStatus.GAMING;
import static world.World.background;

/**
 * Mode Choice Page handler.
 *
 * @author 杨芳源
 */
public class ModeChosePage implements Page {
    static MyButton buttonSurvival, buttonTime;

    public void run(World world) {
        ActionListener listener1 = (e) -> {
            World.pageStatus = GAMING;
            World.gameMode = SURVIVAL;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = GAMING;
            World.gameMode = TIMER;
        };
        buttonSurvival = new MyButton(120, 180, 240, 90, Paths.get("data", "images", "survival.png"), listener1);
        buttonTime = new MyButton(120, 360, 240, 90, Paths.get("data", "images", "time.png"), listener2);
        world.add(buttonSurvival);
        world.add(buttonTime);
    }

    public void paint(Graphics g) {
        background.paint(g);
        g.drawImage(loadImage(Paths.get("data", "images", "survival.png").toFile()),
                120, 180, 240, 90, null);
        g.drawImage(loadImage(Paths.get("data", "images", "time.png").toFile()),
                120, 360, 240, 90, null);
    }

    public void clean(World world) {
        if (buttonSurvival != null)
            world.remove(buttonSurvival);
        if (buttonTime != null)
            world.remove(buttonTime);
        world.revalidate();
        world.repaint();
    }
}