package main.raidenObjects.aircrafts.shootingAircrafts;

import main.launchControllers.*;
import main.motionControllers.KeyboardMotionController;
import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.SuperpowerResidue;
import main.raidenObjects.aircrafts.BaseAircraft;
import main.raidenObjects.aircrafts.BlackholeAircraft;
import main.raidenObjects.bonus.InvincibleBonus;
import main.raidenObjects.bonus.SuperPowerBonus;
import main.raidenObjects.weapons.BigPlayerBullet;
import main.raidenObjects.weapons.PlayerBeam;
import main.raidenObjects.weapons.StandardPlayerBullet;
import main.raidenObjects.weapons.TrackingPlayerBullet;
import main.ui.world.World;
import main.utils.*;

import java.io.File;
import java.nio.file.Paths;

import static java.lang.Math.*;
import static main.raidenObjects.bonus.WeaponUpgradeBonus.*;
import static main.ui.world.World.*;

/**
 * PlayerAircraft. A mobile, versatile aircraft with little strength but great potential.
 *
 * @author 蔡辉宇 张哲瑞 唐宇豪
 */
public final class PlayerAircraft extends BaseShootingAircraft {
    /**
     * X hitSize of the {@link PlayerAircraft}.
     * It is smaller that the aircraft's {@link BaseRaidenObject#imgSizeX} to reduce unwanted collision,
     * which is judged by rectangle-based algorithms.
     * @see main.raidenObjects.BaseRaidenObject#hasHit(BaseRaidenObject)
     */
    private static int hitSizeX = 25;
    /**
     * Y hitSize of the {@link PlayerAircraft}.
     * It is smaller that the aircraft's {@link BaseRaidenObject#imgSizeY} to reduce unwanted collision,
     * which is judged by rectangle-based algorithms.
     * @see main.raidenObjects.BaseRaidenObject#hasHit(BaseRaidenObject)
     */
    private static int hitSizeY = 20;
    private static int superpowerCost = 200, coinScore = 10, highestWeaponLevel = 3;
    private static int defaultMaxHp = 200, staticMaxHp = defaultMaxHp;

    protected int coin = 0;
    protected int availableSuperpowers;
    protected RaidenKeyAdapter keyAdapter;
    protected SimpleLaunchController<KeyboardSuperpowerLaunchCondition> superpowerLaunchController;
    protected SimpleLaunchControllerWithLevel<? extends LaunchCondition> trackingBulletLaunchController;
    protected SimpleLaunchController<? extends LaunchCondition> beamLaunchController;
    /**
     * An {@link EffectCountdown} object controlling the invincible effect.
     * This effect protects the {@link PlayerAircraft} from all weapon damages for several seconds.
     * If the player bumps into an enemy aircraft, the shield would weaken and become ineffective
     * in a short period of time.
     */
    protected EffectCountdown invincibleCountdown = new EffectCountdown();
    /**
     * An {@link EffectCountdown} object controlling the magnet effect.
     * This effect attracts all bonuses except {@link main.raidenObjects.bonus.WeaponUpgradeBonus} to this aircraft.
     * The effect will disable automatically in a few seconds.
     */
    protected EffectCountdown magnetCountdown = new EffectCountdown();

