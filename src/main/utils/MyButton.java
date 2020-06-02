package main.utils;

import main.raidenObjects.BaseRaidenObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Path;


/**
 * My button with pictures
 *
 * @author 杨芳源
 */
public class MyButton extends JButton {
    /**
     * Initiate my button
     *
     * @param x:        X coordinate of button.
     * @param y:        Y coordinate of button.
     * @param width:    Width of the button.
     * @param height:   Height of the button.
     * @param path:     A {@link Path} object for retrieving the pictures.
     * @param listener: An {@link ActionListener} object to apply to the button.
     */
    public MyButton(int x, int y, int width, int height, Path path, ActionListener listener) {

        setBounds(x, y, width, height);

        if (listener != null) {
			addActionListener(listener);
		}

        if (path != null) {
            Image image = BaseRaidenObject.loadImage(path.toFile()).getScaledInstance(width, height, Image.SCALE_DEFAULT);
            setIcon(new ImageIcon(image));
        }

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }
}
