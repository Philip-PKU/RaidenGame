package motionControllers;

import raidenObjects.BaseRaidenObject;

public class TwoStagedMotionController extends BaseMotionController implements MotionController {
    MotionController stageOneScheduler, stageTwoScheduler;
    StateTransitionJudge stateTransitionJudge;
    StateTransitionCallback stateTransitionCallback;
    boolean isInStageTwo = false;

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler,
                                  StateTransitionJudge stateTransitionJudge,
                                  StateTransitionCallback stateTransitionCallback) {
        this.stageOneScheduler = stageOneScheduler;
        this.stageTwoScheduler = stageTwoScheduler;
        this.stateTransitionJudge = stateTransitionJudge;
        this.stateTransitionCallback = stateTransitionCallback;
    }

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler, StateTransitionJudge stateTransitionJudge) {
        this(stageOneScheduler, stageTwoScheduler, stateTransitionJudge, null);
    }

    public void setStateTransitionCallback(StateTransitionCallback stateTransitionCallback) {
        this.stateTransitionCallback = stateTransitionCallback;
    }

    @Override
    public void registerParent(BaseRaidenObject raidenObject) {
        super.registerParent(raidenObject);
        stageOneScheduler.registerParent(raidenObject);
        stageTwoScheduler.registerParent(raidenObject);
    }

    public void scheduleSpeed() {
        if (!isInStageTwo) {
            if (stateTransitionJudge.conditionSatisfied()) {
                isInStageTwo = true;
                resetSpeed();
                if (stateTransitionCallback != null)
                    stateTransitionCallback.stageOneFinished();
            }
            else
                stageOneScheduler.scheduleSpeed();
        }
        if (isInStageTwo)
            stageTwoScheduler.scheduleSpeed();
    }
}
