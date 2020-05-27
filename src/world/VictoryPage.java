package world;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.CLOSE;
import static utils.PageStatus.MAIN;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

/**
 * @author �Դ
 *
 */
public class VictoryPage {
	static MyButton buttonReturn, buttonClose;
	
	static public void run() {
	}

	static public void paint(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "youwin.png").toFile()), 
					50, 30, 380, 300, null);
		g.drawImage(loadImage(Paths.get("data", "images", "back.png").toFile()), 
					130, 430, 220, 80, null);
		g.drawImage(loadImage(Paths.get("data", "images", "exit.png").toFile()), 
					130, 530, 220, 80, null);
		
		ActionListener listener1 = (e)->{World.pageStatus = MAIN;};
		ActionListener listener2 = (e)->{World.pageStatus = CLOSE;};
		buttonReturn = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "back.png"), listener1);
		buttonClose = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "exit.png"), listener2);
		world.add(buttonReturn);
		world.add(buttonClose);
	}

	public static void clean(World world) {
		if (buttonReturn!=null)
			world.remove(buttonReturn);
		if (buttonClose!=null)	
			world.remove(buttonClose);
	}
}
