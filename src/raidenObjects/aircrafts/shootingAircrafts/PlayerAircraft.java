package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.*;
import motionControllers.KeyboardMotionController;
import raidenObjects.SuperpowerResidue;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.BlackHoleAircraft;
import raidenObjects.weapons.BigPlayerBullet;
import raidenObjects.weapons.StandardPlayerBullet;
import raidenObjects.weapons.TrackingPlayerBullet;
import utils.EffectCountdown;
import utils.Faction;
import utils.PlayerController;
import utils.keyAdapters.BaseRaidenKeyAdapter;

import java.io.File;
import java.nio.file.Paths;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;
import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;
    private final int superpowerCost = 200, coinScore = 10;

    public static int UPDATE_WEAPON_NONE = 0, UPDATE_WEAPON_MULTI = 1, UPDATE_WEAPON_SINGLE = 2, UPDATE_WEAPON_TRACKING = 3;
    protected int coin = 0;
    protected int availableSuperpowers;
    protected BaseRaidenKeyAdapter keyAdapter;
    protected LaunchController superpowerLaunchController, trackingBulletLaunchController;
    protected EffectCountdown invincibleCountdown = new EffectCountdown(), magnetCountdown = new EffectCountdown();

    class WeaponMultiLaunchController extends LaunchControllerWithLevel {
        public WeaponMultiLaunchController(int weaponLevel) {
            super(weaponLevel);
            setLaunchEventScheduler(new KeyboardWeaponLaunchEventScheduler(2, keyAdapter));
            if (weaponLevel == 0) {
                this.setLaunchable(() -> {
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 0));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -8));
                });
            } else if (weaponLevel == 1) {
                this.setLaunchable(() -> {
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 0));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 5));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -5));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 10));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -10));
                });
            } else if (weaponLevel >= 2) {
                this.setLaunchable(() -> {
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 0));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 5));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -5));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 10));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -10));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 35));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -35));
                });
            }
        }
    }
    
    class WeaponSingleLaunchController extends LaunchControllerWithLevel {
        public WeaponSingleLaunchController(int weaponLevel) {
            super(weaponLevel);
            setLaunchEventScheduler(new KeyboardWeaponLaunchEventScheduler(3, keyAdapter));
            if (weaponLevel == 0) {
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX(), getMinY(), getFaction()));
                });
            } else if (weaponLevel == 1) {
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX() - 5, getMinY(), getFaction()));
                    interactantList.add(new BigPlayerBullet(getX() + 5, getMinY(), getFaction()));
                });
            } else if (weaponLevel >= 2) {
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX() - 10, getMinY(), getFaction()));
                    interactantList.add(new BigPlayerBullet(getX() - 5, getMinY(), getFaction()));
                    interactantList.add(new BigPlayerBullet(getX() + 5, getMinY(), getFaction()));
                    interactantList.add(new BigPlayerBullet(getX() + 10, getMinY(), getFaction()));
                });
            }
        }

    }

    class SuperpowerLaunchController extends LaunchController {
        public SuperpowerLaunchController(int cooldown) {
            setLaunchEventScheduler(new KeyboardSuperpowerLaunchEventScheduler(cooldown, keyAdapter,
                    () -> getAvailableSuperpowers() > 0));
            setLaunchable(() -> {
                decrAvailableSuperpowers();
                interactantList.removeIf(i -> i.getFaction().isEnemyTo(getFaction()));
                for (BaseAircraft aircraft : aircraftList) {
                    if (aircraft.getFaction().isEnemyTo(getFaction()) && !(aircraft instanceof BlackHoleAircraft)) {
                        aircraft.markAsDead();
                    }
                }
                interactantList.add(new SuperpowerResidue(getX(), getY() - 240, getFaction()));
            });
        }
    }

    class TrackingBulletLaunchController extends LaunchControllerWithLevel {
        public TrackingBulletLaunchController(int weaponLevel) {
            super(weaponLevel);
            setLaunchable(() -> {
                interactantList.add(new TrackingPlayerBullet(
                        getX() - 20, getMinY(), getFaction(), getEnemyToTrack(true), -50));
                interactantList.add(new TrackingPlayerBullet(
                        getX() + 20, getMinY(), getFaction(), getEnemyToTrack(false), 50));
            });
            if (weaponLevel == 0) {
                setLaunchEventScheduler(new PeriodicLaunchEventScheduler(30, 0));
            } else if (weaponLevel == 1) {
                setLaunchEventScheduler(new PeriodicLaunchEventScheduler(30, 0, 5, 2));

            } else if (weaponLevel >= 2) {
                setLaunchEventScheduler(new PeriodicLaunchEventScheduler(30, 0, 5, 3));
            }
        }
    }

    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                100, 0, 100, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        if (playerController == PlayerController.KEYBOARD1)
            keyAdapter = keyAdapter1;
        else if (playerController == PlayerController.KEYBOARD2)
            keyAdapter = keyAdapter2;
        this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        this.registerWeaponLaunchController(new WeaponMultiLaunchController(0), true);
        this.registerSuperpowerLaunchController(new SuperpowerLaunchController(10), true);
    }

    public EffectCountdown getInvincibleCountdown() {
        return invincibleCountdown;
    }

    public EffectCountdown getMagnetCountdown() {
        return magnetCountdown;
    }

    public LaunchController getSuperPowerLaunchController() {
        return superpowerLaunchController;
    }

    public void registerSuperpowerLaunchController(LaunchController superpowerLaunchController, boolean activateNow) {
        this.superpowerLaunchController = superpowerLaunchController;
        if (activateNow)
            superpowerLaunchController.activate();
    }

    public LaunchController getTrackingBulletLaunchController() {
        return trackingBulletLaunchController;
    }

    public void registerTrackingBulletLaunchController(LaunchController trackingBulletLaunchController, boolean activateNow) {
        this.trackingBulletLaunchController = trackingBulletLaunchController;
        if (activateNow)
            trackingBulletLaunchController.activate();
    }

    public int calculateScore() {
        return score + coin * coinScore + availableSuperpowers * superpowerCost * coinScore;
    }

    public int getCoin() {
        return coin;
    }

    public void receiveCoin(int coin) {
        this.coin += coin;
        if (this.coin >= superpowerCost) {
            this.coin -= superpowerCost;
            incrAvailableSuperpowers();
        } else {
            System.out.println("Coin: " + this.coin);
        }
    }

    public int getAvailableSuperpowers() {
        return availableSuperpowers;
    }

    public void incrAvailableSuperpowers() {
        ++availableSuperpowers;
        System.out.println("Available superpowers: " + availableSuperpowers);
    }

    public void decrAvailableSuperpowers() {
        --availableSuperpowers;
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
        String prefix = "Player" + (faction.isPlayer1() ? 1 : 2);
        if (getInvincibleCountdown().isEffective()) {
            int remainingSteps = getInvincibleCountdown().getRemainingSteps();
            // If remainingSteps is low (InvincibleBonus will expire in 1s), blink the shield so that player will know
            if (remainingSteps >= desiredFPS || (remainingSteps / (desiredFPS / 10)) % 2 == 1) {
                return Paths.get("data", "images", prefix + "WithShield.png").toFile();
            }
        }
        return Paths.get("data", "images", prefix + ".png").toFile();
    }

    @Override
    public void markAsDead() {
        super.markAsDead();
        getOffScreen();
    }

    public void updateWeapon(int updateWeaponType) {
        if (updateWeaponType == UPDATE_WEAPON_NONE)
            return;  // No weapon updates to commit

        if (updateWeaponType == UPDATE_WEAPON_MULTI) {
            LaunchControllerWithLevel currentLaunchController = (LaunchControllerWithLevel) getWeaponLaunchController();
            this.registerWeaponLaunchController(
                    new WeaponMultiLaunchController(
                            currentLaunchController instanceof WeaponMultiLaunchController ?
                            currentLaunchController.getWeaponLevel() + 1 : 0),
                    true);
        } else if (updateWeaponType == UPDATE_WEAPON_SINGLE) {
            LaunchControllerWithLevel currentLaunchController = (LaunchControllerWithLevel) getWeaponLaunchController();
            this.registerWeaponLaunchController(
                    new WeaponSingleLaunchController(
                            currentLaunchController instanceof WeaponSingleLaunchController ?
                            currentLaunchController.getWeaponLevel() + 1 : 0),
                    true);
        } else if (updateWeaponType == UPDATE_WEAPON_TRACKING) {
            LaunchControllerWithLevel currentLaunchController = (LaunchControllerWithLevel) getTrackingBulletLaunchController();
            this.registerTrackingBulletLaunchController(
                    new TrackingBulletLaunchController(
                            currentLaunchController == null ?
                            0 : currentLaunchController.getWeaponLevel() + 1),
                    true);
        }
    }

    @Override
    public void step() {
        super.step();
        if (isAlive()) {
            invincibleCountdown.step();
            magnetCountdown.step();
            getSuperPowerLaunchController().launchIfPossible();
            if (getTrackingBulletLaunchController() != null) {
                getTrackingBulletLaunchController().launchIfPossible();
            }
        }
    }

    public BaseAircraft getEnemyToTrack(boolean left) {
        BaseAircraft result = null;
        float maxScore = Float.MIN_VALUE;
        for (BaseAircraft aircraft : aircraftList) {
            if (!aircraft.getFaction().isEnemyTo(getFaction()) || aircraft instanceof BlackHoleAircraft)
                continue;

            float score = 10 * sqrt(aircraft.getHp()) +                     // Favors high HP aircrafts
                    -min(windowWidth * 0.75f, this.distTo(aircraft)) +      // Favors aircrafts close to player
                    aircraft.getY() >= getY() - 100 ? 200 : 0;              // Favors aircrafts close to bottom
            if (left) {
                if (aircraft.getX() < getX() - windowWidth / 12f) {
                    score += 100;                                           // Favors aircrafts to the left of player
                }
            } else {
                if (aircraft.getX() > getX() + windowWidth / 12f) {
                    score += 100;                                           // Favors aircrafts to the right of player
                }
            }
            if (score > maxScore) {
                maxScore = score;
                result = aircraft;
            }
        }
        return result;
    }
}
