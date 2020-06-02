package main.world.pages;

import main.utils.MyButton;
import main.world.World;

import java.awt.*;
import java.nio.file.Paths;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.MAIN;
import static main.utils.PageStatus.MODE_CHOOSE;
import static main.utils.PlayerNumber.ONE;
import static main.utils.PlayerNumber.TWO;

/**
 * Player chose page handler
 *
 * @author 杨芳源
 */
public class PlayerChoosePage implements Page {
    MyButton buttonOne, buttonTwo, buttonBack;

    public void run(World world) {
        buttonOne = new MyButton(150, 320, 180, 80, Paths.get("data", "images", "oneplayer.png"),
                e -> {
                    World.playerNumber = ONE;
                    world.changePageStatus(MODE_CHOOSE);
                });
        buttonTwo = new MyButton(150, 460, 180, 80, Paths.get("data", "images", "twoplayer.png"),
                e -> {
                    World.playerNumber = TWO;
                    world.changePageStatus(MODE_CHOOSE);
                });
        buttonBack = new MyButton(300, 600, 100, 70, Paths.get("data", "images", "previous.png"),
                e -> world.changePageStatus(MAIN));
        
        world.add(buttonOne);
        world.add(buttonTwo);
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
        if (buttonOne != null)
            world.remove(buttonOne);
        if (buttonTwo != null)
            world.remove(buttonTwo);
        if (buttonBack != null)
        	world.remove(buttonBack);
        world.revalidate();
        world.repaint();
    }
}
