package launchControllers;

public interface LaunchController {
    void launchIfPossible();

    void activate();

    void deactivate();

    String getName();
}
