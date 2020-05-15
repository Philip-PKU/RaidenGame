package launchControllers;

import java.util.Collections;
import java.util.List;

public class PeriodicLaunchController implements LaunchController {
    protected int cooldown, curCooldown;
    protected LaunchEventAdapter launchEventAdapter;
    protected List<Integer> launchTimes;
    protected boolean active = false;

    public PeriodicLaunchController(int cooldown, int initCooldown, LaunchEventAdapter launchEventAdapter, List<Integer> launchTimes) {
        this.cooldown = cooldown;
        this.curCooldown = initCooldown;
        this.launchEventAdapter = launchEventAdapter;
        this.launchTimes = launchTimes;
    }

    public PeriodicLaunchController(int cooldown, int initCooldown, LaunchEventAdapter launchEventAdapter) {
        this(cooldown, initCooldown, launchEventAdapter, Collections.singletonList(0));
    }

    public void activate() {
        active = true;
    }

    public void launchIfPossible() {
        if (active) {
            --curCooldown;
            int i = launchTimes.indexOf(curCooldown);
            if (i != -1)
                launchEventAdapter.launch();
            if (curCooldown <= 0)
                curCooldown = cooldown;
        }
    }
}
