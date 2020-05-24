package utils;

/**
 * An enum for the faction (side) of raiden objects.
 * Awards should be neutral, enemy aircrafts and bullets are the enemy, players(1/2) and their bullets are player(1/2).
 * @author 蔡辉宇
 */
public enum Faction {
    ENEMY, PLAYER1, PLAYER2, BONUS;

    public boolean isPlayer1() {
        return this.equals(PLAYER1);
    }

    public boolean isPlayer2() {
        return this.equals(PLAYER2);
    }

    public boolean isPlayer() {
        return isPlayer1() || isPlayer2();
    }

    public boolean isEnemy() {
        return this.equals(ENEMY);
    }
    
    public boolean isBonus() {
    	return this.equals(BONUS);
    }
    
    public boolean isEnemyTo(Faction other) {
        switch(this) {
            case ENEMY:
                return other.isPlayer();
            case PLAYER1:
            case PLAYER2:
                return other.isEnemy();
            case BONUS:
                return false;
		default:
			break;
        }
        return false;
    }
}
