package Utils;

public enum RaidenObjectOwner {
    NEUTRAL, BOSS, PLAYER1, PLAYER2;

    public boolean isPlayer1() {
        return this.equals(PLAYER1);
    }

    public boolean isPlayer2() {
        return this.equals(PLAYER2);
    }

    public boolean isPlayer() {
        return this.equals(PLAYER1) || this.equals(PLAYER2);
    }

    public boolean isBoss() {
        return this.equals(BOSS);
    }

    public boolean isNeutral() {
        return this.equals(NEUTRAL);
    }

    public boolean isEnemyTo(RaidenObjectOwner other) {
        switch(this) {
            case BOSS:
                return other.isPlayer();
            case PLAYER1:
            case PLAYER2:
                return other.isBoss();
            case NEUTRAL:
                return false;
        }
        return false;
    }
}
