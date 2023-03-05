import dice.DiceManager;
import dice.SpecialDice;
import dice.service.DiceService;
import dice.service.DiceServiceImpl;
import player.Player;
import player.service.PlayerService;
import player.service.PlayerServiceImpl;
import utility.AutoIncrementGenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    final public static int PLAYER_MAX = 3;

    final private static PlayerService playerService = new PlayerServiceImpl();
    final private static DiceService diceService = new DiceServiceImpl();

    private static void autoIncrementEnvironmentSet () {
        AutoIncrementGenerator.setAutoIncrementEntity("Player");
        AutoIncrementGenerator.setAutoIncrementEntity("DiceManager");
    }

    public static void main(String[] args) {

        autoIncrementEnvironmentSet();

        for (int i = 0; i < PLAYER_MAX; i++) {
            playerService.createPlayer("Tester" + (i + 1));
            diceService.rollDice(playerService.findByPlayerId(i + 1L));
        }
        List<Player> playerList = playerService.getAllPlayers();
        List<DiceManager> diceManagerList = diceService.getAllDiceInfo();
        System.out.println(diceManagerList);

        playerService.calculateTotalDiceScore(playerList);
        playerService.applySpecialDice(playerList);
        System.out.println(playerList);

        Player winner = playerService.findWinner(playerList);

        if (winner != null) {
            System.out.println("winner: " + winner);
        } else {
            System.out.println("무승부!");
        }
    }
}