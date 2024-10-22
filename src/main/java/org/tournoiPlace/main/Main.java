package org.tournoiPlace.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.service.GameService;
import org.tournoiPlace.service.TournamentService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        TournamentService tournamentService = (TournamentService) context.getBean("tournamentService");
        GameService gameService = (GameService) context.getBean("gameService");

        Game newGame = new Game();
        newGame.setNom("Football");
        newGame.setDifficulte("Medium");
        newGame.setDureeMoyenneMatch(90.0);

        gameService.addGame(newGame);


        Tournament newTournament = new Tournament();
        newTournament.setTitre("Championship 2024");
        newTournament.setGame(newGame);
        newTournament.setDateDebut(LocalDate.now());
        newTournament.setDateFin(LocalDate.parse("2024-12-12"));
        newTournament.setStatut(Tournament.Statut.PLANIFIE);

        tournamentService.runSampleOperations();
        tournamentService.addTournament(newTournament);

        System.out.println(newTournament);

    }
}