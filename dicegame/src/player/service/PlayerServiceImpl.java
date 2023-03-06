package player.service;

import dice.Dice;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private final ScoreRepository scoreRepository = ScoreRepositoryImpl.getInstance();

    @Override
    public Player findWinner() {
        final int WINNER = 0;

        List<Score> scoreList = scoreRepository.findAll();

        List<Score> sortedScore = scoreList.stream()
                .filter(elem -> playerRepository.findByPlayerId(elem.getPlayerId()).isAlive() == true)
                .sorted(Comparator.comparingInt(Score::getTotalScore).reversed())
                .collect(Collectors.toList());

        System.out.println("filter death: " + sortedScore);

        if (sortedScore.size() == 1) {
            final Score maxScore = sortedScore.get(WINNER);
            final Long playerId = maxScore.getPlayerId();

            return playerRepository.findByPlayerId(playerId);
        }

        if (sortedScore.get(WINNER).getTotalScore() ==
                sortedScore.get(WINNER + 1).getTotalScore()) {

            return null;
        }

        final Score maxScore = sortedScore.get(WINNER);
        final Long playerId = maxScore.getPlayerId();

        return playerRepository.findByPlayerId(playerId);
    }

    @Override
    public void createPlayer(String nickname) {
        final Player player = new Player(
                AutoIncrementGenerator.getEntityAutoIncrementValue("Player"),
                nickname
        );
        playerRepository.save(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findByPlayerId(Long id) {
        return playerRepository.findByPlayerId(id);
    }

    @Override
    public void kiilPlayer(Player player) {
        player.setAlive(false);
        playerRepository.save(player);
    }

}
