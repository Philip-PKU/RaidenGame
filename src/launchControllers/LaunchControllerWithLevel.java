package launchControllers;

public class LaunchControllerWithLevel extends LaunchController {
    public final int weaponLevel;

    public LaunchControllerWithLevel(int weaponLevel) {
        super();
        this.weaponLevel = weaponLevel;
    }

    public int getWeaponLevel() {
        return weaponLevel;
    }
}
