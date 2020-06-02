package world.pages;

import utils.MyButton;
import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.CLOSE;
import static utils.PageStatus.MAIN;
import static world.World.*;

/**
 * @author 鏉ㄨ姵婧�
 */
public class EndPage  implements Page {
    MyButton buttonReturn, buttonClose;

    public void run(World world) throws IOException {
    	addResult(totalScore, totalCoin, playerNumber);
    	
        ActionListener listener1 = (e) -> {
            World.pageStatus = MAIN;
        };
        ActionListener listener2 = (e) -> {
            World.pageStatus = CLOSE;
        };
        buttonReturn = new MyButton(130, 430, 220, 80, Paths.get("data", "images", "back.png"), listener1);
        buttonClose = new MyButton(130, 530, 220, 80, Paths.get("data", "images", "exit.png"), listener2);
        world.add(buttonReturn);
        world.add(buttonClose);
    }

    public void paint(Graphics g) {
        g.drawImage(loadImage(Paths.get("data", "images", "BackgroundLose.png").toFile()),
                	0,0,null);
        g.drawImage(loadImage(Paths.get("data", "images", "back.png").toFile()),
                	130, 430, 220, 80, null);
        g.drawImage(loadImage(Paths.get("data", "images", "exit.png").toFile()),
                	130, 530, 220, 80, null);
        
        Font myFont = new Font("Courier", Font.BOLD, 24);
        g.setFont(myFont);
        g.setColor(Color.white);
        g.drawString("你的得分："+totalScore, 130, 350);
    }

    public void clean(World world) {
        if (buttonReturn != null)
            world.remove(buttonReturn);
        if (buttonClose != null)
            world.remove(buttonClose);
        world.revalidate();
        world.repaint();
    }

}
