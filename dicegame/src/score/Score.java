package score;

public class Score {

    final private Long scoreId;

    private int totalScore;

    final private Long playerId;

    public Score (Long scoreId, Long playerId, int totalScore) {
        this.scoreId = scoreId;
        this.playerId = playerId;
        this.totalScore = totalScore;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", totalScore=" + totalScore +
                ", playerId=" + playerId +
                '}' + '\n';
    }
}

// playerList = playerService.findAllPlayer();
// scoreService.calculatePlayerScore(playerList, );