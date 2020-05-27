package world;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import utils.MyButton;

import static utils.PageStatus.*;
import static raidenObjects.BaseRaidenObject.loadImage;

/**
 * Main page handler
 * 
 * @author �Դ
 */
public class MainPage {
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
		world.add(new MyButton(130, 230, 220, 80, PLAYERCHOSE, Paths.get("data", "images", "start.png")));
		world.add(new MyButton(130, 330, 220, 80, RANKLIST , Paths.get("data", "images", "ranklist.png")));
		world.add(new MyButton(130, 430, 220, 80, HELP, Paths.get("data", "images", "help.png")));
		world.add(new MyButton(130, 530, 220, 80, CLOSE, Paths.get("data", "images", "startexit.png")));
	}
	
}
