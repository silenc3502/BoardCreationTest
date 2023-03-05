package dice.repository;

import dice.DiceManager;
import player.Player;

import java.util.List;

public interface DiceRepository {

    void save(DiceManager post);
    DiceManager findByPlayerId(Long playerId);
    List<DiceManager> findAll();
}
