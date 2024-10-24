package org.tournoiPlace.service;

import org.tournoiPlace.model.Team;

import java.util.List;

public interface TeamService {
    void addTeam(Team team);
    void deleteTeam(int teamId);
    void updateTeam(Team team);
    List<Team> getTeams();
    Team getTeam(int id);
}
