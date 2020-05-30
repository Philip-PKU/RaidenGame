package world.pages;

import utils.MyButton;
import utils.VolumeController;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

/**
 * Main page handler
 *
 * @author 鏉ㄨ姵婧�
 */
public class MainPage implements Page {
    MyButton buttonStart, buttonRankList, buttonHelp, buttonExit;

    public void run(World world) {
        world.playBackGroundMusic(Paths.get("data", "bgm", "15. War Council.mp3").toString());
        // The volume control functions in class {@code Player} does not appear to work. DO NOT USE IT.
        // Instead, use {@Code VolumeController} in package utils.
        VolumeController.setVolume(0.08f);

        ActionListener listener1 = (e) -> {
            World.pageStatus = PLAYER_CHOSE;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = RANK_LIST_ONE;
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
        g.drawImage(loadImage(Paths.get("data", "images", "Background.png").toFile()),
                0,0,null);
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
