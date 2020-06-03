package main.raidenObjects.bonus;

import static main.ui.world.World.rand;
import static main.ui.world.World.windowWidth;

import main.motionControllers.HoveringXMotionController;
import main.motionControllers.MotionController;
import main.motionControllers.XYMotionController;
import main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft;
import main.utils.Faction;
import main.utils.InitLocation;

/**
 * Bonus that gives player coins.
 * There are 3 types of {@link CoinBonus}, providing 10, 20 and 40 coins, respectively.
 *
 * @author 张哲瑞
 */
public final class CoinBonus extends BaseBonus {
    /**
     * The indices of current coin in the type list.
     * Can be referenced by other classes when calling the {@link CoinBonus} constructor.
     */
    public static int COIN_SMALL = 0, COIN_MEDIUM = 1, COIN_BIG = 2;
    public static int[] coins = {10, 20, 40};
    public static int[] sizes = {10, 18, 25};
    int coin;

    public CoinBonus(int coinIndex) {
        super("CoinBonus" + coins[coinIndex], sizes[coinIndex], sizes[coinIndex], Faction.BONUS);
        this.coin = coins[coinIndex];
        MotionController XController = new HoveringXMotionController(0.5f, 20, windowWidth - 20);
        MotionController XYController = XYMotionController.createFromXController(
                XController, 1.5f);
        this.registerMotionController(XYController);
    }

    public CoinBonus(InitLocation initLocation, int coinIndex) {
        this(coinIndex);
        initXFromLocation(initLocation);
    }

    public CoinBonus(float x, float y, int coinIndex) {
        this(coinIndex);
        setX(x);
        setY(y);
    }

    public CoinBonus(InitLocation initLocation) {
        this(initLocation, rand.nextInt(coins.length));
    }

    @Override
    public void bonus(PlayerAircraft aircraft) {
        super.bonus(aircraft);
        aircraft.receiveCoin(coin);
    }
}
