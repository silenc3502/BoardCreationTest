package player.service;

import player.Player;

import java.util.List;

public interface PlayerService {
    public void playDiceGame(List<Player> playerList);
    public Player findWinner(List<Player> playerList);
    public void createPlayer(String nickname);
    List<Player> getAllPlayers();
    public Player findByPlayerId(Long id);
    public void calculateTotalDiceScore(List<Player> playerList);
    public void applySpecialDice(List<Player> playerList);
}
