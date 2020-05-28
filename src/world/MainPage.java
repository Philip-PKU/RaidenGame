package world;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

import static utils.PageStatus.*;
import static raidenObjects.BaseRaidenObject.loadImage;

/**
 * Main page handler
 * 
 * @author �Դ
 */
public class MainPage {
	static MyButton buttonStart, buttonRanklist, buttonHelp, buttonExit;
	
	static public void run() {
	}
	
	static public void paint(Graphics g, World world) {
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
		
		ActionListener listener1 = (e)->{World.pageStatus = PLAYERCHOSE;};
		ActionListener listener2 = (e)->{World.pageStatus = RANKLIST;};
		ActionListener listener3 = (e)->{World.pageStatus = HELP;};
		ActionListener listener4 = (e)->{World.pageStatus = CLOSE;};
		buttonStart = new MyButton(130, 230, 220, 80, Paths.get("data", "images", "start.png"), listener1);
		buttonRanklist = new MyButton(130, 330, 220, 80, Paths.get("data", "images", "ranklist.png"), listener2);
		buttonHelp = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "help.png"), listener3);
		buttonExit = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "startexit.png"), listener4);
		world.add(buttonStart);
		world.add(buttonRanklist);
		world.add(buttonHelp);
		world.add(buttonExit);
	}
	
	public static void clean(World world) {
		if (buttonExit!=null)
			world.remove(buttonExit);
		if (buttonHelp!=null)
			world.remove(buttonHelp);
		if (buttonRanklist!=null)
			world.remove(buttonRanklist);
		if (buttonStart!=null)
			world.remove(buttonStart);
	}
	
}
