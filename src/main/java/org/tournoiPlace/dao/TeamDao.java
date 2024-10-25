package org.tournoiPlace.dao;

import org.tournoiPlace.model.Team;
import org.tournoiPlace.model.Tournament;

import java.util.List;

public interface TeamDao {
    void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(int teamId);
    Team getTeamById(int id);
    List<Team> getAllTeams();
    Team findByName(String name);

}
