package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.CLOSE;
import static utils.PageStatus.MAIN;

/**
 * @author 鏉ㄨ姵婧�
 *
 */
public class VictoryPage implements Page {
	MyButton buttonReturn, buttonClose;
	
	public void run(World world) {
		ActionListener listener1 = (e)->{World.pageStatus = MAIN;};
		ActionListener listener2 = (e)->{World.pageStatus = CLOSE;};
		buttonReturn = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "back.png"), listener1);
		buttonClose = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "exit.png"), listener2);
		world.add(buttonReturn);
		world.add(buttonClose);
	}

	public void paint(Graphics g) {
		g.drawImage(loadImage(Paths.get("data", "images", "BackgroundWin.png").toFile()),
					0,0,null);
		g.drawImage(loadImage(Paths.get("data", "images", "back.png").toFile()),
					130, 430, 220, 80, null);
		g.drawImage(loadImage(Paths.get("data", "images", "exit.png").toFile()),
					130, 530, 220, 80, null);
	}

	public void clean(World world) {
		if (buttonReturn!=null)
			world.remove(buttonReturn);
		if (buttonClose!=null)	
			world.remove(buttonClose);
	}
}
