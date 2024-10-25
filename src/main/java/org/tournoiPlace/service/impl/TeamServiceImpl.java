package org.tournoiPlace.service.impl;

import org.tournoiPlace.dao.TeamDao;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.service.TeamService;

import java.util.Collections;
import java.util.List;

public class TeamServiceImpl implements TeamService {
    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }
    @Override
    public void addTeam(Team team) {
        teamDao.addTeam(team);
    }

    @Override
    public void deleteTeam(int teamId) {
        teamDao.deleteTeam(teamId);

    }

    @Override
    public void updateTeam(Team team) {
        teamDao.updateTeam(team);

    }

    @Override
    public List<Team> getTeams() {
        return teamDao.getAllTeams();
    }

    @Override
    public Team getTeam(int id) {
        return teamDao.getTeamById(id);
    }

    @Override
    public Team getTeamByName(String name) {
        return teamDao.findByName(name);
    }
}
