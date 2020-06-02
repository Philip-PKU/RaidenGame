package main;

import main.world.World;

import javax.swing.*;

import static main.world.World.windowHeight;
import static main.world.World.windowWidth;

public class App {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
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
