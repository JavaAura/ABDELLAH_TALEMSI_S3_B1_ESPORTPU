package org.tournoiPlace.service.impl;

import org.tournoiPlace.dao.PlayerDao;
import org.tournoiPlace.model.Player;
import org.tournoiPlace.service.PlayerService;

import java.util.Collections;
import java.util.List;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;


    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
    @Override
    public void addPlayer(Player player) {
        playerDao.addPlayer(player);
    }

    @Override
    public Player getPlayer(int id) {
        return playerDao.getPlayer(id);
    }

    @Override
    public void deletePlayer(int playerId) {
        playerDao.deletePlayer(playerId);
    }

    @Override
    public List<Player> getPlayers() {
        return playerDao.getPlayers();
    }

    @Override
    public void updatePlayer(Player player) {
        playerDao.updatePlayer(player);
    }
}
