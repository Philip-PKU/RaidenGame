package utils;

public enum PlayerController {
    KEYBOARD1, KEYBOARD2, NETWORK;

    public boolean isKeyboard() {
        return this.equals(KEYBOARD1);
    }
}
