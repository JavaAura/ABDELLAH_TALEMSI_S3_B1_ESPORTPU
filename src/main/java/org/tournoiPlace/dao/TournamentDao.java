package org.tournoiPlace.dao;

import org.tournoiPlace.model.Tournament;

import java.util.List;

public interface TournamentDao {
    void addTournament(Tournament tournament);
    void updateTournament(Tournament tournament);
    void deleteTournament(Tournament tournament);
    Tournament getTournament(int id);
    List<Tournament> getTournaments();
    double calculerDureeEstimeeTournoi(int tournoiId);

}
