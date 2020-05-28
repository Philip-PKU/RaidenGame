package raidenObjects.aircrafts.shootingAircrafts;

import launchControllers.*;
import motionControllers.KeyboardMotionController;
import raidenObjects.SuperpowerResidue;
import raidenObjects.aircrafts.BaseAircraft;
import raidenObjects.aircrafts.BlackholeAircraft;
import raidenObjects.bonus.InvincibleBonus;
import raidenObjects.weapons.BigPlayerBullet;
import raidenObjects.weapons.PlayerBeam;
import raidenObjects.weapons.StandardPlayerBullet;
import raidenObjects.weapons.TrackingPlayerBullet;
import utils.EffectCountdown;
import utils.Faction;
import utils.PlayerController;
import utils.keyAdapters.BaseRaidenKeyAdapter;

import java.io.File;
import java.nio.file.Paths;

import static java.lang.Math.*;
import static world.World.*;

public final class PlayerAircraft extends BaseShootingAircraft {
    private static int hitSizeX = 25, hitSizeY = 20;
    private final int superpowerCost = 200, coinScore = 10, highestWeaponLevel = 3;
    private static int staticMaxHp = 200;

    public static int UPDATE_WEAPON_NONE = 0, UPDATE_WEAPON_MULTI = 1, UPDATE_WEAPON_SINGLE = 2, UPDATE_WEAPON_TRACKING = 3;
    protected int coin = 0;
    protected int availableSuperpowers;
    protected BaseRaidenKeyAdapter keyAdapter;
    protected SimpleLaunchController<KeyboardSuperpowerLaunchCondition> superpowerLaunchController;
    protected SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController;
    protected SimpleLaunchController<? extends LaunchCondition> beamLaunchController;
    protected EffectCountdown invincibleCountdown = new EffectCountdown(), magnetCountdown = new EffectCountdown();

