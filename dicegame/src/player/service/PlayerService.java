package player.service;

import player.Player;

import java.util.List;

public interface PlayerService {

    public Player findWinner();
    public void createPlayer(String nickname);
    List<Player> getAllPlayers();
    public Player findByPlayerId(Long id);
    void kiilPlayer(Player player);
}
