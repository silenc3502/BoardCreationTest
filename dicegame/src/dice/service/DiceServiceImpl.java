package dice.service;

import dice.DiceManager;
import dice.repository.DiceRepository;
import dice.repository.DiceRepositoryImpl;
import player.Player;
import player.repository.PlayerRepository;
import player.repository.PlayerRepositoryImpl;
import utility.AutoIncrementGenerator;

import java.util.List;

public class DiceServiceImpl implements DiceService {

    private final DiceRepository repository = DiceRepositoryImpl.getInstance();

    @Override
    public void stealEachPlayerScore() {

    }

    @Override
    public void donateToEachPlayer() {

    }

    @Override
    public void everyoneLossScore() {

    }

    @Override
    public void killPlayer() {

    }

    @Override
    public DiceManager findByPlayerId(Long playerId) {
        return repository.findByPlayerId(playerId);
    }

    @Override
    public List<DiceManager> getAllDiceInfo() {
        return repository.findAll();
    }

    @Override
    public void rollDice(Player player) {
        final DiceManager diceManager = new DiceManager(
                AutoIncrementGenerator.getEntityAutoIncrementValue("DiceManager"),
                player.getPlayerId()
        );

        repository.save(diceManager);
    }
}
