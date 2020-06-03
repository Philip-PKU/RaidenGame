package main.ui;

import java.awt.TextField;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.util.Pair;

import static main.utils.VolumeController.*;

/**
 *
 * @author 杨芳源
 */
public class VolumeSlider extends JSlider{
	public VolumeSlider() {
		super(0,100);
		Pair<Float, Float> volumeIntervalPair = getVolumeInterval();
		System.out.println(volumeIntervalPair.getKey()+" "+volumeIntervalPair.getValue());
		setValue((int)((getVolume()-volumeIntervalPair.getKey())*100.0/(volumeIntervalPair.getValue()-volumeIntervalPair.getKey())));
		setMajorTickSpacing(20);
		setMinorTickSpacing(5);
		setPaintLabels(true);
		setPaintTicks(true);
		addChangeListener(new ChangeListener() {
		    @Override
		    public void stateChanged(ChangeEvent e) {
		    	Pair<Float, Float> volumeIntervalPair = getVolumeInterval();
		    	float volume = (float)(getValue()/100.0) * (volumeIntervalPair.getValue()-volumeIntervalPair.getKey()) + volumeIntervalPair.getKey();
		    	setVolume(volume);
		    }
		});
	}
}
