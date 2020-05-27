package world;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;
import static utils.GameMode.*;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.GameMode;
import utils.MyButton;

/**
 * Mode Choice Page handler.
 * 
 * @author �Դ
 */
public class ModeChosePage {
	static MyButton buttonSurvival, buttonTime;
	
	static public void run() {
	}

	static public void paint(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "survival.png").toFile()), 
					120, 180, 240, 90, null);
			g.drawImage(loadImage(Paths.get("data", "images", "time.png").toFile()), 
					120, 360, 240, 90, null);
	
		ActionListener listener1 = (e)->{World.pageStatus = GAMING; World.gameMode = SURVIVAL;};
		ActionListener listener2 = (e)->{World.pageStatus = GAMING; World.gameMode = TIMER;};
		buttonSurvival = new MyButton(120, 180, 240, 90, Paths.get("data", "images", "survival.png"), listener1);
		buttonTime = new MyButton(120, 360, 240, 90, Paths.get("data", "images", "time.png"), listener2);
		world.add(buttonSurvival);
		world.add(buttonTime);
	}

	public static void clean(World world) {
		if (buttonSurvival!=null)
			world.remove(buttonSurvival);
		if (buttonTime!=null)	
			world.remove(buttonTime);
	}
}