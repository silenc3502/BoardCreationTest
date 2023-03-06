package dice.service;

import dice.DiceManager;
import dice.SpecialDice;
import dice.SpecialDicePattern;
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

public class DiceServiceImpl implements DiceService {

    private final DiceRepository diceRepository = DiceRepositoryImpl.getInstance();
    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private final ScoreRepository scoreRepository = ScoreRepositoryImpl.getInstance();

    @Override
    public DiceManager findByPlayerId(Long playerId) {
        return diceRepository.findByPlayerId(playerId);
    }

    @Override
    public List<DiceManager> getAllDiceInfo() {
        return diceRepository.findAll();
    }

    @Override
    public void rollDice(Player player) {
        final DiceManager diceManager = new DiceManager(
                AutoIncrementGenerator.getEntityAutoIncrementValue("DiceManager"),
                player.getPlayerId()
        );

        diceRepository.save(diceManager);
    }

    @Override
    public SpecialDicePattern findSpecialDiceByPlayerId(Long playerId) {
        final DiceManager diceManager = diceRepository.findByPlayerId(playerId);
        final SpecialDice specialDice = diceManager.getSpecialDice();

        if (specialDice == null) {
            return null;
        }

        return diceManager.getSpecialDice().getSpecialDicePattern();
    }

    private int stealScore(int targetScore) {
        final int STEAL_SCORE = 3;

        if (targetScore - STEAL_SCORE >= 0) {
            return STEAL_SCORE;
        }

        return targetScore;
    }

    @Override
    public void stealEachPlayerScore(List<Player> playerList, Long playerId) {
        final Score currentPlayerScore = scoreRepository.findByPlayerId(playerId);
        int currentPlayerTotalScore = currentPlayerScore.getTotalScore();

        for (int i = 0; i < playerList.size(); i++) {
            if (i == playerId) { continue; }

            final Score targetPlayerScore = scoreRepository.findByPlayerId((long) i);
            final int targetTotalScore = targetPlayerScore.getTotalScore();

            final int scoreStealed = stealScore(targetTotalScore);
            currentPlayerTotalScore += scoreStealed;

            targetPlayerScore.setTotalScore(targetTotalScore - scoreStealed);
            scoreRepository.save(targetPlayerScore);
        }

        currentPlayerScore.setTotalScore(currentPlayerTotalScore);
        scoreRepository.save(currentPlayerScore);
    }

    private int donateScore (int playerScore) {
        final int DONATE_SCORE = 2;

        if (playerScore >= DONATE_SCORE) {
            return DONATE_SCORE;
        }

        return playerScore;
    }

    @Override
    public void donateScoreToEachPlayer(List<Player> playerList, Long playerId) {

        final Score currentPlayerScore = scoreRepository.findByPlayerId(playerId);
        int currentPlayerTotalScore = currentPlayerScore.getTotalScore();

        for (int i = 0; i < playerList.size(); i++) {
            if (i == playerId) { continue; }

            final Score targetPlayerScore = scoreRepository.findByPlayerId((long) i);
            final int targetPlayerTotalScore = targetPlayerScore.getTotalScore();

            final int scoreDonated = donateScore(currentPlayerTotalScore);
            currentPlayerTotalScore -= scoreDonated;
            //System.out.println("currentPlayerTotalScore: " + currentPlayerTotalScore);

            currentPlayerScore.setTotalScore(currentPlayerTotalScore);
            scoreRepository.save(currentPlayerScore);

            targetPlayerScore.setTotalScore(targetPlayerTotalScore + scoreDonated);
            scoreRepository.save(targetPlayerScore);

            System.out.println("donate target: " + targetPlayerScore + "");
        }
    }

    @Override
    public void everyoneLossScore(List<Player> playerList) {
        final int LOSS_SCORE = 2;

        for (int i = 0; i < playerList.size(); i++) {
            final Score currentPlayerScore = scoreRepository.findByPlayerId((long) i);
            int currentPlayerTotalScore = currentPlayerScore.getTotalScore();

            if (currentPlayerTotalScore - LOSS_SCORE > 0) { currentPlayerTotalScore -= LOSS_SCORE; }
            else { currentPlayerTotalScore = 0; }

            currentPlayerScore.setTotalScore(currentPlayerTotalScore);
            scoreRepository.save(currentPlayerScore);
        }

        List<Score> scoreList = scoreRepository.findAll();
        System.out.println("after Loss: " + scoreList);
    }

    @Override
    public List<DiceManager> findAll() {
        return diceRepository.findAll();
    }
}
