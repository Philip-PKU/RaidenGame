package main.utils;

/**
 * An enum for the controller of the player.
 * Used in constructor of {@link main.raidenObjects.aircrafts.shootingAircrafts.PlayerAircraft} for selecting motion controller.
 *
 * @author 蔡辉宇
 */
public enum PlayerController {
    KEYBOARD1, KEYBOARD2, NETWORK;

    public boolean isKeyboard() {
        return this.equals(KEYBOARD1) || this.equals(KEYBOARD2);
    }
}
