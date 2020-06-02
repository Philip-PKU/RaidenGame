package world.pages;

import world.World;

import java.awt.*;
import java.io.IOException;

/**
 * An abstraction for Game Pages.
 *
 * @see utils.PageStatus
 *
 * @author 蔡辉宇
 */
public interface Page {
    void run(World world) throws InterruptedException, IOException;

    void paint(Graphics g) throws IOException;

    void clean(World world);
}
