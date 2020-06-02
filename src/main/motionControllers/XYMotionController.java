package main.motionControllers;

import main.raidenObjects.BaseRaidenObject;

/**
 * A Motion Controller that combines X and Y {@link MotionController}s.
 *
 * @author 蔡辉宇
 */
public class XYMotionController extends BaseMotionController implements MotionController {
    MotionController XMotionController, YMotionController;

    /**
     * Constructor.
     *
     * @param XMotionController MotionController that controls X motion.
     * @param YMotionController MotionController that controls Y motion.
     */
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

    /**
     * Constructs a {@link XYMotionController} from X Controller alone. The Y MotionController defaults to
     * {@link ConstSpeedYMotionController}.
     * @param XMotionController MotionController that controls X motion.
     * @param constSpeedY Constant speed of Y motion.
     * @return A new {@link XYMotionController} instance.
     */
    public static XYMotionController createFromXController(MotionController XMotionController, float constSpeedY) {
        return new XYMotionController(XMotionController, new ConstSpeedYMotionController(constSpeedY));
    }
}
