import application.Controller;
import dice.DiceManager;
import dice.SpecialDicePattern;
import dice.service.DiceService;
import dice.service.DiceServiceImpl;
import player.Player;
import player.repository.PlayerRepositoryImpl;
import player.service.PlayerService;
import player.service.PlayerServiceImpl;
import score.Score;
import score.controller.ScoreController;
import score.service.ScoreService;
import score.service.ScoreServiceImpl;
import utility.AutoIncrementGenerator;

import java.util.List;

public class Main {
    final public static int PLAYER_MAX = 3;

    final private static PlayerService playerService = new PlayerServiceImpl();
    final private static DiceService diceService = new DiceServiceImpl();
    final private static ScoreService scoreService = new ScoreServiceImpl();

    final private static Controller scoreController = new ScoreController();

    private static void autoIncrementEnvironmentSet () {
        AutoIncrementGenerator.setAutoIncrementEntity("Player");
        AutoIncrementGenerator.setAutoIncrementEntity("DiceManager");
        AutoIncrementGenerator.setAutoIncrementEntity("Score");
    }

    public static void main(String[] args) {

        autoIncrementEnvironmentSet();

        for (int i = 0; i < PLAYER_MAX; i++) {
            playerService.createPlayer("Tester" + (i));
            diceService.rollDice(playerService.findByPlayerId((long) i));
        }
        final List<Player> playerList = playerService.getAllPlayers();
        final List<DiceManager> diceManagerList = diceService.getAllDiceInfo();
        System.out.println(diceManagerList);
        //System.out.println(playerList);

        scoreController.handle(playerList);

        final Player winner = playerService.findWinner();

        if (winner != null) {
            final Score score = scoreService.findScoreByPlayerId(winner.getPlayerId());
            System.out.println("winner: " + winner);
            System.out.println("score: " + score);
        } else {
            System.out.println("무승부!");
        }

        List<Score> scoreList = scoreService.findAll();
        System.out.println("every score: " + scoreList);
    }
}