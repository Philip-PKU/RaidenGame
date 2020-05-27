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
    static Background background;

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
            		HelpPage.paint(g,this);
            		break;
            	case RANKLIST:
            		RanklistPage.paint(g, this);
            		break;
            	case MODECHOSE:
            		ModeChosePage.paint(g, this);
            		break;
            	case PLAYERCHOSE:
            		PlayerChosePage.paint(g, this);
            		break;
            	case GAMING:
            		GamingPage.paint(g, this);;
            		break;
            	case VICTORY:
            		VictoryPage.paint(g, this);
            		break;
            	case END:
            		EndPage.paint(g, this);
            		break;
            	default:
            }
        }
    }
    
    
    /**
     * Remove components when pageStatus changes
     * 
     * @author 杨芳源
     */
    void clean(PageStatus flag) {
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
    		ModeChosePage.clean(this);
    		break;
    	case GAMING:
    		GamingPage.clean(this);
    		break;
    	case VICTORY:
    		VictoryPage.clean(this);
    		break;
    	case END:
    		EndPage.clean(this);
    		break;
    	default:
		}
    }
    
    /**
     * Run the handler program for the next page.
     * 
     * @author 杨芳源
     * @throws InterruptedException 
     */
    void runPageHandler(PageStatus flag) throws InterruptedException {
    	switch (flag){
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
    		GamingPage.run(this);
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
    		System.out.print(flag);
    		System.out.println(pageStatus);
    		
    		clean(flag);
    		
    		flag = pageStatus;

    		repaint();
    		runPageHandler(pageStatus);
    	}
    }
}