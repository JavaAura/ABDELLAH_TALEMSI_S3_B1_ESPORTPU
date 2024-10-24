package org.tournoiPlace.service;

import org.tournoiPlace.model.Game;

import java.util.List;

public interface GameService {
    void addGame(Game game);
    List<Game> getGames();

}
