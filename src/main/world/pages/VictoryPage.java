package main.world.pages;

import main.utils.MyButton;
import main.world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.CLOSE;
import static main.utils.PageStatus.MAIN;
import static main.world.World.*;

/**
 * @author 鏉ㄨ姵婧�
 *
 */
public class VictoryPage implements Page {
	MyButton buttonReturn, buttonClose;
	
	public void run(World world) throws IOException {
		World.addResult(totalScore, totalCoin, playerNumber);
		
		ActionListener listener1 = (e)->{World.pageStatus = MAIN;};
		ActionListener listener2 = (e)->{World.pageStatus = CLOSE;};
		buttonReturn = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "back.png"), listener1);
		buttonClose = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "exit.png"), listener2);
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
