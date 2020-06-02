package main.world.pages;

import main.utils.MyButton;
import main.world.World;

import java.awt.*;
import java.nio.file.Paths;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.GameMode.SURVIVAL;
import static main.utils.GameMode.TIMER;
import static main.utils.PageStatus.GAMING;
import static main.utils.PageStatus.PLAYER_CHOOSE;

/**
 * Mode Choice Page handler.
 *
 * @author 杨芳源
 */
public class ModeChoosePage implements Page {
    static MyButton buttonSurvival, buttonTime, buttonBack;

    public void run(World world) {
        buttonSurvival = new MyButton(150, 320, 180, 80, Paths.get("data", "images", "survival.png"),
                e -> {
                    World.gameMode = SURVIVAL;
                    world.changePageStatus(GAMING);
                });
        buttonTime = new MyButton(150, 460, 180, 80, Paths.get("data", "images", "time.png"),
                e -> {
                    World.gameMode = TIMER;
                    world.changePageStatus(GAMING);
                });
        buttonBack = new MyButton(300, 600, 100, 70, Paths.get("data", "images", "previous.png"),
                e -> world.changePageStatus(PLAYER_CHOOSE));

        world.add(buttonSurvival);
        world.add(buttonTime);
        world.add(buttonBack);
        world.repaint();
    }

    public void paintComponent(Graphics g, World world) {
        g.drawImage(loadImage(Paths.get("data", "images", "Background.png").toFile()),
                	0,0,null);
        g.drawImage(loadImage(Paths.get("data", "images", "title.png").toFile()).getScaledInstance(400, 122, Image.SCALE_DEFAULT),
                40, 50, null);
        world.paintComponents(g);
    }

    public void clean(World world) {
        if (buttonSurvival != null)
            world.remove(buttonSurvival);
        if (buttonTime != null)
            world.remove(buttonTime);
        if (buttonBack != null)
        	world.remove(buttonBack);
        world.revalidate();
        world.repaint();
    }
}