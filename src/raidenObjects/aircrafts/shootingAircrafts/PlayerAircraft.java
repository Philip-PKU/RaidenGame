package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.KeyboardLaunchController;
import motionControllers.KeyboardMotionController;
import raidenObjects.weapons.bullets.StandardPlayerBullet;
import utils.BaseRaidenKeyAdapter;
import utils.Faction;
import utils.PlayerController;
import world.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.nio.file.Paths;

import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;

    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                100, 0, 100, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        BaseRaidenKeyAdapter keyAdapter = null;
        if (playerController == PlayerController.KEYBOARD1)
            keyAdapter = keyAdapter1;
        else if (playerController == PlayerController.KEYBOARD2)
            keyAdapter = keyAdapter2;
        this.setKeyAdapter(keyAdapter);
        this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        this.registerWeaponLaunchController(new KeyboardLaunchController(
                2, keyAdapter,  () -> {
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 0));
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), 8));
            interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getOwner(), -8));
        }));
        this.weaponLaunchController.activate();
    }

    @Override
    public int getHitSizeX() {
        return hitSizeX;
    }

    @Override
    public int getHitSizeY() {
        return hitSizeY;
    }

    @Override
    public File getImageFile() {
        if (!isAlive())
            return null;

        if (owner.isPlayer1()) {
            if (getInvincibleCountdown().isEffective()) {
                return Paths.get("data", "images", "Player0WithShield.png").toFile();
            } else {
                return Paths.get("data", "images", "Player0.png").toFile();
            }
        }
        else {
            if (getInvincibleCountdown().isEffective()) {
                return Paths.get("data", "images", "Player1WithShield.png").toFile();
            } else {
                return Paths.get("data", "images", "Player1.png").toFile();
            }
        }
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }
    
    /**
     * Paint the blood bar and scores.
     * @author 杨芳源
     */
    @Override 
    public void paint(Graphics g) {
    	super.paint(g);
    	//paint the blood bar and scores
    	
    	Font defaultFont = new Font("Dialog",Font.PLAIN,12);
    	//System.out.println(defaultFont);
    	if (this==World.player1) {
    		g.setColor(Color.white);
    		g.drawString("生命：", (int)(World.windowWidth*0.05), (int)(World.windowHeight*0.05));
    		g.setColor(Color.red);
    		g.drawRect((int)(World.windowWidth*0.12), (int)(World.windowHeight*0.035),
						(int)(World.windowWidth*0.2), (int)(World.windowHeight*0.02));
    		g.fillRect((int)(World.windowWidth*0.12), (int)(World.windowHeight*0.035), 
    					(int)(World.windowWidth*0.2*hp/maxHp), (int)(World.windowHeight*0.02));
    		
    		g.setColor(Color.white);
    		g.drawString("得分："+score, (int)(World.windowWidth*0.05), (int)(World.windowHeight*0.09));
    		
    		g.drawImage(loadImage(Paths.get("data", "images","CoinBonus20.png").toFile()), 
    					(int)(World.windowWidth*0.05), (int)(World.windowHeight*0.11), null);
    		//Font font = new Font("宋体",Font.BOLD,15);
    		//g.setFont(font);
    		g.drawString("\u00D7"+coin, (int)(World.windowWidth*0.1), (int)(World.windowHeight*0.13));
    	}
    	if (this==World.player2) {
    		g.setColor(Color.white);
    		//g.setFont(defaultFont);
    		g.drawString("生命：", (int)(World.windowWidth*0.6), (int)(World.windowHeight*0.05));
    		g.setColor(Color.red);
    		g.drawRect((int)(World.windowWidth*0.72), (int)(World.windowHeight*0.035),
						(int)(World.windowWidth*0.2), (int)(World.windowHeight*0.02));
    		g.fillRect((int)(World.windowWidth*0.72), (int)(World.windowHeight*0.035), 
    					(int)(World.windowWidth*0.2*hp/maxHp), (int)(World.windowHeight*0.02));
    		
    		g.setColor(Color.white);
    		g.drawString("得分："+score, (int)(World.windowWidth*0.60), (int)(World.windowHeight*0.09));
    		
    		g.drawImage(loadImage(Paths.get("data", "images","CoinBonus20.png").toFile()), 
    					(int)(World.windowWidth*0.60), (int)(World.windowHeight*0.11), null);
    		//Font font = new Font("宋体",Font.BOLD,15);
    		//g.setFont(font);
    		g.drawString("\u00D7"+coin, (int)(World.windowWidth*0.65), (int)(World.windowHeight*0.13));
    	}
    }
}
