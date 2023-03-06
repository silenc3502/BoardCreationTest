package player;

import dice.Dice;

public class Player {

    final private Long playerId;
    final private String nickname;
    private boolean isAlive;

    public Player(Long playerId, String nickname) {
        this.playerId = playerId;
        this.nickname = nickname;

        this.isAlive = true;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", nickname='" + nickname +
                ", isAlive='" + isAlive + '\'' +
                '}' + '\n';
    }
}
