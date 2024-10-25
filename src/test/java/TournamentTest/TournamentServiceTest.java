package TournamentTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tournoiPlace.dao.TournamentDao;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.service.TournamentService;
import org.tournoiPlace.service.impl.TournamentServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TournamentServiceTest {
    @Mock
    private TournamentDao tournamentDao;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @BeforeEach
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


    @Test
    public void testUpdateTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setTitre("updatedTitle");
        tournament.setDateDebut(LocalDate.now());
        tournament.setDateFin(LocalDate.now());
        tournament.setStatut(Tournament.Statut.TERMINE);

        doNothing().when(tournamentDao).updateTournament(tournament);
        tournamentService.updateTournament(tournament);

        verify(tournamentDao, times(1)).updateTournament(tournament);
    }
    @Test
    public void testGetTournaments() {
        List<Tournament> tournamentList = new ArrayList<>();
        tournamentList.add(new Tournament());
        tournamentList.add(new Tournament());

        when(tournamentDao.getTournaments()).thenReturn(tournamentList);
        List<Tournament> result = tournamentService.getTournaments();

        assert result.size() == 2;
        verify(tournamentDao, times(1)).getTournaments();
    }
    @Test
    public void testCalculerDureeEstimeeTournoi() {
        int tournoiId = 1;
        double expectedDuration = 120.0;

        when(tournamentDao.calculerDureeEstimeeTournoi(tournoiId)).thenReturn(expectedDuration);
        double result = tournamentService.calculerDureeEstimeeTournoi(tournoiId);

        assert result == expectedDuration;
        verify(tournamentDao, times(1)).calculerDureeEstimeeTournoi(tournoiId);
    }

    @Test
    public void testGetTournamentByName() {
        String name = "Champions League";
        Tournament tournament = new Tournament();
        tournament.setTitre(name);

        when(tournamentDao.findByName(name)).thenReturn(tournament);
        Tournament result = tournamentService.getTournamentByName(name);

        assert result.getTitre().equals(name);
        verify(tournamentDao, times(1)).findByName(name);
    }
}
