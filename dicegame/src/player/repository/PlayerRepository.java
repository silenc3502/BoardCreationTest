package player.repository;

import player.Player;

import java.util.List;

public interface PlayerRepository {

    List<Player> findAll();
    Player findByPlayerId(Long id);
    void save(Player player);
}
