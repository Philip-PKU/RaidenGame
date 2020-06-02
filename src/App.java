import world.World;

import javax.swing.*;

import javazoom.jl.decoder.OutputChannels;

import static world.World.windowHeight;
import static world.World.windowWidth;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
    	
        // Construct and initialize the window frame
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth+20, windowHeight+50);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  // make the window visible, and call the paint function

//         make mouse invisible
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Image img = tk.getImage("");
//        Cursor cu = tk.createCustomCursor(img, new Point(10, 10), "stick");
//        frame.setCursor(cu);
        
        try {
            world.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("!!!");
        System.exit(0);
    }
}
