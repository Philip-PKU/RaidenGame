package main;

import static main.ui.world.World.windowHeight;
import static main.ui.world.World.windowWidth;

import javax.swing.*;

import main.ui.world.World;

public class App {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("飞机大战");
        World world = null;
        try {
            world = new World();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth + 15, windowHeight + 38);
        frame.setLocationRelativeTo(null);

        frame.add(world);
        frame.setVisible(true);  // make the window visible, and call the paint function
    }
}
