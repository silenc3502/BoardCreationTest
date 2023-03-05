package dice.service;

import dice.DiceManager;
import player.Player;

import java.util.List;

public interface DiceService {

    void stealEachPlayerScore();
    void donateToEachPlayer();
    void everyoneLossScore();
    void killPlayer();
    DiceManager findByPlayerId(Long playerId);
    List<DiceManager> getAllDiceInfo();
    void rollDice(Player player);
}
