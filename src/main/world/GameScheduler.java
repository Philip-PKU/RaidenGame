package main.world;

import main.launchControllers.ConstAccelerationLaunchCondition;
import main.launchControllers.PeriodicStochasticLaunchCondition;
import main.launchControllers.SimpleLaunchController;
import main.raidenObjects.BaseRaidenObject;
import main.raidenObjects.aircrafts.BlackholeAircraft;
import main.raidenObjects.aircrafts.BumpingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.BarbetteAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.BigShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import main.raidenObjects.aircrafts.shootingAircrafts.SmallShootingAircraft;
import main.raidenObjects.bonus.*;
import main.utils.GameLevel;
import main.utils.PlayerNumber;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;
import static main.raidenObjects.bonus.CoinBonus.*;
import static main.utils.InitLocation.*;
import static main.world.World.*;

/**
 * Manages all {@link BaseRaidenObject}s in the game.
 *
 * @author 蔡辉宇 张哲瑞
 */
public class GameScheduler {
    static final int EASY = 0, NORMAL = 1, HARD = 2, INSANE = 3;
    int gameLevel;
    List<SimpleLaunchController<PeriodicStochasticLaunchCondition>> aircraftHpControllers;
    List<SimpleLaunchController<ConstAccelerationLaunchCondition>> aircraftLaunchControllers;
    List<SimpleLaunchController<PeriodicStochasticLaunchCondition>> bonusLaunchControllers;

