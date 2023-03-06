package score.controller;

import application.Controller;
import dice.DiceManager;
import dice.SpecialDicePattern;
import dice.service.DiceService;
import dice.service.DiceServiceImpl;
import player.Player;
import player.service.PlayerService;
import player.service.PlayerServiceImpl;
import score.Score;
import score.service.ScoreService;
import score.service.ScoreServiceImpl;

import java.util.List;

public class ScoreController implements Controller {

    final private static PlayerService playerService = new PlayerServiceImpl();
    final private static DiceService diceService = new DiceServiceImpl();
    final private static ScoreService scoreService = new ScoreServiceImpl();

    @Override
    public void handle(List<Player> playerList) {
        List<DiceManager> diceManagerList = diceService.findAll();
        scoreService.calculateTotalScore(diceManagerList);

        for (int i = 0; i < playerList.size(); i++) {
            final Long playerId = playerList.get(i).getPlayerId();
            SpecialDicePattern specialDicePattern = diceService.findSpecialDiceByPlayerId(playerId);

            if (specialDicePattern == SpecialDicePattern.PATTERN_NOTHING) {
                return;
            }

            if (specialDicePattern == SpecialDicePattern.PATTERN_STEAL) {
                diceService.stealEachPlayerScore(playerList, playerId);
            }

            if (specialDicePattern == SpecialDicePattern.PATTERN_DEATH) {
                final Player player = playerService.findByPlayerId(playerId);
                playerService.kiilPlayer(player);
            }

            if (specialDicePattern == SpecialDicePattern.PATTERN_DONATE) {
                diceService.donateScoreToEachPlayer(playerList, playerId);
            }

            if (specialDicePattern == SpecialDicePattern.PATTERN_BUDDY_FUCKER) {
                diceService.everyoneLossScore(playerList);
            }
        }
    }
}
