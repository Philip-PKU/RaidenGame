package main.ui.world.pages;

import main.ui.world.World;
import main.utils.MyButton;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.*;

/**
 * Main page handler
 *
 * @author 鏉ㄨ姵婧�
 */
public class MainPage implements Page {
    JButton buttonStart, buttonRankList, buttonHelp, buttonExit;

    public void run(World world) throws InterruptedException {
        buttonStart = new MyButton(130, 230, 220, 80, Paths.get("data", "images", "start.png"),
                e -> world.changePageStatus(PLAYER_CHOOSE));
        buttonRankList = new MyButton(130, 330, 220, 80, Paths.get("data", "images", "ranklist.png"),
                e -> world.changePageStatus(RANK_LIST_ONE));
        buttonHelp = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "help.png"),
                e -> world.changePageStatus(HELP));
        buttonExit = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "startexit.png"),
                e -> world.changePageStatus(CLOSE));

        world.add(buttonStart);
        world.add(buttonRankList);
        world.add(buttonHelp);
        world.add(buttonExit);
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
