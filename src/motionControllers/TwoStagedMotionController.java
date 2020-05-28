package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Callback;
import utils.Condition;

/**
 * A two staged motion controller that uses {@link #stageTransitionCondition} to schedule stage transitions.
 *
 * @author 蔡辉宇
 */
public class TwoStagedMotionController implements MotionController {
    MotionController stageOneScheduler, stageTwoScheduler, currentScheduler;
    Condition stageTransitionCondition;
    Callback stageTransitionCallback;

    /**
     * Constructor.
     *
     * @param stageOneScheduler MotionController in stage one.
     * @param stageTwoScheduler MotionController in stage two.
     * @param stageTransitionCondition A {@link Condition} object specifying state transition conditions.
     * @param stageTransitionCallback A {@link Callback} object containing logic when stage transition occurs.
     */
    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler,
                                     Condition stageTransitionCondition,
                                     Callback stageTransitionCallback) {
        this.currentScheduler = this.stageOneScheduler = stageOneScheduler;
        this.stageTwoScheduler = stageTwoScheduler;
        this.stageTransitionCondition = stageTransitionCondition;
        this.stageTransitionCallback = stageTransitionCallback;
    }

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler, Condition stageTransitionCondition) {
        this(stageOneScheduler, stageTwoScheduler, stageTransitionCondition, null);
    }

    public void setStageTransitionCallback(Callback stageTransitionCallback) {
        this.stageTransitionCallback = stageTransitionCallback;
    }

    @Override
    public void registerParent(BaseRaidenObject raidenObject) {
        stageOneScheduler.registerParent(raidenObject);
        stageTwoScheduler.registerParent(raidenObject);
    }

    @Override
    public BaseRaidenObject getParent() {
        return currentScheduler.getParent();
    }

    public void scheduleSpeed() {
        if (stageTransitionCondition.conditionSatisfied()) {
            currentScheduler = stageTwoScheduler;
            resetSpeed();
            if (stageTransitionCallback != null)
                stageTransitionCallback.callback();
        }
        currentScheduler.scheduleSpeed();
    }
}