    class WeaponMultiLaunchController extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public WeaponMultiLaunchController(int weaponLevel) {
            super("PlayerAircraft shoots StandardPlayerBullet", weaponLevel);
            if (weaponLevel >= highestWeaponLevel) {
                setLaunchCondition(KeyboardWeaponLaunchConditionWrapper.createFromTwoStagedPeriodicLaunchCondition(1, 2, 200, keyAdapter));
            } else {
                setLaunchCondition(KeyboardWeaponLaunchConditionWrapper.createFromPeriodicLaunchCondition(2, keyAdapter));
            }
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
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 16));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -16));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 35));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -35));
                });
            }
            if (weaponLevel >= highestWeaponLevel) {
                if (getBeamLaunchController() == null) {
                    registerBeamLaunchController(new BeamLaunchController(), true);
                }
            }
        }
    }


    class WeaponSingleLaunchController extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public WeaponSingleLaunchController(int weaponLevel) {
            super("PlayerAircraft shoots BigPlayerBullet", weaponLevel);
            if (weaponLevel >= highestWeaponLevel) {
                getInvincibleCountdown().extendDurationBy(InvincibleBonus.getEffectiveGameSteps() * 2);
            }
            setLaunchCondition(KeyboardWeaponLaunchConditionWrapper.createFromPeriodicLaunchCondition(3, keyAdapter));

            if (weaponLevel == 0) {
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX(), getMinY(), getFaction(), signum(getSpeedX())));
                });
            } else if (weaponLevel == 1) {
                BigPlayerBullet.setStaticDamage(BigPlayerBullet.getStaticDamage() * 4 / 5);
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX() - 10, getMinY(), getFaction(), signum(getSpeedX())));
                    interactantList.add(new BigPlayerBullet(getX() + 10, getMinY(), getFaction(), signum(getSpeedX())));
                });
            } else if (weaponLevel >= 2) {
                BigPlayerBullet.setStaticDamage(BigPlayerBullet.getStaticDamage() * 3 / 4);
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX() - 10, getMinY(), getFaction(), signum(getSpeedX())));
                    interactantList.add(new BigPlayerBullet(getX() - 5, getMinY(), getFaction(), signum(getSpeedX())));
                    interactantList.add(new BigPlayerBullet(getX() + 5, getMinY(), getFaction(), -signum(getSpeedX())));
                    interactantList.add(new BigPlayerBullet(getX() + 10, getMinY(), getFaction(), -signum(getSpeedX())));
                });
            }
            if (getBeamLaunchController() != null) {
                getBeamLaunchController().deactivate();
            }
        }
    }

    class BeamLaunchController extends SimpleLaunchController<LaunchCondition> {
        public BeamLaunchController() {
            super("PlayerAircraft fires PlayerBeam");
            setLaunchCondition(KeyboardWeaponLaunchConditionWrapper.createFromPeriodicLaunchCondition(1, keyAdapter));
            setLaunchable(() -> {
                interactantList.add(new PlayerBeam(getX(), getMinY() + 10, getFaction()));
            });
        }
    }

    class SuperpowerLaunchController extends SimpleLaunchController<KeyboardSuperpowerLaunchCondition> {
        public SuperpowerLaunchController(int cooldown) {
            super("PlayerAircraft uses superpower");
            setLaunchCondition(new KeyboardSuperpowerLaunchCondition(cooldown, keyAdapter,
                    () -> getAvailableSuperpowers() > 0));
            setLaunchable(() -> {
                decrAvailableSuperpowers();
                interactantList.removeIf(i -> i.getFaction().isEnemyTo(getFaction()));
                for (BaseAircraft aircraft : aircraftList) {
                    if (aircraft.getFaction().isEnemyTo(getFaction()) && !(aircraft instanceof BlackholeAircraft)) {
                        aircraft.markAsDead();
                    }
                }
                interactantList.add(new SuperpowerResidue(getX(), max(0, getY() - 240), getFaction()));
            });
        }
    }

    class TrackingBulletLaunchController extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public TrackingBulletLaunchController(int weaponLevel) {
            super("PlayerAircraft shoots TrackingBullet", weaponLevel);
            setLaunchable(() -> {
                interactantList.add(new TrackingPlayerBullet(
                        getX() - 20, getMinY(), getFaction(), getEnemyToTrack(true), -50));
                interactantList.add(new TrackingPlayerBullet(
                        getX() + 20, getMinY(), getFaction(), getEnemyToTrack(false), 50));
            });
            if (weaponLevel == 0) {
                setLaunchCondition(new PeriodicLaunchCondition(50, 0));
            } else if (weaponLevel == 1) {
                setLaunchCondition(new PeriodicLaunchCondition(50, 0, 5, 2));

            } else if (weaponLevel == 2) {
                setLaunchCondition(new PeriodicLaunchCondition(50, 0, 5, 3));
            } else if (weaponLevel >= highestWeaponLevel) {
                setLaunchCondition(new TwoStagedPeriodicLaunchCondition(3, 0, 50, 0, 70));
            }
        }
    }

    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        super("Player0", x, y, 50, 40, owner,
                staticMaxHp, 0, 100, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        if (playerController == PlayerController.KEYBOARD1)
            keyAdapter = keyAdapter1;
        else if (playerController == PlayerController.KEYBOARD2)
            keyAdapter = keyAdapter2;
        this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        this.registerWeaponLaunchController(new WeaponMultiLaunchController(0), true);
        // this.registerWeaponLaunchController(new WeaponSingleLaunchController(0), true);
        this.registerSuperpowerLaunchController(new SuperpowerLaunchController(10), true);
    }

    public EffectCountdown getInvincibleCountdown() {
        return invincibleCountdown;
    }

    public EffectCountdown getMagnetCountdown() {
        return magnetCountdown;
    }

    public SimpleLaunchController<KeyboardSuperpowerLaunchCondition> getSuperPowerLaunchController() {
        return superpowerLaunchController;
    }

    public void registerSuperpowerLaunchController(SimpleLaunchController<KeyboardSuperpowerLaunchCondition> superpowerLaunchController, boolean activateNow) {
        this.superpowerLaunchController = superpowerLaunchController;
        if (activateNow)
            superpowerLaunchController.activate();
    }

    public SimpleLaunchControllerWithLevel<? extends LaunchCondition> getTrackingBulletLaunchController() {
        return trackingBulletLaunchController;
    }

    public void registerTrackingBulletLaunchController(SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController, boolean activateNow) {
        this.trackingBulletLaunchController = trackingBulletLaunchController;
        if (activateNow)
            trackingBulletLaunchController.activate();
    }

    public SimpleLaunchController<? extends LaunchCondition> getBeamLaunchController() {
        return beamLaunchController;
    }

    public void registerBeamLaunchController(SimpleLaunchController<? extends LaunchCondition> beamLaunchController, boolean activateNow) {
        this.beamLaunchController = beamLaunchController;
        if (activateNow)
            beamLaunchController.activate();
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

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        PlayerAircraft.staticMaxHp = staticMaxHp;
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
        becomeInvisible();
    }

    public void updateWeapon(int updateWeaponType) {
        if (updateWeaponType == UPDATE_WEAPON_NONE)
            return;  // No weapon updates to commit

        SimpleLaunchControllerWithLevel<? extends LaunchCondition> currentWeaponLaunchController = (SimpleLaunchControllerWithLevel<? extends LaunchCondition>) getWeaponLaunchController();
        if (updateWeaponType == UPDATE_WEAPON_MULTI) {
            this.registerWeaponLaunchController(
                    new WeaponMultiLaunchController(
                            currentWeaponLaunchController instanceof WeaponMultiLaunchController ?
                                    currentWeaponLaunchController.getLevel() + 1 : 0),
                    true);
        } else if (updateWeaponType == UPDATE_WEAPON_SINGLE) {
            this.registerWeaponLaunchController(
                    new WeaponSingleLaunchController(
                            currentWeaponLaunchController instanceof WeaponSingleLaunchController ?
                                    currentWeaponLaunchController.getLevel() + 1 : 0),
                    true);
        } else if (updateWeaponType == UPDATE_WEAPON_TRACKING) {
            SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController = (SimpleLaunchControllerWithLevel<? extends LaunchCondition>) getTrackingBulletLaunchController();
            this.registerTrackingBulletLaunchController(
                    new TrackingBulletLaunchController(
                            trackingBulletLaunchController == null ?
                                    0 : trackingBulletLaunchController.getLevel() + 1),
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
            if (getBeamLaunchController() != null) {
                getBeamLaunchController().launchIfPossible();
            }
        }
    }

    public BaseAircraft getEnemyToTrack(boolean left) {
        BaseAircraft result = null;
        float maxScore = Float.MIN_VALUE;
        for (BaseAircraft aircraft : aircraftList) {
            if (!aircraft.getFaction().isEnemyTo(getFaction()) || aircraft instanceof BlackholeAircraft)
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
