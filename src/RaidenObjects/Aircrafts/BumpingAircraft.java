package RaidenObjects.Aircrafts;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

public final class BumpingAircraft extends BaseAircraft {
    BumpingAircraft(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
                    RaidenObjectOwner owner, RaidenObjectController controller,
                    int maxHp, int maxStepsAfterDeath, int crashDamage) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller,
                maxHp, maxStepsAfterDeath, crashDamage);
    }

    public void step() {

    }
}
