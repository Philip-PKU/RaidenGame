package world.pages;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import utils.Faction;
import utils.PlayerController;
import world.GameScheduler;
import world.World;

import java.awt.*;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.GameMode.SURVIVAL;
import static utils.PageStatus.VICTORY;
import static utils.PlayerNumber.TWO;
import static world.World.*;

/**
 * Gaming Page handler.
 *
 * @author 鏉ㄨ姵婧�
 */
public class GamingPage implements Page {
	static int totalScore, totalCoin;
	
	/**
     * Paint the game state, including HP bar, number of coins and game points earned.
     *
     * @author 鏉ㄨ姵婧�
     */
    void paintGameState(Graphics g) {
        if (player1 != null) {
            g.setColor(Color.white);
            g.drawString("鐢熷懡锛�", (int) (windowWidth * 0.05), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player1.getHp() / player1.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("寰楀垎锛�" + player1.getScore(), (int) (windowWidth * 0.05), (int) (windowHeight * 0.09));

            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.05), (int) (windowHeight * 0.11), null);
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
            g.drawString("鐢熷懡锛�", (int) (windowWidth * 0.6), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player2.getHp() / player2.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("寰楀垎锛�" + player2.getScore(), (int) (windowWidth * 0.60), (int) (windowHeight * 0.09));

            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.60), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + player2.getCoin(),
                    (int) (windowWidth * 0.65), (int) (windowHeight * 0.13));
           
            g.drawImage(loadImage(Paths.get("data", "images", "SuperpowerBonusSmall.png").toFile()),
                    (int) (windowWidth * 0.75), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + player2.getAvailableSuperpowers(),
                    (int) (windowWidth * 0.8), (int) (windowHeight * 0.13));
        }
    }
    
    /**
     * Paint the Gaming page
     *
     * @author 钄¤緣瀹�
     */
    public void paint(Graphics g) {
        background.paint(g);
        aircraftList.forEach(aircraft -> {
            if (aircraft != null)
                aircraft.paint(g);
        });
        interactantList.forEach(interactant -> interactant.paint(g));
        // Game state info should be at the top of the game page, so we paint it last
        paintGameState(g);
    }
	
	/**
     * Run the game.
     *
     * @throws InterruptedException If sleep is interrupted.
     * @author 钄¤緣瀹�
     */
	public void run(World world) throws InterruptedException {
        System.out.println(playerNumber);
        world.addKeyListener(keyAdapter1);  // monitor the keyboard
        world.addKeyListener(keyAdapter2);  // monitor the keyboard
        world.requestFocus();
        gameScheduler = new GameScheduler(gameLevel, playerNumber);

        // Clear aircraft and interactant lists.
        aircraftList.clear();
        interactantList.clear();

        // The background music
        // TODO: change the bgm in different scenarios
        world.playBackGroundMusic(Paths.get("data", "bgm", "05. Unknown Pollution.mp3").toString());
        playSoundEffect(Paths.get("data", "sound effects", "OK Lets Go.mp3").toString());

        // Reset game step to zero.
        gameStep.setValue(0);

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

        totalScore = 0;
        totalCoin = 0;
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
            if (player1 != null && !player1.isAlive()) {
            	totalScore += player1.getScore();
            	totalScore += player1.getCoin();
                player1 = null;
        	}
            if (player2 != null && !player2.isAlive()) {
            	totalScore += player2.getScore();
            	totalCoin += player2.getCoin();
                player2 = null;
            }

            // Periodically print the score
            if (gameStep.intValue() % 100 == 0) {
                if (player1 != null)
                    System.out.println("player1: " + player1.calculateScore());
                if (playerNumber == TWO && player2 != null)
                    System.out.println("player2: " + player2.calculateScore());
            }
            world.repaint();
            sleep(msToSleepAtEachGameStep);

            gameStep.increment();
            if (gameMode == SURVIVAL && gameStep.intValue() >= desiredFPS * survivalModeSeconds) {
                System.out.println("Victory!");
                musicPlayer.stop();
                gameSpeedAdjusterTimer.stop();
                pageStatus = VICTORY;
                return;
            }
        }
        System.out.println("Game over");
        musicPlayer.stop();
        gameSpeedAdjusterTimer.stop();
        //pageStatus = END;
	}

	/**
	 * @param world
	 */
	public void clean(World world) {
		// TODO Auto-generated method stub
        world.revalidate();
        world.repaint();
	}
}
