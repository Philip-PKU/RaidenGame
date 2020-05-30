package world;

import maryb.player.Player;
import raidenObjects.Background;
import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.*;
import utils.keyAdapters.BaseRaidenKeyAdapter;
import utils.keyAdapters.RaidenKeyAdapter1;
import utils.keyAdapters.RaidenKeyAdapter2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Thread.sleep;
import static utils.GameLevel.LEVEL_NORMAL;
import static utils.GameMode.SURVIVAL;
import static utils.PageStatus.*;
import static utils.PlayerNumber.ONE;

/**
 * The game panel added to JFrame in App (the main class).
 * Functions include initializing the game (in {@code init}) and running the game (in {@code run}).
 * <p>
 * Note: This class must stay inside a non-default package because it will be referenced
 * by other classes, and classes in the default package cannot be imported.
 *
 * @author 蔡辉宇 杨芳源 张哲瑞 唐宇豪
 */
public class World extends JPanel {
    public static Background background;

    // These public static variables can be referenced by any class in the game.
    public static PlayerAircraft player1, player2;
    // Interactants + Aircrafts = All Game Elements
    // Note that every aircraft/interactant should be added to the following lists by the CALLER of its constructor,
    // not the constructor itself. Because in future two-player mode, we might need two sets of lists,
    // and appending every aircraft/interactant to the following lists might cause serious problems.
    public static Deque<BaseAircraft> aircraftList = new ConcurrentLinkedDeque<>();
    public static Deque<BaseRaidenObject> interactantList = new ConcurrentLinkedDeque<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static BaseRaidenKeyAdapter keyAdapter1 = new RaidenKeyAdapter1(), keyAdapter2 = new RaidenKeyAdapter2();
    public static Random rand = new Random();
    public static Player musicPlayer = new Player();
    public static final int windowWidth = 480;
    public static final int windowHeight = 720;
    public static volatile int msToSleepAtEachGameStep = 15;
    public static final int desiredFPS = 50;
    public static Timer gameSpeedAdjusterTimer;
    public static GameMode gameMode = SURVIVAL;
    public static int survivalModeSeconds = 222;
    public static PageStatus pageStatus = GAMING;
    public static PlayerNumber playerNumber = ONE;
    public static GameScheduler gameScheduler;
    public static GameLevel gameLevel = LEVEL_HARD;

    public World() {
        init();
    }

    public void playBackGroundMusic(String path) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        musicPlayer = new Player();
        musicPlayer.setSourceLocation(path);
        musicPlayer.play();
    }

    public static void playSoundEffect(String path) {
        Thread thread = new Thread(() -> {
            Player soundEffectPlayer = new Player();
            soundEffectPlayer.setSourceLocation(path);
            soundEffectPlayer.play();
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Initialize the game.
     *
     * @author 蔡辉宇
     */
    public void init() {
        // The background image
        background = new Background();

        setLayout(null);
        setVisible(true);
        requestFocus();

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
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @return true iff (x, y) is out of the window, and false otherwise.
     * @author 蔡辉宇
     */
    public static boolean isOutOfWindow(float x, float y) {
        return x < 0 || x >= windowWidth || y < 0 || y >= windowHeight;
    }

    /**
     * In gaming interface, paint the panel by painting the background, all aircrafts and all interactants.
     *
     * @author 蔡辉宇
     */
    public void paint(Graphics g) {
        synchronized (this) {
            if (pageStatus != CLOSE) {
                pageStatus.getPage().paint(g);
            }
        }
    }


    /**
     * Remove components when pageStatus changes
     *
     * @author 杨芳源
     */
    void clean(PageStatus flag) {
        if (flag != CLOSE) {
            flag.getPage().clean(this);
        }
    }

    /**
     * Run the handler program for the next page.
     *
     * @throws InterruptedException
     * @author 杨芳源
     */
    void runPageHandler(PageStatus flag) throws InterruptedException {
        if (flag != CLOSE) {
            flag.getPage().run(this);
        }
    }

    /**
     * Run the game from main page.
     *
     * @throws InterruptedException
     * @author 杨芳源
     */
    public void run() throws InterruptedException {
        pageStatus = MAIN;
        PageStatus flag = MAIN;
        runPageHandler(pageStatus);

        while (pageStatus != CLOSE) {
            while (pageStatus == flag) {
                sleep(msToSleepAtEachGameStep);
                repaint();
            }

            System.out.print(flag + " → ");
            System.out.println(pageStatus);

            clean(flag);

            flag = pageStatus;

            repaint();
            runPageHandler(pageStatus);
        }
    }
}

