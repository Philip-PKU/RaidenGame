package utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javax.sound.sampled.Port.Info.*;

public class Mp3Player extends Thread {
    Player player;

    public Mp3Player(File file, float initVolume) {
        try {
            player = new Player(new FileInputStream(file));
        } catch (JavaLayerException | FileNotFoundException ignored) {
        }
        setVolume(initVolume);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                player.play();
            }
        } catch (JavaLayerException ignored) {
        }
    }

    public float getVolume() {
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

    public void setVolume(float volume) {
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
}
