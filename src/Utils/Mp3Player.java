package Utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Mp3Player extends Thread {
    Player player;
    int repeatTimes;
    public Mp3Player(File file, int repeatTimes) {
        try {
            player = new Player(new FileInputStream(file));
        }
        catch (JavaLayerException | FileNotFoundException ignored) {}
        this.repeatTimes = repeatTimes;
    }
    public void run() {
        try {
            for (int i = 0; i < repeatTimes; ++i) {
                player.play();
            }
        }
        catch (JavaLayerException ignored) {}
    }
}
