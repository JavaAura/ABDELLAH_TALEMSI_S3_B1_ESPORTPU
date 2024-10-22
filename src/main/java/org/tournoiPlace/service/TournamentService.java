package org.tournoiPlace.service;

import org.tournoiPlace.model.Tournament;

import java.util.List;

public interface TournamentService {
    void runSampleOperations();
    void addTournament(Tournament tournament);
    void updateTournament(Tournament tournament);
    void deleteTournament(Tournament tournament);
    Tournament getTournament(int id);
    List<Tournament> getTournaments();
}
