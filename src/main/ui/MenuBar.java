package main.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.sun.xml.internal.ws.api.Component;

import main.ui.world.World;

import static main.utils.GameLevel.*;

/**
 * Initiate the Menu bar
 *
 * @author 杨芳源
 */
public class MenuBar extends JMenuBar{
	JMenu [] menus;
	JMenuItem itemEasy, itemNormal, itemHard;
	JMenuItem menuitems [][];
	public MenuBar() {
		menus = new JMenu[] {
				new JMenu("难度选择"),
				new JMenu("音量调节")
			};
		itemEasy = new JMenuItem("easy");
		itemNormal = new JMenuItem("normal ✓");
		itemHard = new JMenuItem("hard");
		menuitems = new JMenuItem [][] {
			{itemEasy, itemNormal, itemHard},
			{}
		};
		menus[1].add(new VolumeSlider());
		initMenu();
	}
	
	void initMenu() {
		for( int i=0; i<menus.length; i++ ){
			this.add( menus[i] );
			for( int j=0; j<menuitems[i].length; j++ ){
				menus[i].add( menuitems[i][j]);
				menuitems[i][j].addActionListener(new menuActionListner());
			}
		}
	}
	
	
	void resetItemText(JMenuItem item, String text) {
		if (World.gameLevel == LEVEL_EASY)
			itemEasy.setText("easy");
		if (World.gameLevel == LEVEL_NORMAL)
			itemNormal.setText("normal");
		if (World.gameLevel == LEVEL_HARD)
			itemHard.setText("hard");
		item.setText(text);
	}
	
	class menuActionListner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem)e.getSource();
			String id = item.getText();
			if (id.contains("easy")) {
				resetItemText(item, "easy ✓");
				World.gameLevel = LEVEL_EASY;
			}
			else if (id.contains("normal")) {
				resetItemText(item, "normal ✓");
				World.gameLevel = LEVEL_NORMAL;
			}
			else if (id.contains("hard")) {
				resetItemText(item, "hard ✓");
				World.gameLevel = LEVEL_HARD;
			}
			//System.out.println(World.gameLevel);
		}
		
	}
}
