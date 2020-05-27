package world;

import java.awt.Graphics;
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
	static MyButton ButtonStart, ButtonRanklist, ButtonHelp, ButtonExit;
	
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
		
		ButtonStart = new MyButton(130, 230, 220, 80, PLAYERCHOSE, Paths.get("data", "images", "start.png"));
		ButtonRanklist = new MyButton(130, 330, 220, 80, RANKLIST , Paths.get("data", "images", "ranklist.png"));
		ButtonHelp = new MyButton(130, 430, 220, 80, HELP, Paths.get("data", "images", "help.png"));
		ButtonExit = new MyButton(130, 530, 220, 80, CLOSE, Paths.get("data", "images", "startexit.png"));
		world.add(ButtonStart);
		world.add(ButtonRanklist);
		world.add(ButtonHelp);
		world.add(ButtonExit);
	}
	
	public static void clean(World world) {
		world.remove(ButtonExit);
		world.remove(ButtonHelp);
		world.remove(ButtonRanklist);
		world.remove(ButtonStart);
	}
	
}
