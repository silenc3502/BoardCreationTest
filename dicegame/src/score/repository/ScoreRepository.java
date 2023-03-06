package score.repository;

import player.Player;
import score.Score;

import java.util.List;

public interface ScoreRepository {

    List<Score> findAll();
    void save(Score score);

    int findTotalScoreByPlayerId(Long playerId);

    Score findByPlayerId(Long playerId);
}
