package world;

public interface GameScheduler {
    void init();

    boolean gameIsNotOver();

    void scheduleObjectInserts();
}
