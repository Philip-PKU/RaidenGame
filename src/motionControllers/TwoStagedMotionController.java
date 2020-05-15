package motionControllers;

import raidenObjects.BaseRaidenObject;

public class TwoStagedMotionController extends BaseMotionController implements MotionController {
    MotionController stageOneScheduler, stageTwoScheduler;
    StateTransitionJudge stateTransitionJudge;
    StateTransitionEventAdapter stateTransitionEventAdapter;
    boolean isInStageTwo = false;

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler,
                                  StateTransitionJudge stateTransitionJudge,
                                  StateTransitionEventAdapter stateTransitionEventAdapter) {
        this.stageOneScheduler = stageOneScheduler;
        this.stageTwoScheduler = stageTwoScheduler;
        this.stateTransitionJudge = stateTransitionJudge;
        this.stateTransitionEventAdapter = stateTransitionEventAdapter;
    }

    public TwoStagedMotionController(MotionController stageOneScheduler, MotionController stageTwoScheduler, StateTransitionJudge stateTransitionJudge) {
        this(stageOneScheduler, stageTwoScheduler, stateTransitionJudge, null);
    }

    public void setStateTransitionEventAdapter(StateTransitionEventAdapter stateTransitionEventAdapter) {
        this.stateTransitionEventAdapter = stateTransitionEventAdapter;
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
                if (stateTransitionEventAdapter != null)
                    stateTransitionEventAdapter.stageOneFinished();
            }
            else
                stageOneScheduler.scheduleSpeed();
        }
        if (isInStageTwo)
            stageTwoScheduler.scheduleSpeed();
    }
}
