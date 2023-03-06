package score.service;

import dice.Dice;
import dice.DiceManager;
import dice.SpecialDice;
import dice.repository.DiceRepository;
import dice.repository.DiceRepositoryImpl;
import player.Player;
import player.repository.PlayerRepository;
import player.repository.PlayerRepositoryImpl;
import score.Score;
import score.repository.ScoreRepository;
import score.repository.ScoreRepositoryImpl;
import utility.AutoIncrementGenerator;

import java.util.List;

public class ScoreServiceImpl implements ScoreService {

    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private final DiceRepository diceRepository = DiceRepositoryImpl.getInstance();
    private final ScoreRepository scoreRepository = ScoreRepositoryImpl.getInstance();

    @Override
    public void calculateTotalScore(List<DiceManager> diceManagerList) {

        for (int i = 0; i < diceManagerList.size(); i++) {
            final DiceManager currentDiceManager = diceManagerList.get(i);

            final Dice playerDice = currentDiceManager.getGeneralDice();
            final SpecialDice specialDice = currentDiceManager.getSpecialDice();

            final int generalDiceNumber = playerDice.getDiceNumber();
            final int specialDiceNumber = specialDice == null ? 0 : specialDice.getDiceNumber();

            final Score score = new Score(
                    AutoIncrementGenerator.getEntityAutoIncrementValue("Score"),
                    currentDiceManager.getPlayerId(),
                    generalDiceNumber + specialDiceNumber);

            scoreRepository.save(score);
        }
    }

    @Override
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    @Override
    public Score findScoreByPlayerId(Long playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }
}
