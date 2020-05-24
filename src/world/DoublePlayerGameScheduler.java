package world;

import raidenObjects.aircrafts.BlackHoleAircraft;
import raidenObjects.aircrafts.BumpingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.*;
import raidenObjects.bonus.*;
import utils.Faction;
import utils.GameLevel;
import utils.PlayerController;

import static world.World.*;

public class DoublePlayerGameScheduler implements GameScheduler {
    GameLevel level;

    public DoublePlayerGameScheduler(GameLevel level) {
        this.level = level;
    }

    @Override
    public void init() {
        player1 = new PlayerAircraft(windowWidth * .75f, windowHeight - 150,
                Faction.PLAYER1, PlayerController.KEYBOARD1);
        aircraftList.add(player1);
        player2 = new PlayerAircraft(windowWidth * .25f, windowHeight - 150,
                Faction.PLAYER2, PlayerController.KEYBOARD2);
        aircraftList.add(player2);
    }

    @Override
    public boolean gameIsNotOver() {
        return player1.isAlive() || player2.isAlive();
    }

    @Override
    public void scheduleObjectInserts() {
        // TODO: Verify the parameters based on game level
        if (gameStep.intValue() % getFrequency(700) == 37) {
            aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % getFrequency(600) == 198) {
            aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % getFrequency(900) == 100) {
            aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth / 2), 0));
        }
        if (gameStep.intValue() % getFrequency(900) == 300) {
            aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2.0f, 0));
        }
        if (gameStep.intValue() % getFrequency(600) == 200) {
            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % getFrequency(1000)== 432) {
            aircraftList.add(new BlackHoleAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % getFrequency(2000) == 965) {
            aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % getFrequency(1500) == 665) {
            aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % 50 == 0 && rand.nextInt(100) > 95) {
            interactantList.add(new InvincibleBonus(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % 20 == 0 && rand.nextInt(100) > 95) {
            interactantList.add(new CoinBonus(rand.nextInt(windowWidth), 0, rand.nextInt(3)));
        }
        if (gameStep.intValue() % 50 == 0 && rand.nextInt(100) > 95) {
            interactantList.add(new CureBonus(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % 100 == 0 && rand.nextInt(100) > 95) {
            interactantList.add(new MagnetBonus(rand.nextInt(windowWidth), 0));
        }
        if (gameStep.intValue() % 100 == 0 && rand.nextInt(100) > 97) {
            interactantList.add(new WeaponUpgradeBonus(rand.nextInt(windowWidth), 0, rand.nextInt(2)));
        }
        if (gameStep.intValue() % 100 == 0 && rand.nextInt(100) > 97) {
            interactantList.add(new SuperPowerBonus(rand.nextInt(windowWidth), 0));
        }
    }
    
    public int getFrequency(int ori_frequency){
    	if(gameStep.intValue() > 25000)
    		return (int) Math.floor(ori_frequency / 2.5);
    	else if(gameStep.intValue() > 20000)
    		return (int) Math.floor(ori_frequency / 2.0);
    	else if(gameStep.intValue() > 15000)
    		return (int) Math.floor(ori_frequency / 1.7);
    	else if(gameStep.intValue() > 10000)
    		return (int) Math.floor(ori_frequency / 1.45);
    	else if(gameStep.intValue() > 5000)
    		return (int) Math.floor(ori_frequency / 1.2);
    	else 
    		return ori_frequency;
    }
}