    /**
     * Launch Controller of {@link StandardPlayerBullet}.
     * <p>
     * Level 0: Launches 3 {@link StandardPlayerBullet}s each time, covering 16 degrees.
     * Level 1: Launches 5 {@link StandardPlayerBullet}s each time, covering 20 degrees.
     * Level 2: Launches 7 {@link StandardPlayerBullet}s each time, covering 70 degrees.
     * Level 3: Everything in level 2 plus an extra {@link PlayerBeam} that penetrates everything ahead. The beam yields
     *          double damage in the first few seconds.
     * </p>
     *
     * @see StandardPlayerBullet
     *
     * @author 唐宇豪 蔡辉宇
     */
    public class StandardBulletLaunchScheduler extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public StandardBulletLaunchScheduler(int weaponLevel) {
            super("PlayerAircraft shoots StandardPlayerBullet", weaponLevel);
            setLaunchCondition(KeyboardWeaponLaunchCondition.createFromPeriodicLaunchCondition(2, keyAdapter));
            if (weaponLevel == 0) {
                StandardPlayerBullet.setStaticDamage(StandardPlayerBullet.getDefaultDamage());
                this.setLaunchable(() -> {
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 0));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -8));
                });
            } else if (weaponLevel == 1) {
                StandardPlayerBullet.setStaticDamage(StandardPlayerBullet.getDefaultDamage());
                this.setLaunchable(() -> {
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 0));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -8));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), 16));
                    interactantList.add(new StandardPlayerBullet(getX(), getMinY(), getFaction(), -16));
                });
            } else if (weaponLevel >= 2) {
                StandardPlayerBullet.setStaticDamage(StandardPlayerBullet.getDefaultDamage()/5*6);
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

    /**
     * Launch Controller for {@link BigPlayerBullet}.
     * <p>
     * Level 0: Launches 1 {@link BigPlayerBullet} each time.
     * Level 1: Launches 2 {@link BigPlayerBullet} each time. The bullets have 80% strength of that in Level 0.
     * Level 2: Launches 4 {@link BigPlayerBullet} each time. The bullets have 60% strength of that in Level 0.
     * Level 3: Everything in level 2 plus an extended {@link InvincibleBonus} that allows player to yield insane damage
     *          while avoid being harmed.
     * </p>
     *
     * @see BigPlayerBullet
     *
     * @author 唐宇豪 蔡辉宇
     */
    public class BigBulletLaunchController extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public BigBulletLaunchController(int weaponLevel) {
            super("PlayerAircraft shoots BigPlayerBullet", weaponLevel);
            if (weaponLevel >= highestWeaponLevel) {
                getInvincibleCountdown().extendDurationBy(InvincibleBonus.getEffectiveGameSteps() * 2);
            }
            setLaunchCondition(KeyboardWeaponLaunchCondition.createFromPeriodicLaunchCondition(3, keyAdapter));

            if (weaponLevel == 0) {
                BigPlayerBullet.setStaticDamage(BigPlayerBullet.getDefaultDamage());
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX(), getMinY(), getFaction(), signum(getSpeedX())));
                });
            } else if (weaponLevel == 1) {
                BigPlayerBullet.setStaticDamage(BigPlayerBullet.getDefaultDamage() * 4 / 5);
                this.setLaunchable(() -> {
                    interactantList.add(new BigPlayerBullet(getX() - 10, getMinY(), getFaction(), signum(getSpeedX())));
                    interactantList.add(new BigPlayerBullet(getX() + 10, getMinY(), getFaction(), signum(getSpeedX())));
                });
            } else if (weaponLevel >= 2) {
                BigPlayerBullet.setStaticDamage(BigPlayerBullet.getDefaultDamage() * 3 / 4);
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

    /**
     * Launch Controller of {@link PlayerBeam}.
     *
     * @see PlayerBeam
     *
     * @author 蔡辉宇
     */
    public class BeamLaunchController extends SimpleLaunchController<LaunchCondition> {
        public BeamLaunchController() {
            super("PlayerAircraft fires PlayerBeam");
            PlayerBeam.setStaticDamage(PlayerBeam.getStaticDamage() * 2);
            setLaunchCondition(KeyboardWeaponLaunchCondition.createFromTwoStagedPeriodicLaunchCondition(1, 1, 150, keyAdapter,
                    () -> PlayerBeam.setStaticDamage(PlayerBeam.getStaticDamage() / 2)));
            setLaunchable(() -> {
                interactantList.add(new PlayerBeam(getX(), getMinY() + 10, getFaction()));
            });
        }
    }

    /**
     * Launch Controller of {@link PlayerAircraft}'s superpower. The ultimate weapon that brings annihilation
     * to the enemy.
     *
     * @see TrackingPlayerBullet
     *
     * @author 唐宇豪 蔡辉宇
     */
    public class SuperpowerLaunchController extends SimpleLaunchController<KeyboardSuperpowerLaunchCondition> {
        public SuperpowerLaunchController(int cooldown) {
            super("PlayerAircraft uses superpower");
            setLaunchCondition(new KeyboardSuperpowerLaunchCondition(cooldown, keyAdapter,
                    () -> getAvailableSuperpowers() > 0));
            setLaunchable(() -> {
                decrAvailableSuperpowers();
                World.playSoundEffect(Paths.get("data", "sound effects", "Superpower.mp3").toString());
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

    /**
     * Launch Controller for {@link TrackingPlayerBullet}.
     * <p>
     * Level 0: Launches 1 {@link TrackingPlayerBullet} each time.
     * Level 1: Launches 2 {@link TrackingPlayerBullet}s each time.
     * Level 2: Launches 3 {@link TrackingPlayerBullet}s each time.
     * Level 3: Everything in level 2 plus extra 70 swiftly-fired {@link TrackingPlayerBullet}s.
     * </p>
     */
    public class TrackingBulletLaunchController extends SimpleLaunchControllerWithLevel<LaunchCondition> {
        public TrackingBulletLaunchController(int weaponLevel) {
            super("PlayerAircraft shoots TrackingBullet", weaponLevel);
            setLaunchable(() -> {
                interactantList.add(new TrackingPlayerBullet(
                        getX() - 20, getMinY(), getFaction(), getEnemyToTrack(true), -60));
                interactantList.add(new TrackingPlayerBullet(
                        getX() + 20, getMinY(), getFaction(), getEnemyToTrack(false), 60));
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

    /**
     * Constructor.
     *
     * @param owner            Owner of this aircraft.
     * @param playerController Controller of this aircraft. Used to determine motion and weapon controllers.
     */
    public PlayerAircraft(Faction owner, PlayerController playerController) {
        super("Player0",50, 40, owner,
                staticMaxHp, 0, 100, 0);
        if (!owner.isPlayer())
            throw new RuntimeException("Invalid owner: player must be owned by either Player1 or Player2.");
        if (playerController == PlayerController.KEYBOARD1) {
            keyAdapter = keyAdapter1;
    	}
        else if (playerController == PlayerController.KEYBOARD2)
            keyAdapter = keyAdapter2;
        this.registerMotionController(new KeyboardMotionController(keyAdapter, 5));
        this.registerWeaponLaunchController(new StandardBulletLaunchScheduler(0), true);
        // this.registerWeaponLaunchController(new WeaponSingleLaunchController(0), true);
        this.registerSuperpowerLaunchController(new SuperpowerLaunchController(10), true);
    }

    public PlayerAircraft(float x, float y, Faction owner, PlayerController playerController) {
        this(owner, playerController);
        setX(x);
        setY(y);
    }

    public PlayerAircraft(InitLocation initLocation, Faction owner, PlayerController playerController) {
        this(owner, playerController);
        initXFromLocation(initLocation);
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

    /**
     * Calculate score of the aircraft by the equation
     * {@link #score}
     *     + {@link #coin} * {@link #coinScore}
     *     + {@link #availableSuperpowers} * {@link #superpowerCost} * {@link #coinScore}.
     *
     * @return Current score of the aircraft.
     */
    public int calculateScore() {
        return score + coin * coinScore + availableSuperpowers * superpowerCost * coinScore;
    }

    public int getCoin() {
        return coin;
    }

    /**
     * Receive {@code coin} coins. If current coin number exceeds {@link #superpowerCost}, clear them and add an
     * available superpower to the aircraft.
     * @param coin Number of coins to be received.
     */
    public void receiveCoin(int coin) {
        this.coin += coin;
        if (this.coin >= superpowerCost) {
            this.coin -= superpowerCost;
            new SuperPowerBonus().bonus(this);
        } else {
            System.out.println("Coin: " + this.coin);
        }
    }

    public static int getSuperpowerCost() {
        return superpowerCost;
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

    public static int getDefaultMaxHp() {
        return defaultMaxHp;
    }

    public static int getStaticMaxHp() {
        return staticMaxHp;
    }

    public static void setStaticMaxHp(int staticMaxHp) {
        PlayerAircraft.staticMaxHp = staticMaxHp;
    }

    /**
     * Return the image file.
     * @return A File object representing current image of this aircraft.
     */
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

    /**
     * Mark this aircraft as dead.
     * Unfortunately we don't have visual effect for {@link PlayerAircraft},
     * so we make it disappear from the screen immediately.
     */
    @Override
    public void markAsDead() {
        super.markAsDead();
        becomeInvisible();
    }

    /**
     * Update the aircraft's weapon system after it has received a weapon bonus.
     * @param updateWeaponType Type of received weapon bonus.
     */
    public void updateWeapon(int updateWeaponType) {
        if (updateWeaponType == UPDATE_WEAPON_NONE)
            return;  // No weapon updates to commit

        SimpleLaunchControllerWithLevel<? extends LaunchCondition> currentWeaponLaunchController = (SimpleLaunchControllerWithLevel<? extends LaunchCondition>) getWeaponLaunchController();
        if (updateWeaponType == UPDATE_WEAPON_STANDARD) {
            this.registerWeaponLaunchController(
                    new StandardBulletLaunchScheduler(
                            currentWeaponLaunchController instanceof StandardBulletLaunchScheduler ?
                                    currentWeaponLaunchController.getLevel() + 1 : 0),
                    true);
        } else if (updateWeaponType == UPDATE_WEAPON_BIG) {
            this.registerWeaponLaunchController(
                    new BigBulletLaunchController(
                            currentWeaponLaunchController instanceof BigBulletLaunchController ?
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

    /**
     * Get the enemy to track down using an evaluation function.
     * @param left Should we find the enemy to the left or right of this aircraft?
     * @return The enemy chosen (according to the evaluation function) which will be tracked down by a {@link TrackingPlayerBullet}.
     */
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
