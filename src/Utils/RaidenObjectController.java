package Utils;

public enum RaidenObjectController {
    AI, KEYBOARD1, KEYBOARD2;

    public boolean isKeyboard() {
        return this.equals(KEYBOARD1);
    }
}
