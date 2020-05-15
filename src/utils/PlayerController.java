package utils;

/**
 * An enum for the controller of the player.
 * Used in constructor of {@code PlayerAircraft} for selcting motion controller.
 * @author 蔡辉宇
 */
public enum PlayerController {
    KEYBOARD1, KEYBOARD2, NETWORK;

    public boolean isKeyboard() {
        return this.equals(KEYBOARD1) || this.equals(KEYBOARD2);
    }
}
