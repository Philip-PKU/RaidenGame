package world.pages;

import utils.MyButton;
import utils.PageStatus;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;
import static utils.PlayerNumber.*;

/**
 * Player chose page handler
 *
 * @author 鏉ㄨ姵婧�
 */
public class PlayerChosePage implements Page {
    MyButton buttonOne, buttonTwo, buttonBack;

    public void run(World world) {
        ActionListener listener1 = (e) -> {
            World.pageStatus = MODE_CHOSE;
            World.playerNumber = ONE;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = MODE_CHOSE;
            World.playerNumber = TWO;
        };
        ActionListener listener3 = (e) -> {
        	World.pageStatus = MAIN;
        };
        buttonOne = new MyButton(150, 320, 180, 80, Paths.get("data", "images", "oneplayer.png"), listener1);
        buttonTwo = new MyButton(150, 460, 180, 80, Paths.get("data", "images", "twoplayer.png"), listener2);
        buttonBack = new MyButton(300, 600, 100, 70, Paths.get("data", "images", "previous.png"), listener3);
        
        world.add(buttonOne);
        world.add(buttonTwo);
        world.add(buttonBack);
    }

    public void paint(Graphics g) {
        g.drawImage(loadImage(Paths.get("data", "images", "Background.png").toFile()),
                	0,0,null);
        g.drawImage(loadImage(Paths.get("data", "images", "oneplayer.png").toFile()),
        			150, 320, 180, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "twoplayer.png").toFile()),
        			150, 460, 180, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
        			300, 600, 100, 70, null);
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
