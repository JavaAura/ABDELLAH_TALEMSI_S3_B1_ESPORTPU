package org.tournoiPlace.service;

import org.tournoiPlace.model.Game;

import java.util.List;

public interface GameService {
    void addGame(Game game);
    List<Game> getGames();
    Game getGame(int id);
    void deleteGame(int id);
    void updateGame(Game game);

}
