package dice.repository;

import dice.DiceManager;
import player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiceRepositoryImpl implements DiceRepository {

    private static final DiceRepository INSTANCE = new DiceRepositoryImpl();

    public static DiceRepository getInstance() {
        return INSTANCE;
    }

    private final Map<Long, DiceManager> diceDb = new HashMap<>();

    @Override
    public void save(DiceManager diceManager) {
        //System.out.println("save mechanism");
        //System.out.println(diceManager);
        diceDb.put(diceManager.getId(), diceManager);
    }

    @Override
    public DiceManager findByPlayerId(Long playerId) {
        List<DiceManager> diceManagerList = findAll();

        for (int i = 0; i < diceManagerList.size(); i++) {
            if (diceManagerList.get(i).getPlayerId() == playerId) {
                return diceDb.get(playerId);
            }
        }

        return null;
    }

    @Override
    public List<DiceManager> findAll() {
        return new ArrayList<>(diceDb.values());
    }
}
