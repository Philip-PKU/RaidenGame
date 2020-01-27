package FlyingObjects.Aircrafts.ShootingAircrafts;

import FlyingObjects.Weapons.BaseWeapon;
import org.apache.commons.lang3.mutable.MutableInt;

public class BigShootingAircraft extends BaseShootingAircraft {
    public BigShootingAircraft(int x, int y, int maxSpeedX, int maxSpeedY, MutableInt gameStep, BaseWeapon weapon) {
        super(60, 40, x, y, maxSpeedX, maxSpeedY, gameStep,
                100, 13, "bigplane", weapon);
    }
}
