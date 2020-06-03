package main.utils;

public /**
 * A utility class for representing volume range.
 * Used by {@link VolumeController} and {@link main.ui.VolumeSlider}
 */
class VolumeRange {
    float min, max;
    VolumeRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