    public GameScheduler(GameLevel gameLevel, PlayerNumber playerNumber) {
        this.gameLevel = gameLevel.ordinal() + playerNumber.ordinal();

        // Reset Max HPs
        SmallShootingAircraft.setStaticMaxHp(SmallShootingAircraft.getDefaultMaxHp());
        MiddleShootingAircraft.setStaticMaxHp(MiddleShootingAircraft.getDefaultMaxHp());
        BigShootingAircraft.setStaticMaxHp(BigShootingAircraft.getDefaultMaxHp());
        BarbetteAircraft.setStaticMaxHp(BarbetteAircraft.getDefaultMaxHp());

        aircraftHpControllers = Arrays.asList(
                new SimpleLaunchController<>(
                        "SmallShootingAircraft increases HP",
                        new PeriodicStochasticLaunchCondition(2000, 1000, 0.7f),
                        () -> SmallShootingAircraft.setStaticMaxHp(min(300, (int) (SmallShootingAircraft.getStaticMaxHp() * 1.4f)))
                ),
                new SimpleLaunchController<>(
                        "MiddleShootingAircraft increases HP",
                        new PeriodicStochasticLaunchCondition(2000, 1500, 0.6f),
                        () -> MiddleShootingAircraft.setStaticMaxHp(min(400, (int) (MiddleShootingAircraft.getStaticMaxHp() * 1.3f)))
                ),
                new SimpleLaunchController<>(
                        "BigShootingAircraft increases HP",
                        new PeriodicStochasticLaunchCondition(2000, 1800, 0.5f),
                        () -> BigShootingAircraft.setStaticMaxHp(min(1200, (int) (BigShootingAircraft.getStaticMaxHp() * 1.25f)))
                ),
                new SimpleLaunchController<>(
                        "BarbetteAircraft increases HP",
                        new PeriodicStochasticLaunchCondition(3000, 2500, 0.4f),
                        () -> BarbetteAircraft.setStaticMaxHp(min(1500, (int) (BarbetteAircraft.getStaticMaxHp() * 1.2f)))
                )
        );
        aircraftHpControllers.forEach(hpController -> {
            hpController.getLaunchCondition().scaleByGameLevel(gameLevel);
            hpController.activate();
        });

        aircraftLaunchControllers = Arrays.asList(
                new SimpleLaunchController<>(
                        "New BumpingAircraft",
                        new ConstAccelerationLaunchCondition(400, 300,
                                0.1f, 0.03f, 0.8f),
                        () -> aircraftList.add(new BumpingAircraft(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New SmallShootingAircraft",
                        new ConstAccelerationLaunchCondition(700, 40,
                                0.05f, 0.015f, 0.6f),
                        () -> aircraftList.add(new SmallShootingAircraft(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New MiddleShootingAircraft",
                        new ConstAccelerationLaunchCondition(700, 198,
                                0.08f, 0.01f, 0.5f),
                        () -> aircraftList.add(new MiddleShootingAircraft(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New BigShootingAircraft ×2",
                        new ConstAccelerationLaunchCondition(1000, 600,
                                0.04f, 0.01f, 0.25f),
                        () -> {
                            aircraftList.add(new BigShootingAircraft(LOC_LEFT));
                            aircraftList.add(new BigShootingAircraft(LOC_RIGHT));
                        }
                ),
                new SimpleLaunchController<>(
                        "New BlackholeAircraft",
                        new ConstAccelerationLaunchCondition(1800, 50, 600,
                                450, 400,
                                0.02f, 0.02f, 0.15f),
                        () -> aircraftList.add(new BlackholeAircraft(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New BarbetteAircraft",
                        new ConstAccelerationLaunchCondition(2000, 100, 800,
                                700, 500,
                                0.02f, 0.015f, 0.15f),
                        () -> aircraftList.add(new BarbetteAircraft(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New heavy wave: BumpingAircraft, BigShootingAircraft, BarbetteAircraft",
                        new ConstAccelerationLaunchCondition(3500, 2400,
                                0.02f, 0.03f, 0.15f),
                        () -> {
                            if (this.gameLevel >= HARD) {
                                aircraftList.add(new BarbetteAircraft(LOC_LEFT));
                                aircraftList.add(new BarbetteAircraft(LOC_RIGHT));
                            } else {
                                aircraftList.add(new BarbetteAircraft(LOC_RANDOM));
                            }
                            aircraftList.add(new BumpingAircraft(LOC_RANDOM));
                            aircraftList.add(new BumpingAircraft(LOC_RANDOM));
                            aircraftList.add(new BigShootingAircraft(LOC_RANDOM));
                        }
                ),
                new SimpleLaunchController<>(
                        "New light wave: SmallShootingAircraft, MiddleShootingAircraft",
                        new ConstAccelerationLaunchCondition(3000, 1500,
                                0.02f, 0.03f, 0.15f),
                        () -> {
                            aircraftList.add(new SmallShootingAircraft(LOC_LEFT));
                            aircraftList.add(new SmallShootingAircraft(LOC_RANDOM));
                            aircraftList.add(new SmallShootingAircraft(LOC_RIGHT));
                            aircraftList.add(new MiddleShootingAircraft(LOC_LEFT));
                            aircraftList.add(new MiddleShootingAircraft(LOC_RIGHT));
                            if (this.gameLevel >= HARD) {
                                aircraftList.add(new MiddleShootingAircraft(LOC_RANDOM));
                                aircraftList.add(new BlackholeAircraft(LOC_RANDOM));
                            }
                        }
                ),
                new SimpleLaunchController<>(
                        "New mixed wave: BumpingAircraft, MiddleShootingAircraft, BarbetteAircraft, BlackholeAircraft",
                        new ConstAccelerationLaunchCondition(3800, 3000,
                                0.02f, 0.01f, 0.1f),
                        () -> {
                            aircraftList.add(new BlackholeAircraft(LOC_RANDOM));
                            aircraftList.add(new BarbetteAircraft(LOC_LEFT));
                            aircraftList.add(new BarbetteAircraft(LOC_RIGHT));
                            aircraftList.add(new BumpingAircraft(LOC_LEFT));
                            aircraftList.add(new BumpingAircraft(LOC_RANDOM));
                            aircraftList.add(new BumpingAircraft(LOC_RIGHT));
                            aircraftList.add(new MiddleShootingAircraft(LOC_LEFT));
                            aircraftList.add(new MiddleShootingAircraft(LOC_RIGHT));
                        }
                )
        );
        aircraftLaunchControllers.forEach(launchController -> {
            launchController.getLaunchCondition().scaleByGameLevel(gameLevel);
            launchController.activate();
        });

        bonusLaunchControllers = Arrays.asList(
                new SimpleLaunchController<>(
                        "New small CoinBonus",
                        new PeriodicStochasticLaunchCondition(100, 10, 0.05f),
                        () -> interactantList.add(new CoinBonus(LOC_RANDOM, COIN_SMALL))
                ),
                new SimpleLaunchController<>(
                        "New medium CoinBonus",
                        new PeriodicStochasticLaunchCondition(100, 880, 0.03f),
                        () -> interactantList.add(new CoinBonus(LOC_RANDOM, COIN_MEDIUM))
                ),
                new SimpleLaunchController<>(
                        "New big CoinBonus",
                        new PeriodicStochasticLaunchCondition(100, 340, 0.02f),
                        () -> interactantList.add(new CoinBonus(LOC_RANDOM, COIN_BIG))
                ),
                new SimpleLaunchController<>(
                        "New InvincibleBonus",
                        new PeriodicStochasticLaunchCondition(1000, 450, 0.35f),
                        () -> interactantList.add(new InvincibleBonus(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New CureBonus",
                        new PeriodicStochasticLaunchCondition(1000, 900, 0.25f),
                        () -> interactantList.add(new CureBonus(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New MagnetBonus",
                        new PeriodicStochasticLaunchCondition(800, 250, 0.25f),
                        () -> interactantList.add(new MagnetBonus(LOC_RANDOM))
                ),
                new SimpleLaunchController<>(
                        "New WeaponUpgradeBonus",
                        new PeriodicStochasticLaunchCondition(3500, 600, 1f),
                        () -> interactantList.add(new WeaponUpgradeBonus(LOC_RANDOM))
                )
        );
        bonusLaunchControllers.forEach(launchController -> {
            launchController.getLaunchCondition().scaleByGameLevel(gameLevel);
            launchController.activate();
        });
    }

    public void scheduleObjectInserts() {
        aircraftHpControllers.forEach(SimpleLaunchController::launchIfPossible);
        aircraftLaunchControllers.forEach(SimpleLaunchController::launchIfPossible);
        bonusLaunchControllers.forEach(SimpleLaunchController::launchIfPossible);
    }

    public static void main(String[] args) {
        GameScheduler gameScheduler = new GameScheduler(GameLevel.LEVEL_NORMAL, PlayerNumber.ONE);
        for (int i = 0; i < 222 * desiredFPS; ++i) {
            int finalI = i;
            gameScheduler.aircraftHpControllers.forEach(c -> {
                if (c.getLaunchCondition().shouldLaunchNow()) {
                    System.out.println(String.format("Step %5d, %5.1f s, [HP] %s", finalI, (float) finalI / desiredFPS, c.getName()));
                }
            });
            gameScheduler.aircraftLaunchControllers.forEach(c -> {
                if (c.getLaunchCondition().shouldLaunchNow()) {
                    System.out.println(String.format("Step %5d, %5.1f s, [AC] %s", finalI, (float) finalI / desiredFPS, c.getName()));
                }
            });
            gameScheduler.bonusLaunchControllers.forEach(c -> {
                if (c.getLaunchCondition().shouldLaunchNow()) {
                    System.out.println(String.format("Step %5d, %5.1f s, [BN] %s", finalI, (float) finalI / desiredFPS, c.getName()));
                }
            });
        }
    }
}
