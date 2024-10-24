package TournamentTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tournoiPlace.dao.TournamentDao;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.service.TournamentService;
import org.tournoiPlace.service.impl.TournamentServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TournamentServiceTest {
    @Mock
    private TournamentDao tournamentDao;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTournament() {
        Tournament tournament = new Tournament();
        tournament.setTitre("test");
        tournament.setDateDebut(LocalDate.now());
        tournament.setDateFin(LocalDate.now());
        tournament.setStatut(Tournament.Statut.EN_COURS);
        doNothing().when(tournamentDao).addTournament(tournament);
        tournamentService.addTournament(tournament);
        verify(tournamentDao, times(1)).addTournament(tournament);
    }
    @Test
    public void testGetTournamentById() {
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setTitre("testUpdate");

        when(tournamentDao.getTournament(1)).thenReturn(tournament);
        Tournament tournament1 = tournamentService.getTournament(1);
        assert tournament1 != null;
        assert tournament1.getTitre().equals("testUpdate");

        verify(tournamentDao, times(1)).getTournament(1);
    }

    @Test
    public void TestDeleteTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(1);

        when(tournamentDao.getTournament(1)).thenReturn(tournament);
        Tournament tournament1 = tournamentService.getTournament(1);
        doNothing().when(tournamentDao).deleteTournament(tournament1);
        tournamentService.deleteTournament(tournament1);

        verify(tournamentDao, times(1)).getTournament(1);
        verify(tournamentDao, times(1)).deleteTournament(tournament1);
    }
}
