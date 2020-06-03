package main.ui.pages;

import main.World;
import main.utils.RaidenButton;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

import static main.World.*;
import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.CLOSE;
import static main.utils.PageStatus.MAIN;

/**
 * @author 鏉ㄨ姵婧�
 *
 */
public class VictoryPage implements Page {
	RaidenButton buttonReturn, buttonClose;
	
	public void run(World world) throws IOException {
		World.addResult(totalScore, totalCoin, playerNumber);
		
		buttonReturn = new RaidenButton(130, 430, 220, 80, Paths.get("data", "images", "back.png"),
				e -> world.changePageStatus(MAIN));
		buttonClose = new RaidenButton(130, 530, 220, 80, Paths.get("data", "images", "exit.png"),
				e -> world.changePageStatus(CLOSE));
		world.add(buttonReturn);
		world.add(buttonClose);
		world.repaint();
	}

	public void paintComponent(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "BackgroundWin.png").toFile()),
					0,0,null);
		
		Font myFont = new Font("Courier", Font.PLAIN, 24);
        g.setFont(myFont);
        g.setColor(Color.white);
        g.drawString("你的得分："+totalScore, 160, 350);

        world.paintComponents(g);
	}

	public void clean(World world) {
		if (buttonReturn!=null)
			world.remove(buttonReturn);
		if (buttonClose!=null)	
			world.remove(buttonClose);
		world.revalidate();
		world.repaint();
	}
}
