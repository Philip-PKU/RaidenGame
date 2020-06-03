package main.ui.pages;

import main.World;
import main.utils.RaidenButton;

import java.awt.*;
import java.nio.file.Paths;

import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.PageStatus.MAIN;

/**
 * @author 鏉ㄨ姵婧�
 *
 */
public class HelpPage implements Page {
	RaidenButton buttonBack, buttonPage1, buttonPage2, buttonPage3;
	static int pageNumber = 1;
	
	public void run(World world) {
        buttonBack = new RaidenButton(360, 640, 100, 70, Paths.get("data", "images", "previous.png"),
				e -> world.changePageStatus(MAIN));
        buttonPage1 = new RaidenButton(22, 655, 90, 35, null,
				e -> {
					pageNumber = 1;
					world.repaint();
				});
        buttonPage2 = new RaidenButton(130, 655, 90, 35, null,
				e -> {
        			pageNumber = 2;
        			world.repaint();
				});
        buttonPage3 = new RaidenButton(240, 655, 90, 35, null,
				e -> {
        			pageNumber = 3;
        			world.repaint();
				});

        world.add(buttonBack);
        world.add(buttonPage1);
        world.add(buttonPage2);
        world.add(buttonPage3);
        world.repaint();
	}
	
	public void paintComponent(Graphics g, World world) {
		g.drawImage(loadImage(Paths.get("data", "images", "help" + pageNumber + ".png").toFile()),0,0,null);
		world.paintComponents(g);
	}

	public void clean(World world) {
		if (buttonBack != null)
        	world.remove(buttonBack);
		if (buttonPage1 != null)
			world.remove(buttonPage1);
		if (buttonPage2 != null)
			world.remove(buttonPage2);
		if (buttonPage3 != null)
			world.remove(buttonPage3);
		pageNumber = 1;
		world.revalidate();
		world.repaint();
	}
	
}
