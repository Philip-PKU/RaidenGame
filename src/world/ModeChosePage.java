package world;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

import java.awt.Graphics;
import java.nio.file.Paths;

import utils.MyButton;

/**
 * Mode Choice Page handler.
 * 
 * @author �Դ
 */
public class ModeChosePage {
	static MyButton ButtonSurvival, ButtonTime;
	
	static public void run() {
	}

	static public void paint(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "survival.png").toFile()), 
					120, 180, 240, 90, null);
			g.drawImage(loadImage(Paths.get("data", "images", "time.png").toFile()), 
					120, 360, 240, 90, null);
	
		ButtonSurvival = new MyButton(120, 180, 240, 90, GAMING, Paths.get("data", "images", "survival.png"));
		ButtonTime = new MyButton(120, 360, 240, 90, GAMING, Paths.get("data", "images", "time.png"));
		world.add(ButtonSurvival);
		world.add(ButtonTime);
	}

	public static void clean(World world) {
		if (ButtonSurvival!=null)
			world.remove(ButtonSurvival);
		if (ButtonTime!=null)	
			world.remove(ButtonTime);
	}
}