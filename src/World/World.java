package World;

import RaidenObjects.Aircrafts.BaseAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.BigShootingAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.MiddleShootingAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.PlayerAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.SmallShootingAircraft;
import RaidenObjects.Background;
import RaidenObjects.BaseRaidenObject;
import Utils.RaidenKeyAdapter1;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

// import Utils.Mp3Player;
// import java.nio.file.Paths;

public class World extends JPanel {
    private static Background background;

    public static PlayerAircraft player1, player2;
    public static List<BaseAircraft> aircraftList = new LinkedList<>();
    public static List<BaseRaidenObject> interactantList = new LinkedList<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static RaidenKeyAdapter1 keyAdapter = new RaidenKeyAdapter1();
    public static Random rand = new Random();

    public final static int windowWidth = 480;
    public final static int windowHeight = 720;

    public World() {
        init();
    }

    public void init() {
        background = new Background();

        aircraftList.clear();
        interactantList.clear();

        player1 = new PlayerAircraft(windowWidth/2.0f, windowHeight - 150,
                RaidenObjectOwner.PLAYER1, RaidenObjectController.KEYBOARD1);
        aircraftList.add(player1);

        gameStep.setValue(0);
    }

    public static boolean isOutOfWindow(float x, float y) {
        return x < 0 || x >= windowWidth || y < 0 || y >= windowHeight;
    }

    public void paint(Graphics g) {
        synchronized (this) {
            background.paint(g);
            aircraftList.forEach(aircraft -> aircraft.paint(g));
            interactantList.forEach(interactant -> interactant.paint(g));
        }
    }

    public void startGame() throws InterruptedException {
        // new Mp3Player(Paths.get("data", "bgm", "05. Unknown Pollution.mp3").toFile(), 100000).start();
        while(!aircraftList.isEmpty() && player1 != null) {
            synchronized (this) {
                if (gameStep.intValue() % 279 == 37) {
                    aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth), 0));
                }
                if (gameStep.intValue() % 269 == 0) {
                    aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth/2) + windowWidth/2.0f, 0));
                }
                if (gameStep.intValue() % 269 == 198) {
                    aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth / 2), 0));
                }
                if (gameStep.intValue() % 397 == 100) {
                    aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth/2), 0));
                }
                if (gameStep.intValue() % 397 == 300) {
                    aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth/2) + windowWidth/2.0f, 0));
                }
                background.step();
                aircraftList.forEach(BaseAircraft::step);
                interactantList.forEach(BaseRaidenObject::step);
                for (int i = 0; i < aircraftList.size(); ++i) {
                    for (int j = i + 1; j < aircraftList.size(); ++j) {
                        aircraftList.get(i).interactIfContacted(aircraftList.get(j));
                    }
                }
                aircraftList.removeIf(BaseRaidenObject::isOffScreen);
                interactantList.removeIf(BaseRaidenObject::isOffScreen);
            }
            repaint();
            gameStep.increment();
            sleep(15);
        }
        System.out.println("End");
    }
}
