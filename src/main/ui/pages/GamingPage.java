package main.ui.pages;

import main.GameScheduler;
import main.World;
import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.BaseAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.Faction;
import main.utils.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static main.World.*;
import static main.raidenObjects.BaseRaidenObject.loadImage;
import static main.utils.GameMode.TIMER;
import static main.utils.PageStatus.END;
import static main.utils.PageStatus.VICTORY;
import static main.utils.PlayerNumber.TWO;

/**
 * Gaming Page handler.
 *
 * @author 杨芳源
 */
public class GamingPage implements Page {
	
	/**
     * Paint the game state, including HP bar, number of coins and game points earned.
     *
     * @author 杨芳源
     */
    void paintGameState(Graphics g) {
        if (player2 != null || playerNumber == TWO) {
            g.setColor(Color.white);
            g.drawString("生命：", (int) (windowWidth * 0.05), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            double hp = player2==null ? 0. : (double)player2.getHp() / player2.getMaxHp();
            g.fillRect((int) (windowWidth * 0.12), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * hp), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            int score = player2==null ? totalScore : player2.getScore();
            g.drawString("得分：" + score, (int) (windowWidth * 0.05), (int) (windowHeight * 0.09));
            
            int coin = player2==null ? totalCoin : player2.getCoin();
            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.05), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + coin, (int) (windowWidth * 0.1), (int) (windowHeight * 0.13));

            int bomb = player2==null ? 0 : player2.getAvailableSuperpowers();
            g.drawImage(loadImage(Paths.get("data", "images", "SuperpowerBonusSmall.png").toFile()),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + bomb,(int) (windowWidth * 0.25), (int) (windowHeight * 0.13));
        }
        if (player1 != null || playerNumber == TWO) {
        	g.setColor(Color.white);
            g.drawString("生命：", (int) (windowWidth * 0.60), (int) (windowHeight * 0.05));
            g.setColor(Color.red);
            g.drawRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2), (int) (windowHeight * 0.02));
            double hp = player1==null ? 0. : (double)player1.getHp() / player1.getMaxHp();
            g.fillRect((int) (windowWidth * 0.72), (int) (windowHeight * 0.035),
                    (int) (windowWidth * 0.2 * hp), (int) (windowHeight * 0.02));

            g.setColor(Color.white);
            int score = player1==null ? totalScore : player1.getScore();
            g.drawString("得分：" + score, (int) (windowWidth * 0.60), (int) (windowHeight * 0.09));
            
            int coin = player1==null ? totalCoin : player1.getCoin();
            g.drawImage(loadImage(Paths.get("data", "images", "CoinBonus20.png").toFile()),
                    (int) (windowWidth * 0.60), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + coin, (int) (windowWidth * 0.65), (int) (windowHeight * 0.13));

            int bomb = player1==null ? 0 : player1.getAvailableSuperpowers();
            g.drawImage(loadImage(Paths.get("data", "images", "SuperpowerBonusSmall.png").toFile()),
                    (int) (windowWidth * 0.75), (int) (windowHeight * 0.11), null);
            g.drawString("\u00D7" + bomb,(int) (windowWidth * 0.80), (int) (windowHeight * 0.13));
        }
    }
    
    /**
     * Paint the Gaming page
     *
     * @author 蔡辉宇
     */
    public void paintComponent(Graphics g, World world) {
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
     * @author 蔡辉宇
     */
	public void run(World world) throws InterruptedException {
        System.out.println(playerNumber);
        keyAdapter1.clearStates();
        keyAdapter2.clearStates();
        world.addKeyListener(keyAdapter1);  // monitor the keyboard
        world.addKeyListener(keyAdapter2);  // monitor the keyboard
        world.requestFocus();
        gameScheduler = new GameScheduler(gameLevel, playerNumber);

        // Timer to adjust game speed
        gameSpeedAdjusterTimer = new Timer(1000, new ActionListener() {
            int lastGameStep;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastGameStep != 0) {
                    int fps = gameStep.intValue() - lastGameStep;
                    float overheadPerStep = (1000 - fps * msToSleepAtEachGameStep) / (float) fps;
                    if (overheadPerStep < 0)
                        overheadPerStep = 0;
                    msToSleepAtEachGameStep = (int) ((1000f / desiredFPS) - overheadPerStep);
                    if (msToSleepAtEachGameStep < 0)
                        msToSleepAtEachGameStep = 0;
                }
                lastGameStep = gameStep.intValue();
            }
        });
        gameSpeedAdjusterTimer.setRepeats(true);

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
                if (gameMode == TIMER) {
                    System.out.println("Victory!");
                    if (player1 != null) {
                        totalScore += player1.getScore();
                        totalCoin += player1.getCoin() + player1.getAvailableSuperpowers() * PlayerAircraft.getSuperpowerCost();
                    }
                    if (player2 != null) {
                        totalScore += player2.getScore();
                        totalCoin += player2.getCoin() + player2.getAvailableSuperpowers() * PlayerAircraft.getSuperpowerCost();
                    }
                    musicPlayer.stop();
                    gameSpeedAdjusterTimer.stop();
                    world.changePageStatus(VICTORY);
                    return;
                }
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

            if (player1 != null && !player1.isAlive()) {
            	totalScore += player1.getScore();
            	totalCoin += player1.getCoin() + player1.getAvailableSuperpowers() * PlayerAircraft.getSuperpowerCost();
                player1 = null;
        	}
            if (player2 != null && !player2.isAlive()) {
            	totalScore += player2.getScore();
            	totalCoin += player2.getCoin() + player2.getAvailableSuperpowers() * PlayerAircraft.getSuperpowerCost();
                player2 = null;
            }

            world.repaint();
            sleep(msToSleepAtEachGameStep);

            gameStep.increment();
        }
        System.out.println("Game over");
        musicPlayer.stop();
        gameSpeedAdjusterTimer.stop();
        world.changePageStatus(END);
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
