package utils;

import static utils.PageStatus.GAMING;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import world.World;


/**
 * My button with pictures
 * @author �Դ
 */
public class MyButton extends JButton {
	/**
	 * Initiate my button
	 * @param x: X coordinate of button.
	 * @param y: Y coordinate of button.
	 * @param width: Width of the button.
	 * @param height: Height of the button.
	 * @param pageStatus: Change of pageStatus when the button is pressed.
	 * @param path: A Path object to get the pictures.
	 */
	public MyButton(int x, int y, int width, int height, PageStatus pageStatus, Path path){
		
		setBounds(x, y, width, height);
		
		URL url = null;
		try {
			url = path.toUri().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon ii = new ImageIcon(url);  
		//���ݰ�ť��С�ı�ͼƬ��С  
		Image temp = ii.getImage().getScaledInstance(getWidth(), getHeight(), ii.getImage().SCALE_DEFAULT);
		ii = new ImageIcon(temp);  
		setIcon(ii);
		
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				World.pageStatus = GAMING;
			}
		});
	}
}
