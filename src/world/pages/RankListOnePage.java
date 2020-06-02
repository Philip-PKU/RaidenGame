package world.pages;

import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import utils.MyButton;
import utils.ResultPair;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;
import static utils.PlayerNumber.ONE;

/**
 * @author 杨芳源
 *
 */
public class RankListOnePage implements Page {
	MyButton buttonBack, buttonTwoPlayer;
	
	public void run(World world) {
		ActionListener listener1 = (e) -> {
        	World.pageStatus = MAIN;
        };
        ActionListener listener2 = (e) -> {
        	World.pageStatus = RANK_LIST_TWO;
        };
        buttonBack = new MyButton(10, 640, 110, 80, Paths.get("data", "images", "previous.png"), listener1);
        buttonTwoPlayer = new MyButton(315, 75, 100, 30, null, listener2);
        world.add(buttonBack);
        world.add(buttonTwoPlayer);
	}
	
	public void paint(Graphics g) throws IOException {
		g.drawImage(loadImage(Paths.get("data", "images", "ranklist1.png").toFile()),
					0,0,null);
		g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
					10, 640, 110, 80, null);
		
		File file = Paths.get("data", "result", "1.txt").toFile();
		DataInputStream input = null;
		try {
			input= new DataInputStream(new FileInputStream(file));
		} catch (Exception e) {}
		
		int n = input.readInt();
		int height = 235;
		Font myFont = new Font("Courier", Font.BOLD, 24);
        g.setFont(myFont);
        g.setColor(Color.white);
		for (int i = 0; i < n; i++) {
			int s = input.readInt();
			int c = input.readInt();
			g.drawString(Integer.toString(s), 170, height);
			g.drawString(Integer.toString(c), 310, height);
			height += 49;
		}
		input.close();
	}

	public void clean(World world) {
		if (buttonBack != null)
        	world.remove(buttonBack);
		if (buttonTwoPlayer != null)
			world.remove(buttonTwoPlayer);
		world.revalidate();
		world.repaint();
	}
	
}
