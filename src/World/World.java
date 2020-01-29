package World;

import RaidenObjects.Aircrafts.BaseAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.BigShootingAircraft;
import RaidenObjects.Aircrafts.ShootingAircrafts.PlayerAircraft;
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

public class World extends JPanel {
    private static Background background;

    public static PlayerAircraft player1, player2;
    public static List<BaseAircraft> aircraftList = new LinkedList<>();
    public static List<BaseRaidenObject> interactantList = new LinkedList<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static RaidenKeyAdapter1 keyAdapter = new RaidenKeyAdapter1();
    public static Random rand = new Random();

    public final static int windowWidth = 640;
    public final static int windowHeight = 960;

    public World() {
        init();
    }

    public void init() {
        background = new Background();

        aircraftList.clear();
        interactantList.clear();

        aircraftList.add(new BigShootingAircraft(40, 20));
        aircraftList.add(new BigShootingAircraft(540, 20));

        player1 = new PlayerAircraft(200, 600, RaidenObjectOwner.PLAYER1, RaidenObjectController.KEYBOARD1);
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
        while(!aircraftList.isEmpty() && player1 != null) {
            synchronized (this) {
                background.step();
                aircraftList.forEach(BaseAircraft::step);
                interactantList.forEach(BaseRaidenObject::step);
                for (int i = 0; i < aircraftList.size(); ++i) {
                    for (int j = i + 1; j < aircraftList.size(); ++j) {
                        aircraftList.get(i).interactWith(aircraftList.get(j));
                    }
                }
                aircraftList.removeIf(aircraft -> !aircraft.isOnScreen());
                interactantList.removeIf(interactant -> !interactant.isOnScreen());
            }
            repaint();
            gameStep.increment();
            sleep(15);
        }
        System.out.println("End");
    }
}
