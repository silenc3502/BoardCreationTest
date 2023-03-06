package score.repository;

import player.Player;
import player.repository.PlayerRepository;
import player.repository.PlayerRepositoryImpl;
import score.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreRepositoryImpl implements ScoreRepository {

    private static final ScoreRepository INSTANCE = new ScoreRepositoryImpl();

    public static ScoreRepository getInstance() {
        return INSTANCE;
    }

    private final Map<Long, Score> scoreDb = new HashMap<>();

    @Override
    public List<Score> findAll() {
        return new ArrayList<>(scoreDb.values());
    }

    @Override
    public void save(Score score) {
        scoreDb.put(score.getScoreId(), score);
    }

    @Override
    public int findTotalScoreByPlayerId(Long playerId) {
        return scoreDb.get(playerId).getTotalScore();
    }

    @Override
    public Score findByPlayerId(Long playerId) {
        return scoreDb.get(playerId);
    }
}
