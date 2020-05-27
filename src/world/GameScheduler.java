package world;

import launchControllers.ConstAccelerationLaunchEventScheduler;
import launchControllers.LaunchController;
import launchControllers.PeriodicStochasticLaunchEventScheduler;
import raidenObjects.aircrafts.BlackHoleAircraft;
import raidenObjects.aircrafts.BumpingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BarbetteAircraft;
import raidenObjects.aircrafts.shootingAircrafts.BigShootingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.MiddleShootingAircraft;
import raidenObjects.aircrafts.shootingAircrafts.SmallShootingAircraft;
import raidenObjects.bonus.*;
import utils.GameLevel;
import utils.PlayerNumber;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;
import static raidenObjects.bonus.CoinBonus.*;
import static world.World.*;

public class GameScheduler {
    int gameLevel;
    List<LaunchController<PeriodicStochasticLaunchEventScheduler>> aircraftHpControllers;
    List<LaunchController<ConstAccelerationLaunchEventScheduler>> aircraftLaunchControllers;
    List<LaunchController<PeriodicStochasticLaunchEventScheduler>> bonusLaunchControllers;

    public GameScheduler(GameLevel gameLevel, PlayerNumber playerNumber) {
        this.gameLevel = gameLevel.ordinal() + playerNumber.ordinal();

        aircraftHpControllers = Arrays.asList(
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(2000, 1000, 0.5f),
                        () -> SmallShootingAircraft.setStaticMaxHp(min(300, (int) (SmallShootingAircraft.getStaticMaxHp() * 1.4f)))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(2000, 1500, 0.6f),
                        () -> MiddleShootingAircraft.setStaticMaxHp(min(400, (int) (MiddleShootingAircraft.getStaticMaxHp() * 1.3f)))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(2000, 1800, 0.5f),
                        () -> BigShootingAircraft.setStaticMaxHp(min(1200, (int) (BigShootingAircraft.getStaticMaxHp() * 1.2f)))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(3000, 1400, 0.4f),
                        () -> BarbetteAircraft.setStaticMaxHp(min(1500, (int) (BarbetteAircraft.getStaticMaxHp() * 1.15f)))
                )
        );
        aircraftHpControllers.forEach(hpController -> {
            hpController.getLaunchEventScheduler().scaleByGameLevel(gameLevel);
            hpController.activate();
        });

        aircraftLaunchControllers = Arrays.asList(
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(400, 300,
                                0.1f, 0.015f, 0.8f),
                        () -> aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(700, 40,
                                0.05f, 0.015f, 0.6f),
                        () -> aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(600, 198,
                                0.08f, 0.01f, 0.5f),
                        () -> aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(1000, 600,
                                0.04f, 0.01f, 0.25f),
                        () -> {
                            aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2.0f, 0));
                        }
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(1500, 50, 600,
                                450,400,
                                0.02f, 0.015f, 0.15f),
                        () -> aircraftList.add(new BlackHoleAircraft(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(2000, 100, 800,
                                700, 500,
                                0.02f, 0.015f, 0.15f),
                        () -> aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(5000, 2800,
                                0.02f, 0.02f, 0.15f),
                        () -> {
                            aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new BigShootingAircraft(rand.nextInt(windowWidth), 0));
                        }
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(5000, 1500,
                                0.02f, 0.02f, 0.15f),
                        () -> {
                            aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new SmallShootingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2f, 0));
                            aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2f, 0));
                        }
                ),
                new LaunchController<>(
                        new ConstAccelerationLaunchEventScheduler(9000, 4000,
                                0.02f, 0.02f, 0.1f),
                        () -> {
                            aircraftList.add(new BlackHoleAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new BarbetteAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2f, 0));
                            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth), 0));
                            aircraftList.add(new BumpingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2f, 0));
                            aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth / 2), 0));
                            aircraftList.add(new MiddleShootingAircraft(rand.nextInt(windowWidth / 2) + windowWidth / 2f, 0));
                        }
                )
        );
        aircraftLaunchControllers.forEach(launchController -> {
            launchController.getLaunchEventScheduler().scaleByGameLevel(gameLevel);
            launchController.activate();
        });

        bonusLaunchControllers = Arrays.asList(
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(50, 10, 0.15f),
                        () -> interactantList.add(new CoinBonus(rand.nextInt(windowWidth), 0, COIN_SMALL))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(100, 880, 0.05f),
                        () -> interactantList.add(new CoinBonus(rand.nextInt(windowWidth), 0, COIN_MEDIUM))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(100, 340, 0.02f),
                        () -> interactantList.add(new CoinBonus(rand.nextInt(windowWidth), 0, COIN_BIG))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(1000, 400, 0.25f),
                        () -> interactantList.add(new InvincibleBonus(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(1000, 900, 0.25f),
                        () -> interactantList.add(new CureBonus(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(800, 250, 0.25f),
                        () -> interactantList.add(new MagnetBonus(rand.nextInt(windowWidth), 0))
                ),
                new LaunchController<>(
                        new PeriodicStochasticLaunchEventScheduler(2500, 600, 0.45f),
                        () -> interactantList.add(new WeaponUpgradeBonus(rand.nextInt(windowWidth), 0))
                )
        );
        bonusLaunchControllers.forEach(launchController ->  {
            launchController.getLaunchEventScheduler().scaleByGameLevel(gameLevel);
            launchController.activate();
        });
    }

    void scheduleObjectInserts() {
        aircraftHpControllers.forEach(LaunchController::launchIfPossible);
        aircraftLaunchControllers.forEach(LaunchController::launchIfPossible);
        bonusLaunchControllers.forEach(LaunchController::launchIfPossible);
    }
}
