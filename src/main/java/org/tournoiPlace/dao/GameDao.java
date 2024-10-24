package org.tournoiPlace.dao;

import org.tournoiPlace.model.Game;

import java.util.List;

public interface GameDao {
    void addGame(Game game);
    List<Game> getAllGames();
}
