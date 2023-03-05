package score;

public class Score {

    final private Long scoreId;

    private int totalScore;

    final private Long playerId;

    public Score (Long scoreId, Long playerId) {
        this.scoreId = scoreId;
        this.playerId = playerId;
    }
}

// playerList = playerService.findAllPlayer();
// scoreService.calculatePlayerScore(playerList, );