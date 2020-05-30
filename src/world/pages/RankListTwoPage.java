package world.pages;

import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

/**
 * @author �Դ
 *
 */
public class RankListTwoPage implements Page {
	MyButton buttonBack, buttonOnePlayer;
	
	public void run(World world) {
		ActionListener listener1 = (e) -> {
        	World.pageStatus = MAIN;
        };
        ActionListener listener2 = (e) -> {
        	World.pageStatus = RANK_LIST_ONE;
        };
        buttonBack = new MyButton(10, 640, 110, 80, Paths.get("data", "images", "previous.png"), listener1);
        buttonOnePlayer = new MyButton(115, 70, 100, 30, null, listener2);
        world.add(buttonBack);
        world.add(buttonOnePlayer);
	}
	
	public void paint(Graphics g) {
		g.drawImage(loadImage(Paths.get("data", "images", "ranklist2.png").toFile()),
					0,0,null);
		g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
					10, 640, 110, 80, null);
	}

	public void clean(World world) {
		if (buttonBack != null)
        	world.remove(buttonBack);
		if (buttonOnePlayer != null)
			world.remove(buttonOnePlayer);
		world.revalidate();
		world.repaint();
	}
	
}
