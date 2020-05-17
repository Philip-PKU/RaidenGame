package world;

import maryb.player.Player;
import raidenObjects.Background;
import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.BumpingAircraft;
import raidenObjects.aircrafts.bonus.CoinBonus;
import raidenObjects.aircrafts.bonus.CureBonus;
import raidenObjects.aircrafts.bonus.InvincibleBonus;
import raidenObjects.aircrafts.bonus.MagnetBonus;
import raidenObjects.aircrafts.shootingAircrafts.BarbetteAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BigShootingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BlackHoleAircraft;
import raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import raidenObjects.aircrafts.shootingAircrafts.SmallShootingAircraft;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * The game panel added to JFrame in App (the main class).
 * Functions include initializing the game (in {@code init}) and running the game (in {@code run}).
 *
 * Note: This class must stay inside a non-default package because it will be referenced
 * by other classes, and classes in the default package cannot be imported.
 */
public class World extends JPanel {
    private static Background background;

    // These public static variables can be referenced by any class in the game.
    public static PlayerAircraft player1, player2;
    // Interactants + Aircrafts = All Game Elements
    // Note that every aircraft/interactant should be added to the following lists by the CALLER of its constructor,
    // not the constructor itself. Because in future two-player mode, we might need two sets of lists,
    // and appending every aircraft/interactant to the following lists might cause serious problems.
    public static List<BaseAircraft> aircraftList = new LinkedList<>();
    public static List<BaseRaidenObject> interactantList = new LinkedList<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static BaseRaidenKeyAdapter keyAdapter1 = new RaidenKeyAdapter1();
    public static Random rand = new Random();
    public static Player musicPlayer = new Player();
    public static final int windowWidth = 480;
    public static final int windowHeight = 720;
    public static volatile int msToSleepAtEachGameStep = 15;
    public static final int desiredFPS = 50;
    public static Timer gameSpeedAdjusterTimer;

    public World() {
        init();
    }

    /**
     * Initialize the game.
     * @author 钄¤緣瀹�
     */
    public void init() {
        // The background image
        background = new Background();

        // The background music
        // TODO: change the bgm in different scenarios
        musicPlayer.setSourceLocation(Paths.get("data", "bgm", "05. Unknown Pollution.mp3").toString());
        // The volume control functions in {@code Player} does not appear to work. DO NOT USE IT.
        // Instead, use {@Code VolumeController} in package utils.
        VolumeController.setVolume(0.1f);

        // Clear aircraft and interactant lists.
        aircraftList.clear();
        interactantList.clear();

        // Initialize the player
        player1 = new PlayerAircraft(windowWidth/2.0f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
        aircraftList.add(player1);

        // Reset game step to zero.
        gameStep.setValue(0);

        // Timer to adjust game speed
        gameSpeedAdjusterTimer = new Timer(1000, new ActionListener() {
            int lastGameStep;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastGameStep != 0) {
                    int fps = gameStep.intValue() - lastGameStep;
                    float overheadPerStep = (1000 - fps * msToSleepAtEachGameStep) / (float) fps;
                    msToSleepAtEachGameStep = (int) ((1000f / desiredFPS) - overheadPerStep);
                    if (msToSleepAtEachGameStep < 0)
                        msToSleepAtEachGameStep = 0;
                }
                lastGameStep = gameStep.intValue();
            }
        });
        gameSpeedAdjusterTimer.setRepeats(true);
    }

    /**
     * Convenience function checking if a given point if out of the game panel.
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return true iff (x, y) is out of the window, and false otherwise.
     * @author 钄¤緣瀹�
     */
    public static boolean isOutOfWindow(float x, float y) {
        return x < 0 || x >= windowWidth || y < 0 || y >= windowHeight;
    }

    /**
     * TODO: In startup interface, paint the menu, etc.
     * In gaming interface, paint the panel by painting the background, all aircrafts and all interactants.
     * @param g A java.awt.Graphics object.
     * @author 蔡辉宇
     */
    public void paint(Graphics g) {
        synchronized (this) {
            background.paint(g);
            aircraftList.forEach(aircraft -> aircraft.paint(g));
            interactantList.forEach(interactant -> interactant.paint(g));
        }
    }

    /**
     * Run the game.
     * @throws InterruptedException If sleep is interrupted.
     * @author 蔡辉宇
     */
    public void run() throws InterruptedException {
        musicPlayer.play();
        gameSpeedAdjusterTimer.start();
        while(!aircraftList.isEmpty() && player1 != null) {
            synchronized (this) {
                // Periodically add new planes
            	if (gameStep.intValue() % 679 == 37) {
                    aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth), 0));
                }
                if (gameStep.intValue() % 569 == 198) {
                    aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth), 0));
                }
                if (gameStep.intValue() % 997 == 100) {
                    aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth/2), 0));
                }
                if (gameStep.intValue() % 997 == 300) {
                    aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth/2) + windowWidth/2.0f, 0));
                }
                if (gameStep.intValue() % 597 == 200) {
                    aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0));
                }
                if(gameStep.intValue() % 1094 == 432) {
                	aircraftList.add(new BlackHoleAircraft(rand.nextInt(windowWidth), 0));
                }
                if(gameStep.intValue() % 794 == 732) {
                	aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth), 0));
                }
                if(gameStep.intValue() % 50 == 0 && rand.nextInt(100) > 95) {
                	aircraftList.add(new InvincibleBonus(rand.nextInt(windowWidth), 0));
                }
                if(gameStep.intValue()% 20 == 0 && rand.nextInt(100) > 95) {
                	aircraftList.add(new CoinBonus(rand.nextInt(windowWidth), 0, rand.nextInt(3)));
                }
                if(gameStep.intValue() % 50 == 0 && rand.nextInt(100) > 95) {
                	aircraftList.add(new CureBonus(rand.nextInt(windowWidth), 0));
                }
                if(gameStep.intValue() % 100 == 0 && rand.nextInt(100) > 95) {
                	aircraftList.add(new MagnetBonus(rand.nextInt(windowWidth), 0));
                }
                // Move everything in the game one step forward
                background.step();
                aircraftList.forEach(BaseAircraft::step);
                interactantList.forEach(BaseRaidenObject::step);

                // Remove off screen objects from the global lists and fields
                aircraftList.removeIf(BaseRaidenObject::isOffScreen);
                interactantList.removeIf(BaseRaidenObject::isOffScreen);
                if (player1 != null && !player1.isAlive())
                    player1 = null;
                if (player2 != null && !player2.isAlive())
                    player2 = null;
            }
            repaint();
            sleep(msToSleepAtEachGameStep);

            gameStep.increment();
        }
        System.out.println("End");
        musicPlayer.stop();
        gameSpeedAdjusterTimer.stop();
    }
}