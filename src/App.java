import world.World;

import javax.swing.*;

import static world.World.windowHeight;
import static world.World.windowWidth;

public class App {

    public static void main(String[] args) {
        // Construct and initialize the window frame
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
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
