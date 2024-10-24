package org.tournoiPlace.dao;

import org.tournoiPlace.model.Team;

import java.util.List;

public interface TeamDao {
    void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(int teamId);
    Team getTeamById(int id);
    List<Team> getAllTeams();

}
