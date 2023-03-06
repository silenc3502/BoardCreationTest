package dice.service;

import dice.DiceManager;
import dice.SpecialDicePattern;
import player.Player;

import java.util.List;

public interface DiceService {

    DiceManager findByPlayerId(Long playerId);
    List<DiceManager> getAllDiceInfo();
    void rollDice(Player player);

    SpecialDicePattern findSpecialDiceByPlayerId(Long playerId);

    void stealEachPlayerScore(List<Player> playerList, Long playerId);

    void donateScoreToEachPlayer(List<Player> playerList, Long playerId);

    void everyoneLossScore(List<Player> playerList);

    List<DiceManager> findAll();
}
