package org.tournoiPlace.service;

import org.tournoiPlace.model.Player;

import java.util.List;

public interface PlayerService {
    void addPlayer(Player player);
    Player getPlayer(int id);
    void deletePlayer(int playerId);
    List<Player> getPlayers();
    void updatePlayer(Player player);
}
