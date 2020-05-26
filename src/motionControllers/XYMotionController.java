package motionControllers;

import raidenObjects.BaseRaidenObject;

public class XYMotionController extends BaseMotionController implements MotionController {
    MotionController XMotionController, YMotionController;

    public XYMotionController(MotionController XMotionController, MotionController YMotionController) {
        this.XMotionController = XMotionController;
        this.YMotionController = YMotionController;
    }

    @Override
    public void scheduleSpeed() {
        XMotionController.scheduleSpeed();
        YMotionController.scheduleSpeed();
    }

    @Override
    public void registerParent(BaseRaidenObject raidenObject) {
        super.registerParent(raidenObject);
        XMotionController.registerParent(raidenObject);
        YMotionController.registerParent(raidenObject);
    }

    public static XYMotionController createFromXController(MotionController XMotionController, float speedY) {
        return new XYMotionController(XMotionController, new ConstSpeedYMotionController(speedY));
    }
}
