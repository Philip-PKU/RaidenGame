/**
 * 
 */
package world.pages;

import world.World;

import java.awt.*;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;

/**
 * @author 杨芳源
 *
 */
public class HelpPage implements Page {
	public void run(World world) {
		
	}
	public void paint(Graphics g) {
		g.drawImage(loadImage(Paths.get("data", "images", "Background.png").toFile()),
				0,0,null);
	}
	/**
	 * @param world
	 */
	public void clean(World world) {
		// TODO Auto-generated method stub
		world.revalidate();
		world.repaint();
	}
	
}
