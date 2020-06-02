package main.world;

import main.raidenObjects.Background;
import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.BaseAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.*;
import maryb.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.awt.event.KeyEvent.*;
import static main.utils.GameLevel.LEVEL_NORMAL;
import static main.utils.GameMode.SURVIVAL;
import static main.utils.PageStatus.*;
import static main.utils.PlayerNumber.ONE;

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
    public static Background background = new Background();

    // These public static variables can be referenced by any class in the game.
    public static PlayerAircraft player1, player2;
    // Interactants + Aircrafts = All Game Elements
    // Note that every aircraft/interactant should be added to the following lists by the CALLER of its constructor,
    // not the constructor itself. Because in future two-player mode, we might need two sets of lists,
    // and appending every aircraft/interactant to the following lists might cause serious problems.
    public static Deque<BaseAircraft> aircraftList = new ConcurrentLinkedDeque<>();
    public static Deque<BaseRaidenObject> interactantList = new ConcurrentLinkedDeque<>();
    public static MutableInt gameStep = new MutableInt(0);
    public static RaidenKeyAdapter keyAdapter1 = new RaidenKeyAdapter(VK_LEFT, VK_RIGHT, VK_UP, VK_DOWN, VK_SLASH, VK_PERIOD);
    public static RaidenKeyAdapter keyAdapter2 = new RaidenKeyAdapter(VK_A, VK_D, VK_W, VK_S, VK_Z, VK_X);
    public static Random rand = new Random();
    public static Player musicPlayer = new Player();
    public static final int windowWidth = 480;
    public static final int windowHeight = 720;
    public static volatile int msToSleepAtEachGameStep = 15;
    public static final int desiredFPS = 50;
    public static Timer gameSpeedAdjusterTimer;
    public static GameMode gameMode = SURVIVAL;
    public static PageStatus pageStatus = GAMING;
    public static PlayerNumber playerNumber = ONE;
    public static GameScheduler gameScheduler;
    public static GameLevel gameLevel = LEVEL_NORMAL;
    public static int totalScore, totalCoin;

    public World() throws IOException, InterruptedException {
        setLayout(null);
        setBounds(0, 0, windowWidth, windowHeight);
        playBackGroundMusic(Paths.get("data", "bgm", "15. War Council.mp3").toString());
        // The volume control functions in class {@code Player} does not appear to work. DO NOT USE IT.
        // Instead, use {@Code VolumeController} in package utils.
        VolumeController.setVolume(0.08f);

        pageStatus = MAIN;
        pageStatus.getPage().run(this);
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
    @Override
    public void paint(Graphics g) {
        if (pageStatus != CLOSE) {
            try {
                pageStatus.getPage().paintComponent(g, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Remove components when pageStatus changes
     *
     * @author 杨芳源
     */
    public void changePageStatus(PageStatus other) {
        pageStatus.getPage().clean(this);
        if (other == CLOSE) {
            System.exit(0);
        }
        Thread thread = new Thread(() -> {
            try {
                other.getPage().run(this);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        pageStatus = other;
    }
    
    /**
	 * Add result to ranklist
	 * 
	 * @param score
	 * @param coin
	 * @param number: playerNumer
	 * @author 杨芳源
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void addResult(int score, int coin, PlayerNumber number) throws IOException {
		File file;
		if (number == ONE)
			file = Paths.get("data", "result", "1.txt").toFile();
		else
			file = Paths.get("data", "result", "2.txt").toFile();
		
		DataInputStream input = null;
		try {
			input= new DataInputStream(new FileInputStream(file));
		} catch (Exception e) {}
		
		int n = input.readInt();
		ArrayList<ResultPair> list = new ArrayList<ResultPair>();
		for (int i = 0; i < n; i++) {
			int s = input.readInt();
			int c = input.readInt();
			list.add(new ResultPair(s,c));
		}
		input.close();
		list.add(new ResultPair(score, coin));
		list.sort(new Comparator<ResultPair>() {
			public int compare(ResultPair a, ResultPair b) {
				if (a.score == b.score)
					return b.coin - a.coin;
				return b.score - a.score;
			}
		});
		n = n>7 ? 8 : n+1;
		System.out.print(n+" "+score+" "+coin);
		
		DataOutputStream output = null;
		try {
			output = new DataOutputStream(new FileOutputStream(file));
		} catch (Exception e) {}
		
		output.writeInt(n);
		for (int i = 0; i < n; i++) {
			output.writeInt(list.get(i).score);
			output.writeInt(list.get(i).coin);
		}
		output.close();
	}
}

