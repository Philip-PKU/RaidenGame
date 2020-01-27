import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    final static int windowWidth = 400;
    final static int windowHeight = 700;

    public static void main(String[] args) throws IOException {
        // Construct and initialize the window frame
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        // frame.addKeyListener(l); //添加键盘侦听对象
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  // make the window visible, and call the paint function

        // make mouse invisible
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image img=tk.getImage("");
        Cursor cu=tk.createCustomCursor(img,new Point(10,10),"stick");
        frame.setCursor(cu);

        try {
            world.startGame();
        }
        catch (Exception e) {
        }
    }
}
