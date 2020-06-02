package world.pages;

import world.World;

import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import utils.MyButton;

import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.PageStatus.*;

/**
 * @author 鏉ㄨ姵婧�
 *
 */
public class HelpPage implements Page {
	MyButton buttonBack, buttonPage1, buttonPage2, buttonPage3;
	static int pageNumber = 1;
	
	public void run(World world) {
		ActionListener listener = (e) -> {
        	World.pageStatus = MAIN;
        };
        ActionListener listener1 = (e) -> {
        	pageNumber = 1;
        	world.repaint();
        };
        ActionListener listener2 = (e) -> {
        	pageNumber = 2;
        	world.repaint();
        };
        ActionListener listener3 = (e) -> {
        	pageNumber = 3;
        	world.repaint();
        };
        buttonBack = new MyButton(360, 640, 120, 80, Paths.get("data", "images", "previous.png"), listener);
        buttonPage1 = new MyButton(22, 655, 90, 35, null, listener1);
        buttonPage2 = new MyButton(130, 655, 90, 35, null, listener2);
        buttonPage3 = new MyButton(240, 655, 90, 35, null, listener3);
        world.add(buttonBack);
        world.add(buttonPage1);
        world.add(buttonPage2);
        world.add(buttonPage3);
	}
	
	public void paint(Graphics g) {
		if (pageNumber == 1)
			g.drawImage(loadImage(Paths.get("data", "images", "help1.png").toFile()),0,0,null);
		if (pageNumber == 2)
			g.drawImage(loadImage(Paths.get("data", "images", "help2.png").toFile()),0,0,null);
		if (pageNumber == 3)
			g.drawImage(loadImage(Paths.get("data", "images", "help3.png").toFile()),0,0,null);
		g.drawImage(loadImage(Paths.get("data", "images", "previous.png").toFile()),
                	360, 640, 100, 70, null);
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
