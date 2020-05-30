package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;


/**
 * My button with pictures
 * @author 鏉ㄨ姵婧�
 */
public class MyButton extends JButton {
	/**
	 * Initiate my button
	 * @param x: X coordinate of button.
	 * @param y: Y coordinate of button.
	 * @param width: Width of the button.
	 * @param height: Height of the button.
	 * @param path: A Path object to get the pictures.
	 * @param listener:
	 */
	public MyButton(int x, int y, int width, int height, Path path, ActionListener listener){
		
		setBounds(x, y, width, height);
		
		addActionListener(listener);
		
		if (path == null) return;
		
		URL url = null;
		try {
			url = path.toUri().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon ii = new ImageIcon(url);  
		//鏍规嵁鎸夐挳澶у皬鏀瑰彉鍥剧墖澶у皬  
		Image temp = ii.getImage().getScaledInstance(getWidth(), getHeight(), ii.getImage().SCALE_DEFAULT);
		ii = new ImageIcon(temp);  
		setIcon(ii);
	}
}