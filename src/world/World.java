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
import java.nio.file.Paths;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Thread.sleep;
import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.GameLevel.*;
import static utils.GameMode.*;
import static utils.PageStatus.*;
import static utils.PlayerNumber.*;

/**
 * The game panel added to JFrame in App (the main class).
 * Functions include initializing the game (in {@code init}) and running the game (in {@code run}).
 * <p>
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
    public static GameScheduler gameScheduler = new GameScheduler(LEVEL_NORMAL, playerNumber);

    public World() {
        init();
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

        // The background music
        // TODO: change the bgm in different scenarios
        musicPlayer.setSourceLocation(Paths.get("data", "bgm", "05. Unknown Pollution.mp3").toString());
        // The volume control functions in class {@code Player} does not appear to work. DO NOT USE IT.
        // Instead, use {@Code VolumeController} in package utils.
        VolumeController.setVolume(0.1f);

        // Clear aircraft and interactant lists.
        aircraftList.clear();
        interactantList.clear();

        // Set game scheduler and initialize
        if (playerNumber == TWO) {
            player1 = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                    Faction.PLAYER1, PlayerController.KEYBOARD1);
            aircraftList.add(player1);
            player2 = new PlayerAircraft(windowWidth * .25f, windowHeight - 150,
                    Faction.PLAYER2, PlayerController.KEYBOARD2);
            aircraftList.add(player2);
        } else {
            player1 = new PlayerAircraft(windowWidth * .5f, windowHeight - 150,
                    Faction.PLAYER1, PlayerController.KEYBOARD1);
            aircraftList.add(player1);
        }

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
     * Paint the Gaming page
     *
     * @author 蔡辉宇
     */
    public void paintGame(Graphics g) {
        aircraftList.forEach(aircraft -> {
            if (aircraft != null)
                aircraft.paint(g);
        });
        interactantList.forEach(interactant -> interactant.paint(g));
        // Game state info should be at the top of the game page, so we paint it last
        paintGameState(g);
    }

    /**
     * Paint the game state, including HP bar, number of coins and game points earned.
     *
     * @author 杨芳源
     */
    public void paintGameState(Graphics g) {
        Font defaultFont = new Font("Dialog", Font.PLAIN, 12);
        //System.out.println(defaultFont);
        if (player1 != null) {
            g.setColor(Color.white);
            g.drawString("生命：", (int) (windowWidth * 0.05), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player1.getHp() / player1.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("得分：" + player1.getScore(), (int) (windowWidth * 0.05), (int) (windowHeight * 0.09));

            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.05), (int) (windowHeight * 0.11), null);
            //Font font = new Font("宋体",Font.BOLD,15);
            //g.setFont(font);
            g.drawString("\u00D7" + player1.getCoin(),
                    (int) (windowWidth * 0.1), (int) (windowHeight * 0.13));

            g.drawImage(loadImage(Paths.get("data", "images", "SuperpowerBonusSmall.png").toFile()),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + player1.getAvailableSuperpowers(),
                    (int) (windowWidth * 0.25), (int) (windowHeight * 0.13));
        }
        if (player2 != null) {
            g.setColor(Color.white);
            //g.setFont(defaultFont);
            g.drawString("生命：", (int) (windowWidth * 0.6), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player2.getHp() / player2.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("得分：" + player2.getScore(), (int) (windowWidth * 0.60), (int) (windowHeight * 0.09));

            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.60), (int) (windowHeight * 0.11), null);
            //Font font = new Font("宋体",Font.BOLD,15);
            //g.setFont(font);
            g.drawString("\u00D7" + player2.getCoin(),
                    (int) (windowWidth * 0.65), (int) (windowHeight * 0.13));
            g.drawImage(loadImage(Paths.get("data", "images", "SuperpowerBonusSmall.png").toFile()),
                    (int) (windowWidth * 0.75), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + player2.getAvailableSuperpowers(),
                    (int) (windowWidth * 0.8), (int) (windowHeight * 0.13));
        }
    }

    /**
     * In gaming interface, paint the panel by painting the background, all aircrafts and all interactants.
     *
     * @param g A java.awt.Graphics object.
     * @author 蔡辉宇
     */
    public void paint(Graphics g) {
        synchronized (this) {
            background.paint(g);
            switch (pageStatus){
            	case MAIN:
					MainPage.paint(g, this);
            		break;
            	case HELP:
            		HelpPage.paint(g);
            		break;
            	case RANKLIST:
            		RanklistPage.paint(g);
            		break;
            	case MODECHOSE:
            		ModeChosePage.paint(g);
            		break;
            	case PLAYERCHOSE:
            		PlayerChosePage.paint(g, this);
            		break;
            	case GAMING:
            		paintGame(g);
            		break;
            	case VICTORY:
            		VictoryPage.paint(g);
            		break;
            	case END:
            		EndPage.paint(g);
            		break;
            	default:
            }
        }
    }

    /**
     * Run the game.
     *
     * @throws InterruptedException If sleep is interrupted.
     * @author 蔡辉宇
     */
    public void runGame() throws InterruptedException {
        musicPlayer.play();
        gameSpeedAdjusterTimer.start();
        while (player1 != null || player2 != null) {
            if (musicPlayer.isEndOfMediaReached()) {
                musicPlayer.seek(0);
                musicPlayer.play();
            }
            // Periodically add new planes / bonuses
            gameScheduler.scheduleObjectInserts();

            // Move everything in the game one step forward
            background.step();
            aircraftList.forEach(BaseAircraft::step);
            interactantList.forEach(BaseRaidenObject::step);

            // Remove off screen objects from the global lists and fields
            aircraftList.removeIf(BaseRaidenObject::isInvisibleOrOutOfWorld);
            interactantList.removeIf(BaseRaidenObject::isInvisibleOrOutOfWorld);
            // TODO: inform UI that player1 has died and collect scores before setting it to NULL
            if (player1 != null && !player1.isAlive())
                player1 = null;
            if (player2 != null && !player2.isAlive())
                player2 = null;

            // Periodically print the score
            if (gameStep.intValue() % 100 == 0) {
                if (player1 != null)
                    System.out.println("player1: " + player1.calculateScore());
                if (playerNumber == TWO && player2 != null)
                    System.out.println("player2: " + player2.calculateScore());
            }
            repaint();
            sleep(msToSleepAtEachGameStep);

            gameStep.increment();
            if (gameMode == SURVIVAL && gameStep.intValue() >= desiredFPS * survivalModeSeconds) {
                System.out.println("Victory!");
                musicPlayer.stop();
                gameSpeedAdjusterTimer.stop();
                //pageStatus = VICTORY;
            }
        }
        System.out.println("Game over");
        musicPlayer.stop();
        gameSpeedAdjusterTimer.stop();
        //pageStatus = END;
    }


    /**
     * Run the game from main page.
     * 
     * @throws InterruptedException
     * @author 杨芳源
     */
    public void run() throws InterruptedException{
    	pageStatus = PLAYERCHOSE;
    	PageStatus flag = MAIN;
    	while (pageStatus != CLOSE) {
    		while (pageStatus == flag) {
    			sleep(msToSleepAtEachGameStep);
        		gameStep.increment();
    		}
    		System.out.print(flag);
    		System.out.println(pageStatus);
    		switch (flag) {
    		case MAIN:
        		MainPage.clean(this);
        		break;
        	case HELP:
        		HelpPage.clean(this);
        		break;
        	case RANKLIST:
        		RanklistPage.clean(this);
        		break;
        	case PLAYERCHOSE:
        		PlayerChosePage.clean(this);
        		break;
        	case MODECHOSE:
        		//ModeChosePage.clean(this);
        		break;
        	case VICTORY:
        		VictoryPage.clean(this);
        		break;
        	case END:
        		EndPage.clean(this);
        		break;
        	default:
    		}
    		flag = pageStatus;
    		repaint();
    		switch (pageStatus){
        	case MAIN:
        		MainPage.run();
        		break;
        	case HELP:
        		HelpPage.run();
        		break;
        	case RANKLIST:
        		RanklistPage.run();
        		break;
        	case PLAYERCHOSE:
        		PlayerChosePage.run();
        		break;
        	case MODECHOSE:
        		ModeChosePage.run();
        		break;
        	case GAMING:
        		//System.out.println("((((((((((((");
        		runGame();
        		//System.out.println("))))))))))))");
        		break;
        	case VICTORY:
        		VictoryPage.run();
        		break;
        	case END:
        		EndPage.run();
        		break;
        	default: return;
    		}
    	}
    }
}