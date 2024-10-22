package org.tournoiPlace.service.impl;

import org.tournoiPlace.dao.TournamentDao;
import org.tournoiPlace.dao.daoImpl.TournamentDaoImpl;
import org.tournoiPlace.service.TournamentService;

public class TournamentServiceImpl implements TournamentService {
    private final TournamentDao tournamentDao;

    // Constructor injection for dependency
    public TournamentServiceImpl(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    @Override
    public void runSampleOperations() {
        System.out.println("Running application...");
    }
}
