package main.utils;

/**
 * A condition utility with only one method: {@link #conditionSatisfied()}}.
 * @author 蔡辉宇
 */
public interface Condition {
    /**
     * Determine if the condition is satisfied.
     * @return {@code true} if the condition is satisfied, and {@code false} otherwise.
     */
    boolean conditionSatisfied();
}
