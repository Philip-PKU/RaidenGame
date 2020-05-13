package motionControllers;

import raidenObjects.BaseRaidenObject;

public class TwoStagedMotionController extends BaseMotionController implements MotionController {
    MotionController stageOneScheduler, stageTwoScheduler;
    StateTransitionJudge stateTransitionJudge;
    StateTransitionAdapter stateTransitionAdapter;
    boolean isInStageTwo = false;

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler,
                                  StateTransitionJudge stateTransitionJudge,
                                  StateTransitionAdapter stateTransitionAdapter) {
        this.stageOneScheduler = stageOneScheduler;
        this.stageTwoScheduler = stageTwoScheduler;
        this.stateTransitionJudge = stateTransitionJudge;
        this.stateTransitionAdapter = stateTransitionAdapter;
    }

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler, StateTransitionJudge stateTransitionJudge) {
        this(stageOneScheduler, stageTwoScheduler, stateTransitionJudge, null);
    }

    public void setStateTransitionAdapter(StateTransitionAdapter stateTransitionAdapter) {
        this.stateTransitionAdapter = stateTransitionAdapter;
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
                if (stateTransitionAdapter != null)
                    stateTransitionAdapter.stageOneFinished();
            }
            else
                stageOneScheduler.scheduleSpeed();
        }
        if (isInStageTwo)
            stageTwoScheduler.scheduleSpeed();
    }
}
