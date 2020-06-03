package main.utils;

import javax.sound.sampled.*;

import static javax.sound.sampled.Port.Info.*;

/**
 * A convenience class for music volume control. The setVolume function in maryb.player.Player seems to have broken.
 *
 * @author 蔡辉宇
 */
public class VolumeController {

    public static float getVolume() {
        Line.Info[] sources = {SPEAKER, LINE_OUT, HEADPHONE};

        for (Line.Info source : sources) {
            if (AudioSystem.isLineSupported(source)) {
                try {
                    Port outline = (Port) AudioSystem.getLine(source);
                    outline.open();
                    FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                    return volumeControl.getValue();
                } catch (LineUnavailableException ex) {
                    System.err.println("source not supported");
                    ex.printStackTrace();
                }
            }
        }
        throw new RuntimeException("In Utils.Mp3Player: Unable to find any output line.");
    }

    public static void setVolume(float volume) {
        Line.Info[] sources = {SPEAKER, LINE_OUT, HEADPHONE};
        boolean foundSource = false;
        for (Line.Info source : sources) {
            if (AudioSystem.isLineSupported(source)) {
                try {
                    Port outline = (Port) AudioSystem.getLine(source);
                    outline.open();
                    FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                    volumeControl.setValue(volume);
                    foundSource = true;
                    break;
                } catch (LineUnavailableException ex) {
                    System.err.println("source not supported");
                    ex.printStackTrace();
                }
            }
        }
        if (!foundSource)
            throw new RuntimeException("In Utils.Mp3Player: Unable to find any output line.");
    }
    
    /**
     * Get minimum volume and maximum volume 
     * 
     * @author 杨芳源
     */
    public static VolumeRange getVolumeInterval() {
    	Line.Info[] sources = {SPEAKER, LINE_OUT, HEADPHONE};

        for (Line.Info source : sources) {
            if (AudioSystem.isLineSupported(source)) {
                try {
                    Port outline = (Port) AudioSystem.getLine(source);
                    outline.open();
                    FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                    return new VolumeRange(volumeControl.getMinimum(), volumeControl.getMaximum());
                } catch (LineUnavailableException ex) {
                    System.err.println("source not supported");
                    ex.printStackTrace();
                }
            }
        }
        throw new RuntimeException("In Utils.Mp3Player: Unable to find any output line.");
    }
    
}