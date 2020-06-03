package main.ui;

import main.utils.VolumeRange;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static main.utils.VolumeController.*;

/**
 * Slider for adjusting the relative volume.
 *
 * @author 杨芳源
 */
public class VolumeSlider extends JSlider {
    public VolumeSlider() {
        super(0, 100);
        VolumeRange volumeRange = getVolumeInterval();
        setValue((int) ((getVolume() - volumeRange.getMin()) * 100.0 / (volumeRange.getMax() - volumeRange.getMin())));
        setMajorTickSpacing(20);
        setMinorTickSpacing(5);
        setPaintLabels(true);
        setPaintTicks(true);
        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = (getValue() / 100f) * (volumeRange.getMax() - volumeRange.getMin()) + volumeRange.getMin();
                System.out.println(volume);
                setVolume(volume);
            }
        });
    }
}
