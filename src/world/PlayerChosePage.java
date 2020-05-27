package world;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;
import static utils.PlayerNumber.*;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

/**
 * Player chose page handler
 * 
 * @author �Դ
 */
public class PlayerChosePage {
	static MyButton buttonOne, buttonTwo;
	
	static public void run() {
	}
	
	static public void paint(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "oneplayer.png").toFile()), 
					120, 180, 240, 90, null);
		g.drawImage(loadImage(Paths.get("data", "images", "twoplayer.png").toFile()), 
					120, 360, 240, 90, null);
		
		ActionListener listener1 = (e)->{World.pageStatus = MODECHOSE; World.playerNumber = ONE;};
		ActionListener listener2 = (e)->{World.pageStatus = MODECHOSE; World.playerNumber = TWO;};
		buttonOne = new MyButton(120, 180, 240, 90, Paths.get("data", "images", "oneplayer.png"), listener1);
		buttonTwo = new MyButton(120, 360, 240, 90, Paths.get("data", "images", "twoplayer.png"), listener2);
		world.add(buttonOne,false);
		world.add(buttonTwo,false);
	}

	public static void clean(World world) {
		if (buttonOne!=null)
			world.remove(buttonOne);
		if (buttonTwo!=null)
			world.remove(buttonTwo);
	}
}
