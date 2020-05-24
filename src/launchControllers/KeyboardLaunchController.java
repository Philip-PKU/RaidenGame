package launchControllers;

import utils.BaseRaidenKeyAdapter;

import static world.World.gameStep;

public class KeyboardLaunchController implements LaunchController {
    protected int cooldown, lastLaunch;
    protected LaunchEventAdapter launchEventAdapter;
    protected BaseRaidenKeyAdapter keyAdapter;
    protected boolean active;

    public KeyboardLaunchController(int cooldown, BaseRaidenKeyAdapter keyAdapter, LaunchEventAdapter launchEventAdapter) {
        this.cooldown = cooldown;
        this.keyAdapter = keyAdapter;
        this.lastLaunch = 0;
        this.launchEventAdapter = launchEventAdapter;
    }


    @Override
    public void launchIfPossible() {
        if ((keyAdapter.getWeaponState() & keyAdapter.SHOOT) != 0 &&
                gameStep.intValue() >= lastLaunch + cooldown) {
            lastLaunch = gameStep.intValue();
            launchEventAdapter.launch();
        }
    }

    @Override
    public void activate() {
        active = true;
    }
}
