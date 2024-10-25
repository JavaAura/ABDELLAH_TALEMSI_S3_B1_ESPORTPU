package org.tournoiPlace.service;

import org.tournoiPlace.model.Team;
import org.tournoiPlace.model.Tournament;

import java.util.List;

public interface TeamService {
    void addTeam(Team team);
    void deleteTeam(int teamId);
    void updateTeam(Team team);
    List<Team> getTeams();
    Team getTeam(int id);
    Team getTeamByName(String name);
}
