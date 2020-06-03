package main.utils;

import main.ui.world.World;

/**
 * A utility for keeping track of the game step.
 *
 * @see World#gameStep
 *
 * @author 蔡辉宇
 */
public class MutableInt {
    int value;

    public MutableInt(int initValue) {
        this.value = initValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public MutableInt() {
        this(0);
    }

    public void increment() {
        value++;
    }

    public int intValue() {
        return value;
    }
}
