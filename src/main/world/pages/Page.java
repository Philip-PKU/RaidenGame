package main.world.pages;

import main.utils.PageStatus;
import main.world.World;

import java.awt.*;
import java.io.IOException;

/**
 * An abstraction for Game Pages.
 *
 * @see PageStatus
 *
 * @author 蔡辉宇
 */
public interface Page {
    void run(World world) throws InterruptedException, IOException;

    void paintComponent(Graphics g, World world) throws IOException;

    void clean(World world);
}
