package world;

import maryb.player.Player;
import raidenObjects.Background;
import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
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
    public static List<BaseAircraft> aircraftList = new LinkedList<>();
    public static List<BaseRaidenObject> interactantList = new LinkedList<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static BaseRaidenKeyAdapter keyAdapter1 = new RaidenKeyAdapter1(), keyAdapter2 = new RaidenKeyAdapter2();
    public static Random rand = new Random();
    public static Player musicPlayer = new Player();
    public static final int windowWidth = 480;
    public static final int windowHeight = 720;
    public static volatile int msToSleepAtEachGameStep = 15;
    public static final int desiredFPS = 50;
    public static Timer gameSpeedAdjusterTimer;
    public static int survivalModeSeconds = 300;
    public static GameScheduler gameScheduler;
    public static GameMode gameMode = SURVIVAL;
    public static PageStatus pageStatus = MAIN;
    public static PlayerNumber playerNumber = ONE;

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
            gameScheduler = new DoublePlayerGameScheduler(LEVEL_NORMAL);
        } else {
            gameScheduler = new SinglePlayerGameScheduler(LEVEL_NORMAL);
        }
        gameScheduler.init();

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
     * Paint the gaming page
     */
    public void paintGame(Graphics g) {
    	aircraftList.forEach(aircraft -> aircraft.paint(g));
        interactantList.forEach(interactant -> interactant.paint(g));
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
            		PlayerChosePage.paint(g);
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
        while (gameScheduler.gameIsNotOver()) {
            synchronized (this) {
                // Periodically add new planes / bonuses
                gameScheduler.scheduleObjectInserts();

                // Move everything in the game one step forward
                background.step();
                aircraftList.forEach(BaseAircraft::step);
                interactantList.forEach(BaseRaidenObject::step);

                // Remove off screen objects from the global lists and fields
                aircraftList.removeIf(BaseRaidenObject::isOffScreen);
                interactantList.removeIf(BaseRaidenObject::isOffScreen);
                
                // Periodically print the score
                if(gameStep.intValue() % 100 == 0) {
                	System.out.println("player1: " + player1.calculateScore());
                	if(playerNumber == TWO)
                		System.out.println("player2: " + player2.calculateScore());
                }
            }
            repaint();
            sleep(msToSleepAtEachGameStep);

            gameStep.increment();
            if (gameMode==SURVIVAL && gameStep.intValue() >= desiredFPS * survivalModeSeconds) {
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
    	pageStatus = GAMING;
    	PageStatus flag = MAIN;
    	while (pageStatus != CLOSE) {
    		while (pageStatus == flag) {
    			sleep(msToSleepAtEachGameStep);
        		gameStep.increment();
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
        		PlayerChosePage.run();
        		break;
        	case GAMING:
        		System.out.println("((((((((((((");
        		runGame();
        		System.out.println("))))))))))))");
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