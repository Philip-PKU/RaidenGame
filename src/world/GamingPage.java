package world;

import static java.lang.Thread.sleep;
import static raidenObjects.BaseRaidenObject.loadImage;
import static utils.GameMode.*;
import static utils.PlayerNumber.*;

import raidenObjects.BaseRaidenObject;
import raidenObjects.aircrafts.BaseAircraft;
import utils.PageStatus;

import static world.World.*;
import static utils.PageStatus.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.nio.file.Paths;

/**
 * Gaming Page handler.
 *
 * @author �Դ
 */
public class GamingPage {
	
	/**
     * Paint the game state, including HP bar, number of coins and game points earned.
     *
     * @author �Դ
     */
    static void paintGameState(Graphics g) {
        if (player1 != null) {
            g.setColor(Color.white);
            g.drawString("������", (int) (windowWidth * 0.05), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player1.getHp() / player1.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("�÷֣�" + player1.getScore(), (int) (windowWidth * 0.05), (int) (windowHeight * 0.09));

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
            g.drawString("������", (int) (windowWidth * 0.6), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            g.fillRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * player2.getHp() / player2.getMaxHp()), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            g.drawString("�÷֣�" + player2.getScore(), (int) (windowWidth * 0.60), (int) (windowHeight * 0.09));

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
     * @author �̻���
     */
    public static void paint(Graphics g, World world) {
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
     * @author �̻���
     */
	public static void run(World world) throws InterruptedException {
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
        pageStatus = END;
	}

	/**
	 * @param world
	 */
	public static void clean(World world) {
		// TODO Auto-generated method stub
		
	}
}
