package motionControllers;

import raidenObjects.BaseRaidenObject;
import utils.Condition;

public class TwoStagedMotionController implements MotionController {
    MotionController stageOneScheduler, stageTwoScheduler, currentScheduler;
    Condition stageTransitionCondition;
    StateTransitionCallback stageTransitionCallback;

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler,
                                     Condition stageTransitionCondition,
                                     StateTransitionCallback stageTransitionCallback) {
        this.currentScheduler = this.stageOneScheduler = stageOneScheduler;
        this.stageTwoScheduler = stageTwoScheduler;
        this.stageTransitionCondition = stageTransitionCondition;
        this.stageTransitionCallback = stageTransitionCallback;
    }

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler, Condition stageTransitionCondition) {
        this(stageOneScheduler, stageTwoScheduler, stageTransitionCondition, null);
    }

    public void setStageTransitionCallback(StateTransitionCallback stageTransitionCallback) {
        this.stageTransitionCallback = stageTransitionCallback;
    }

    @Override
    public void registerParent(BaseRaidenObject raidenObject) {
        stageOneScheduler.registerParent(raidenObject);
        stageTwoScheduler.registerParent(raidenObject);
    }

    @Override
    public BaseRaidenObject getRaidenObject() {
        return currentScheduler.getRaidenObject();
    }

    public void scheduleSpeed() {
        if (stageTransitionCondition.conditionSatisfied()) {
            currentScheduler = stageTwoScheduler;
            resetSpeed();
            if (stageTransitionCallback != null)
                stageTransitionCallback.stageOneFinished();
        }
        currentScheduler.scheduleSpeed();
    }
}
