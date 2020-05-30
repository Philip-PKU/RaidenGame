package world.pages;

import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

/**
 * @author 杨芳源
 *
 */
public class RankListOnePage implements Page {
	MyButton buttonBack, buttonTwoPlayer;
	
	public void run(World world) {
		ActionListener listener1 = (e) -> {
        	World.pageStatus = MAIN;
        };
        ActionListener listener2 = (e) -> {
        	World.pageStatus = RANK_LIST_TWO;
        };
        buttonBack = new MyButton(10, 640, 110, 80, Paths.get("data", "images", "previous.png"), listener1);
        buttonTwoPlayer = new MyButton(315, 75, 100, 30, null, listener2);
        world.add(buttonBack);
        world.add(buttonTwoPlayer);
	}
	
	public void paint(Graphics g) {
		g.drawImage(loadImage(Paths.get("data", "images", "ranklist1.png").toFile()),
					0,0,null);
		g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
					10, 640, 110, 80, null);
	}

	public void clean(World world) {
		if (buttonBack != null)
        	world.remove(buttonBack);
		if (buttonTwoPlayer != null)
			world.remove(buttonTwoPlayer);
		world.revalidate();
		world.repaint();
	}
	
}
