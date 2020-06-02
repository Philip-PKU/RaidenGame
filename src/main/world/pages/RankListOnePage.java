package main.world.pages;

import main.utils.MyButton;
import main.world.World;

import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.MAIN;
import static main.utils.PageStatus.RANK_LIST_TWO;

/**
 * @author 杨芳源
 *
 */
public class RankListOnePage implements Page {
	MyButton buttonBack, buttonTwoPlayer;
	List<Integer> sList, cList;
	
	public void run(World world) throws IOException {
        buttonBack = new MyButton(10, 640, 110, 80, Paths.get("data", "images", "previous.png"),
				e -> world.changePageStatus(MAIN));
        buttonTwoPlayer = new MyButton(315, 75, 100, 30, null,
				e -> world.changePageStatus(RANK_LIST_TWO));
        world.add(buttonBack);
        world.add(buttonTwoPlayer);
		world.repaint();

		sList = new ArrayList<>();
		cList = new ArrayList<>();
		File file = Paths.get("data", "result", "1.txt").toFile();
		try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
			int n = input.readInt();
			for (int i = 0; i < n; i++) {
				int s = input.readInt();
				int c = input.readInt();
				sList.add(s);
				cList.add(c);
			}
		} catch (Exception ignored) {
		}
	}

	public void paintComponent(Graphics g, World world) throws IOException {
		g.drawImage(loadImage(Paths.get("data", "images", "ranklist1.png").toFile()),
					0,0,null);

		if (sList != null && cList != null) {
			int height = 235;
			Font myFont = new Font("Courier", Font.BOLD, 24);
			g.setFont(myFont);
			g.setColor(Color.white);
			for (int i = 0; i < sList.size(); i++) {
				int s = sList.get(i);
				int c = cList.get(i);
				g.drawString(Integer.toString(s), 170, height);
				g.drawString(Integer.toString(c), 310, height);
				height += 49;
			}
		}

		world.paintComponents(g);
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
