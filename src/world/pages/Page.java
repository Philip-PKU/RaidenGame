package world.pages;

import world.World;

import java.awt.*;

/**
 * An abstraction for Game Pages.
 *
 * @see utils.PageStatus
 *
 * @author 蔡辉宇
 */
public interface Page {
    void run(World world) throws InterruptedException;

    void paint(Graphics g);

    void clean(World world);
}
