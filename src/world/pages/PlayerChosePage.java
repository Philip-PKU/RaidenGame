package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.MODE_CHOSE;
import static utils.PlayerNumber.ONE;
import static utils.PlayerNumber.TWO;
import static world.World.background;

/**
 * Player chose page handler
 *
 * @author 杨芳源
 */
public class PlayerChosePage implements Page {
    MyButton buttonOne, buttonTwo;

    public void run(World world) {
        ActionListener listener1 = (e) -> {
            World.pageStatus = MODE_CHOSE;
            World.playerNumber = ONE;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = MODE_CHOSE;
            World.playerNumber = TWO;
        };
        buttonOne = new MyButton(120, 180, 240, 90, Paths.get("data", "images", "oneplayer.png"), listener1);
        buttonTwo = new MyButton(120, 360, 240, 90, Paths.get("data", "images", "twoplayer.png"), listener2);
        world.add(buttonOne, false);
        world.add(buttonTwo, false);
    }

    public void paint(Graphics g) {
        background.paint(g);
        g.drawImage(loadImage(Paths.get("data", "images", "oneplayer.png").toFile()),
                120, 180, 240, 90, null);
        g.drawImage(loadImage(Paths.get("data", "images", "twoplayer.png").toFile()),
                120, 360, 240, 90, null);
    }

    public void clean(World world) {
        if (buttonOne != null) {
            world.remove(buttonOne);
        }
        if (buttonTwo != null) {
            world.remove(buttonTwo);
        }
        world.revalidate();
        world.repaint();
    }
}
