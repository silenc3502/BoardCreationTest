package score.service;

import dice.DiceManager;
import score.Score;

import java.util.List;

public interface ScoreService {
    void calculateTotalScore(List<DiceManager> diceManagerList);

    List<Score> findAll();

    Score findScoreByPlayerId(Long playerId);
}
