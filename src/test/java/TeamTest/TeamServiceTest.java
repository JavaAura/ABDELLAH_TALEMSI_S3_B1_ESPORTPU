package TeamTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tournoiPlace.dao.TeamDao;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.service.TeamService;
import org.tournoiPlace.service.impl.TeamServiceImpl;

import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private TeamDao teamDao;


    @InjectMocks
    private TeamServiceImpl teamService;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddTeam() {
        Team team = new Team();
        team.setNom("Java");
        team.setClassement(5);
        doNothing().when(teamDao).addTeam(team);
        teamService.addTeam(team);
        verify(teamDao,times(1)).addTeam(team);
    }
    @Test
    public void testUpdateTeam() {
        Team team = new Team();
        team.setId(1);
        team.setNom("JavaUpdate");
        team.setClassement(6);
        doNothing().when(teamDao).updateTeam(team);
        teamService.updateTeam(team);
        verify(teamDao,times(1)).updateTeam(team);
    }

    @Test
    public void testDeleteTeam() {
        Team team = new Team();
        team.setId(1);
        when(teamDao.getTeamById(1)).thenReturn(team);
        Team team1 = teamService.getTeam(1);
        doNothing().when(teamDao).deleteTeam(team1.getId());
        teamService.deleteTeam(team.getId());

        verify(teamDao,times(1)).getTeamById(1);
        verify(teamDao,times(1)).deleteTeam(team1.getId());

    }

    @Test
    public void testGetTeamById() {
        Team team = new Team();
        team.setId(1);
        team.setNom("New Team");
        when(teamDao.getTeamById(1)).thenReturn(team);
        Team team1 = teamService.getTeam(1);
        assert team1 != null;
        assert team1.getNom().equals("New Team");

        verify(teamDao,times(1)).getTeamById(1);
    }

    @Test
    public void testGetAllTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(teamDao.getAllTeams()).thenReturn(teams);
        List<Team> result = teamService.getTeams();

        assert result.size() == teams.size();
        verify(teamDao,times(1)).getAllTeams();
    }
}
