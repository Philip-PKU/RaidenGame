package motionControllers;

public interface TargetAwareMotionController extends MotionController {
    float distToTarget();
}
