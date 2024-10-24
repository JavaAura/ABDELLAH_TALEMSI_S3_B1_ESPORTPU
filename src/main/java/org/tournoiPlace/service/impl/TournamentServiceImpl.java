package org.tournoiPlace.service.impl;

import org.tournoiPlace.dao.TournamentDao;
import org.tournoiPlace.dao.daoImpl.TournamentDaoImpl;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.service.TournamentService;

import java.util.Collections;
import java.util.List;

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

    @Override
    public void addTournament(Tournament tournament) {
        tournamentDao.addTournament(tournament);
    }

    @Override
    public void updateTournament(Tournament tournament) {
        tournamentDao.updateTournament(tournament);

    }

    @Override
    public void deleteTournament(Tournament tournament) {
        tournamentDao.deleteTournament(tournament);
    }

    @Override
    public Tournament getTournament(int id) {
        return tournamentDao.getTournament(id);
    }

    @Override
    public List<Tournament> getTournaments() {
        return tournamentDao.getTournaments();
    }

    @Override
    public double calculerDureeEstimeeTournoi(int tournoiId) {
        return tournamentDao.calculerDureeEstimeeTournoi(tournoiId);
    }

    @Override
    public Tournament getTournamentByName(String name) {
        return tournamentDao.findByName(name);
    }
}
