package TeamTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.TeamService;

import javax.transaction.Transactional;

public class TeamServiceIntegrationTest {
    private TeamService teamService;


    @Before
    public void setUp() throws Exception {
        ApplicationContext context = ApplicationContextProvider.getContext();
        teamService = (TeamService) context.getBean("teamService");
    }

    @Test
    @Transactional
    public void testCreateAndFindTournament() {
        Team team = new Team();
        team.setNom("Team_1");
        team.setClassement(2);

        teamService.addTeam(team);
        Team fetchedTeam = teamService.getTeamByName("Team_1");
        assert fetchedTeam != null;
        assert fetchedTeam.getClassement() == 2;
    }
}
