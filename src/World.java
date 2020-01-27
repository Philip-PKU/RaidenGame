import FlyingObjects.Aircrafts.ShootingAircrafts.BigShootingAircraft;
import FlyingObjects.BaseFlyingObject;
import FlyingObjects.Weapons.SmallBullet;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Thread.sleep;

public class World extends JPanel {
    Set<BaseFlyingObject> flyingObjectSet = new TreeSet<>();
    MutableInt gameStep = new MutableInt(0);
    public World() {
        flyingObjectSet.add(new BigShootingAircraft(20, 20, 2, 40, gameStep,
                new SmallBullet(30, 30, gameStep)));
    }
    public void paint(Graphics g) {
        flyingObjectSet.forEach(flyingObject -> flyingObject.paint(g));
    }

    public void startGame() throws InterruptedException {
        while(true) {
            if (gameStep.intValue() == 4) {
                for (BaseFlyingObject flyingObject : flyingObjectSet) {
                    flyingObject.markAsDead();
                    flyingObjectSet.remove(flyingObject);
                }
            }
            flyingObjectSet.forEach(BaseFlyingObject::step);
            repaint();
            gameStep.increment();
            sleep(500);
        }
    }
}
