package TournamentTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.TournamentService;

import javax.transaction.Transactional;
import java.time.LocalDate;


public class TournamentServiceIntegrationTest {
    private TournamentService tournamentService;
    private Tournament fechedTournament;

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationContext context = ApplicationContextProvider.getContext();
        tournamentService = (TournamentService) context.getBean("tournamentService");
    }

    @Test
    @Transactional
    public void testCreateAndFindTournament(){
        Tournament tournament = new Tournament();
        tournament.setTitre("test");
        tournament.setDateDebut(LocalDate.now());
        tournament.setDateFin(LocalDate.now());
        tournament.setStatut(Tournament.Statut.EN_COURS);

        tournamentService.addTournament(tournament);
        fechedTournament = tournamentService.getTournamentByName("test");
        assert  fechedTournament != null;
        assert fechedTournament.getTitre().equals(tournament.getTitre());
    }

    @AfterEach
    public void cleanup() {
        // Step 4: Clean up by deleting the tournament after the test
        if (fechedTournament != null && fechedTournament.getId() > 0) {
            tournamentService.deleteTournament(fechedTournament);
        }
    }
}
