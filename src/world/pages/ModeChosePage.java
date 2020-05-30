package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.GameMode.*;
import static utils.PageStatus.*;

/**
 * Mode Choice Page handler.
 *
 * @author 鏉ㄨ姵婧�
 */
public class ModeChosePage implements Page {
    static MyButton buttonSurvival, buttonTime, buttonBack;

    public void run(World world) {
        ActionListener listener1 = (e) -> {
            World.pageStatus = GAMING;
            World.gameMode = SURVIVAL;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = GAMING;
            World.gameMode = TIMER;
        };
        ActionListener listener3 = (e) -> {
        	World.pageStatus = PLAYER_CHOSE;
        };
        buttonSurvival = new MyButton(150, 320, 180, 80, Paths.get("data", "images", "survival.png"), listener1);
        buttonTime = new MyButton(150, 460, 180, 80, Paths.get("data", "images", "time.png"), listener2);
        buttonBack = new MyButton(300, 600, 100, 70, Paths.get("data", "images", "previous.png"), listener3);
        world.add(buttonSurvival);
        world.add(buttonTime);
        world.add(buttonBack);
    }

    public void paint(Graphics g) {
        g.drawImage(loadImage(Paths.get("data", "images", "Background.png").toFile()),
                	0,0,null);
        g.drawImage(loadImage(Paths.get("data", "images", "survival.png").toFile()),
        			150, 320, 180, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "time.png").toFile()),
        			150, 460, 180, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
        			300, 600, 100, 70, null);
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