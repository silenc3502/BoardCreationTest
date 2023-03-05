package player.repository;

import player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final PlayerRepository INSTANCE = new PlayerRepositoryImpl();

    public static PlayerRepository getInstance() {
        return INSTANCE;
    }

    private final Map<Long, Player> playerDb = new HashMap<>();

    @Override
    public Player findByPlayerId(Long id) {
        return playerDb.get(id);
    }

    @Override
    public void save(Player player) {
        playerDb.put(player.getPlayerId(), player);
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(playerDb.values());
    }
}
