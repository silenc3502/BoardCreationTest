package application;

import player.Player;

import java.util.List;

public interface Controller {

    void handle(List<Player> playerList);
}
