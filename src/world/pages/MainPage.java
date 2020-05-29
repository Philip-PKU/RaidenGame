package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;
import static world.World.background;

/**
 * Main page handler
 *
 * @author 杨芳源
 */
public class MainPage implements Page {
    MyButton buttonStart, buttonRankList, buttonHelp, buttonExit;

    public void run(World world) {
        ActionListener listener1 = (e) -> {
            World.pageStatus = PLAYER_CHOSE;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = RANK_LIST;
        };
        ActionListener listener3 = (e) -> {
            World.pageStatus = HELP;
        };
        ActionListener listener4 = (e) -> {
            World.pageStatus = CLOSE;
        };
        buttonStart = new MyButton(130, 230, 220, 80, Paths.get("data", "images", "start.png"), listener1);
        buttonRankList = new MyButton(130, 330, 220, 80, Paths.get("data", "images", "ranklist.png"), listener2);
        buttonHelp = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "help.png"), listener3);
        buttonExit = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "startexit.png"), listener4);
        world.add(buttonStart);
        world.add(buttonRankList);
        world.add(buttonHelp);
        world.add(buttonExit);
    }

    public void paint(Graphics g) {
        background.paint(g);
        g.drawImage(loadImage(Paths.get("data", "images", "title.png").toFile()),
                30, 30, 420, 150, null);
        g.drawImage(loadImage(Paths.get("data", "images", "start.png").toFile()),
                130, 230, 220, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "ranklist.png").toFile()),
                130, 330, 220, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "help.png").toFile()),
                130, 430, 220, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "startexit.png").toFile()),
                130, 530, 220, 80, null);
    }

    public void clean(World world) {
        if (buttonExit != null)
            world.remove(buttonExit);
        if (buttonHelp != null)
            world.remove(buttonHelp);
        if (buttonRankList != null)
            world.remove(buttonRankList);
        if (buttonStart != null)
            world.remove(buttonStart);
        world.revalidate();
        world.repaint();
    }

}
