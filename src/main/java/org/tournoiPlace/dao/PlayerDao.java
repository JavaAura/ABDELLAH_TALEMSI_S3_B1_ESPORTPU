package org.tournoiPlace.dao;

import org.tournoiPlace.model.Player;

import java.util.List;

public interface PlayerDao {
    void addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(int playerId);
    Player getPlayer(int id);
    List<Player> getPlayers();

}
