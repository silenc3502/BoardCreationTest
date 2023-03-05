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
import utility.AutoIncrementGenerator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository = PlayerRepositoryImpl.getInstance();
    private final DiceRepository diceRepository = DiceRepositoryImpl.getInstance();

    private void applyEachPlayer (List<Player> playerList, Long playerId) {

        //System.out.println(diceRepository.findByPlayerId(playerId));
        final DiceManager playerDiceInfo = diceRepository.findByPlayerId(playerId);
        final Dice playerDice = playerDiceInfo.getGeneralDice();
        final SpecialDice specialDice = playerDiceInfo.getSpecialDice();



        /*
        int specialDice = getSpecialDiceNumber(playerList, currentIdx);

        SpecialDicePattern dicePattern =
                checkSpecialDicePattern(specialDice, playerList, currentIdx);

        System.out.println("pattern: " + dicePattern.getName() +
                "value: " + dicePattern.getValue());

        postProcessAfterGetPattern(dicePattern, playerList, currentIdx);
         */
    }

    @Override
    public void playDiceGame(List<Player> playerList) {

    }

    @Override
    public void calculateTotalDiceScore(List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            final Player player = playerList.get(i);
            final Long playerId = player.getPlayerId();
            final DiceManager playerDiceInfo = diceRepository.findByPlayerId(playerId);
            final Dice playerDice = playerDiceInfo.getGeneralDice();
            final SpecialDice specialDice = playerDiceInfo.getSpecialDice();

            final int generalDiceNumber = playerDice.getDiceNumber();

            if (specialDice != null) {
                player.setTotalDiceScore(generalDiceNumber + specialDice.getDiceNumber());
            } else {
                player.setTotalDiceScore(generalDiceNumber);
            }

            //System.out.println(player);
        }
    }

    private int stealScore(int targetScore) {
        final int STEAL_SCORE = 3;

        if (targetScore - STEAL_SCORE >= 0) {
            return STEAL_SCORE;
        }

        return targetScore;
    }

    private void stealPlayerScore (List<Player> playerList, int currentIdx) {
        final Player currentPlayer = playerRepository.findByPlayerId((long) currentIdx + 1);
        int myTotalScore = currentPlayer.getTotalDiceScore();

        for (int i = 0; i < playerList.size(); i++) {
            if (i == currentIdx) { continue; }

            final Player targetPlayer = playerRepository.findByPlayerId((long) i + 1);
            final int targetTotalScore = targetPlayer.getTotalDiceScore();

            final int scoreStealed = stealScore(targetTotalScore);
            myTotalScore += scoreStealed;

            targetPlayer.setTotalDiceScore(targetTotalScore - scoreStealed);
        }

        currentPlayer.setTotalDiceScore(myTotalScore);
    }

    private int donateScore (int playerScore) {
        final int DONATE_SCORE = 2;

        if (playerScore >= DONATE_SCORE) {
            return DONATE_SCORE;
        }

        return playerScore;
    }

    private void donateCurrentPlayerScore(List<Player> playerList, int currentIdx) {

        final Player currentPlayer = playerRepository.findByPlayerId((long) currentIdx + 1);
        int myTotalScore = currentPlayer.getTotalDiceScore();

        for (int i = 0; i < playerList.size(); i++) {
            if (i == currentIdx) { continue; }

            final Player targetPlayer = playerRepository.findByPlayerId((long) i + 1);
            final int targetTotalScore = targetPlayer.getTotalDiceScore();

            final int scoreDonated = donateScore(myTotalScore);

            currentPlayer.setTotalDiceScore(myTotalScore - scoreDonated);
            targetPlayer.setTotalDiceScore(targetTotalScore + scoreDonated);
        }
    }

    private void everyoneLossScore (List<Player> playerList) {
        final int LOSS_SCORE = 2;

        for (int i = 0; i < playerList.size(); i++) {
            final Player currentPlayer = playerRepository.findByPlayerId((long) i + 1);
            int totalScore = currentPlayer.getTotalDiceScore();

            if (totalScore - LOSS_SCORE > 0) { totalScore -= LOSS_SCORE; }
            else { totalScore = 0; }

            currentPlayer.setTotalDiceScore(totalScore);
        }
    }

    private void applySpecialDicePattern(SpecialDicePattern specialDicePattern,
                                         List<Player> playerList, int currentIdx) {

        if (specialDicePattern == SpecialDicePattern.PATTERN_NOTHING) {
            return;
        }

        if (specialDicePattern == SpecialDicePattern.PATTERN_STEAL) {
            stealPlayerScore(playerList, currentIdx);
        }

        if (specialDicePattern == SpecialDicePattern.PATTERN_DEATH) {
            final Player player = playerRepository.findByPlayerId((long) currentIdx + 1);
            player.setAlive(false);
        }

        if (specialDicePattern == SpecialDicePattern.PATTERN_DONATE) {
            donateCurrentPlayerScore(playerList, currentIdx);
        }

        if (specialDicePattern == SpecialDicePattern.PATTERN_BUDDY_FUCKER) {
            everyoneLossScore(playerList);
        }
    }

    @Override
    public void applySpecialDice(List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            final Player player = playerList.get(i);
            final Long playerId = player.getPlayerId();
            final DiceManager playerDiceInfo = diceRepository.findByPlayerId(playerId);
            final SpecialDice specialDice = playerDiceInfo.getSpecialDice();

            if (specialDice != null) {
                final SpecialDicePattern specialDicePattern = specialDice.getSpecialDicePattern();
                applySpecialDicePattern(specialDicePattern, playerList, i);
            }
        }
    }

    @Override
    public Player findWinner(List<Player> playerList) {
        final int WINNER = 0;

        List<Player> sortedPlayer = playerList.stream()
                .filter(elem -> elem.isAlive() == true)
                .sorted(Comparator.comparingInt(Player::getTotalDiceScore).reversed())
                .collect(Collectors.toList());

        if (sortedPlayer.get(WINNER).getTotalDiceScore() ==
                sortedPlayer.get(WINNER + 1).getTotalDiceScore()) {

            return null;
        }

        return sortedPlayer.get(WINNER);
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

}
