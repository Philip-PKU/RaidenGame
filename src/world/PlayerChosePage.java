package world;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

import java.awt.Button;
import java.awt.Graphics;
import java.nio.file.Paths;

import utils.MyButton;

/**
 * Player chose page handler
 * 
 * @author �Դ
 */
public class PlayerChosePage {
	static MyButton ButtonOne, ButtonTwo;
	
	static public void run() {
	}
	
	static public void paint(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "oneplayer.png").toFile()), 
					120, 180, 240, 90, null);
		g.drawImage(loadImage(Paths.get("data", "images", "twoplayer.png").toFile()), 
					120, 360, 240, 90, null);
		
		ButtonOne = new MyButton(120, 180, 240, 90, MODECHOSE, Paths.get("data", "images", "oneplayer.png"));
		ButtonTwo = new MyButton(120, 360, 240, 90, MODECHOSE, Paths.get("data", "images", "twoplayer.png"));
		world.add(ButtonOne);
		world.add(ButtonTwo);
	}

	public static void clean(World world) {
		if (ButtonOne!=null)
			world.remove(ButtonOne);
		if (ButtonTwo!=null)
			world.remove(ButtonTwo);
	}
}
