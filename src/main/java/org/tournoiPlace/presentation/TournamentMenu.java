package org.tournoiPlace.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.GameService;
import org.tournoiPlace.service.TournamentService;
import org.tournoiPlace.utilitaire.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static org.tournoiPlace.utilitaire.InputValidator.*;

public class TournamentMenu {

    private static TournamentService tournamentService;
    private static GameService gameService;
    private static final Logger logger = LoggerFactory.getLogger(TournamentMenu.class);

    public static void showMenu(Scanner scanner) {
        ApplicationContext context = ApplicationContextProvider.getContext();
        tournamentService = (TournamentService) context.getBean("tournamentService");
        gameService = (GameService) context.getBean("gameService");


        int choice;

        do {
            System.out.println("\n=== Tournament Management Menu ===");
            System.out.println("1. Add a new tournament");
            System.out.println("2. Update an existing tournament");
            System.out.println("3. View a tournament by ID");
            System.out.println("4. View all tournaments");
            System.out.println("5. Delete a tournament");
            System.out.println("6. Calculate Estimated Duration of a Tournament");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTournament(scanner);
                    break;
                case 2:
                    updateTournament(scanner);
                    break;
                case 3:
                    viewTournamentById(scanner);
                    break;
                case 4:
                    viewAllTournaments();
                    break;
                case 5:
                    deleteTournament(scanner);
                    break;
                case 6:
                    calculateEstimatedDuration(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

    }

    private static void calculateEstimatedDuration(Scanner scanner) {
        System.out.println("\n=== Calculate Estimated Duration of a Tournament ===");
        List<Tournament> tournaments = tournamentService.getTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found. Please add a tournaments first.");
            return;
        }

        System.out.println("\nAvailable tournaments:");
        for (Tournament tournament : tournaments) {
            System.out.println("Tournament ID: " + tournament.getId() + ", Name: " + tournament.getTitre());
        }
        System.out.print("Enter the tournament ID to assign: ");
        int tournamentId = scanner.nextInt();
        scanner.nextLine();

        double durre_estimated = tournamentService.calculerDureeEstimeeTournoi(tournamentId);

        logger.info(" Estimated Duration of a Tournament is: " + durre_estimated);

        System.out.println(" Estimated Duration of a Tournament is: " + durre_estimated);
    }

    private static void addTournament(Scanner scanner) {
        System.out.println("\n=== Add New Tournament ===");

        // Step 1: Display existing games from the database
        System.out.println("Choose a game from the list or add a new one:");
        List<Game> games = gameService.getGames();

        if (games.isEmpty()) {
            System.out.println("No games available.");
        } else {
            for (int i = 0; i < games.size(); i++) {
                System.out.println((i + 1) + ". " + games.get(i).getNom());
            }
        }

        System.out.println((games.size() + 1) + ". Add a new game");
        int gameChoice = readValidPositiveInt("your choice");

        Game selectedGame;

        if (gameChoice == games.size() + 1) {
            // Add a new game
            String newGameName = readValidString("new game name");
            String newGameDifficulty = readValidString("difficulty (Easy/Medium/Hard)");
            double newGameDuration = readValidPositiveDouble("average match duration (in minutes)");

            selectedGame = new Game();
            selectedGame.setNom(newGameName);
            selectedGame.setDifficulte(newGameDifficulty);
            selectedGame.setDureeMoyenneMatch(newGameDuration);

            // Save the new game to the database
            gameService.addGame(selectedGame);
            System.out.println("New game added: " + selectedGame.getNom());
        } else if (gameChoice > 0 && gameChoice <= games.size()) {
            // Select an existing game
            selectedGame = games.get(gameChoice - 1);
            System.out.println("Selected game: " + selectedGame.getNom());
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        // Step 3: Collect the rest of the tournament details
        String title = readValidString("tournament title");

        LocalDate startDate = readValidDate("start date");
        LocalDate endDate = readValidDate("end date");

        // Step 4: Enum validation for Tournament Status
        System.out.println("Enter tournament status: ");
        for (Tournament.Statut statut : Tournament.Statut.values()) {
            System.out.println(statut);
        }

        Tournament.Statut status = null;
        while (status == null) {
            System.out.print("Enter tournament status (PLANIFIE/EN_COURS/TERMINE/ANNULE): ");
            String statusInput = scanner.nextLine();
            scanner.next();
            try {
                status = Tournament.Statut.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter a valid status.");
            }
        }

        double tempsPauseEntreMatchs = readValidPositiveDouble("break time between matches (in minutes)");

        String nombreDeSpectateurs = readValidString("number of spectators");

        double tempsCeremonie = readValidPositiveDouble("ceremony time (in minutes)");

        // Step 6: Create the tournament and save it to the database
        Tournament tournament = new Tournament();
        tournament.setTitre(title);
        tournament.setGame(selectedGame);
        tournament.setDateDebut(startDate);
        tournament.setDateFin(endDate);
        tournament.setStatut(status);
        tournament.setTempsPauseEntreMatchs(tempsPauseEntreMatchs);
        tournament.setNombreDeSpectateurs(nombreDeSpectateurs);
        tournament.setTempsCeremone(tempsCeremonie);

        tournamentService.addTournament(tournament);
        System.out.println("Tournament added successfully!");
    }


    private static void updateTournament(Scanner scanner) {
        System.out.println("\n=== Update Tournament ===");


        int id = InputValidator.readValidPositiveInt("Tournament ID");
        Tournament existingTournament = tournamentService.getTournament(id);
        if (existingTournament == null) {
            System.out.println("Tournament with ID " + id + " not found.");
            return;
        }


        System.out.println("Choose a game from the list or add a new one:");
        List<Game> games = gameService.getGames();

        if (games.isEmpty()) {
            System.out.println("No games available.");
        } else {
            for (int i = 0; i < games.size(); i++) {
                System.out.println((i + 1) + ". " + games.get(i).getNom());
            }
        }
        System.out.println((games.size() + 1) + ". Add a new game");
        int gameChoice = InputValidator.readValidPositiveInt("your choice");

        Game selectedGame;

        if (gameChoice == games.size() + 1) {
            String newGameName = InputValidator.readValidString("new game name");
            String newGameDifficulty = InputValidator.readValidString("difficulty (Easy/Medium/Hard)");
            double newGameDuration = InputValidator.readValidPositiveDouble("average match duration (in minutes)");

            selectedGame = new Game();
            selectedGame.setNom(newGameName);
            selectedGame.setDifficulte(newGameDifficulty);
            selectedGame.setDureeMoyenneMatch(newGameDuration);

            gameService.addGame(selectedGame);
            System.out.println("New game added: " + selectedGame.getNom());
        } else if (gameChoice > 0 && gameChoice <= games.size()) {
            selectedGame = games.get(gameChoice - 1);
            System.out.println("Selected game: " + selectedGame.getNom());
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }


        System.out.print("Enter new tournament title (or press Enter to leave unchanged): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.trim().isEmpty() && InputValidator.validateString(newTitle, "Tournament Title")) {
            existingTournament.setTitre(newTitle);
        }


        System.out.print("Enter new start date (YYYY-MM-DD) (or press Enter to leave unchanged): ");
        String newStartDate = scanner.nextLine();
        if (!newStartDate.trim().isEmpty() && InputValidator.validateDate(newStartDate, "Start Date")) {
            existingTournament.setDateDebut(LocalDate.parse(newStartDate));
        }


        System.out.print("Enter new end date (YYYY-MM-DD) (or press Enter to leave unchanged): ");
        String newEndDate = scanner.nextLine();
        if (!newEndDate.trim().isEmpty() && InputValidator.validateDate(newEndDate, "End Date")) {
            existingTournament.setDateFin(LocalDate.parse(newEndDate));
        }


        System.out.print("Enter new tournament status (PLANIFIE/EN_COURS/TERMINE/ANNULE) (or press Enter to leave unchanged): ");
        String newStatus = scanner.nextLine();
        if (!newStatus.trim().isEmpty()) {
            try {
                existingTournament.setStatut(Tournament.Statut.valueOf(newStatus.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter a valid status.");
                return;
            }
        }


        existingTournament.setGame(selectedGame);

        tournamentService.updateTournament(existingTournament);
        System.out.println("Tournament updated successfully!");
    }


    private static void viewTournamentById(Scanner scanner) {
        System.out.println("\n=== View Tournament by ID ===");

        System.out.print("Enter the tournament ID: ");
        int id = InputValidator.readValidPositiveInt("Tournament ID");;

        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            System.out.println("Tournament ID: " + tournament.getId());
            System.out.println("Title: " + tournament.getTitre());
            System.out.println("Game: " + tournament.getGame().getNom());
            System.out.println("Start Date: " + tournament.getDateDebut());
            System.out.println("End Date: " + tournament.getDateFin());
            System.out.println("Status: " + tournament.getStatut());
        } else {
            System.out.println("Tournament with ID " + id + " not found.");
        }
    }

    private static void viewAllTournaments() {
        System.out.println("\n=== View All Tournaments ===");

        List<Tournament> tournaments = tournamentService.getTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found.");
        } else {
            for (Tournament tournament : tournaments) {
                System.out.println("Tournament ID: " + tournament.getId() +
                        ", Title: " + (tournament.getTitre() != null ? tournament.getTitre() : "N/A") +
                        ", Game: " + (tournament.getGame() != null && tournament.getGame().getNom() != null ? tournament.getGame().getNom() : "N/A") +
                        ", Start Date: " + (tournament.getDateDebut() != null ? tournament.getDateDebut() : "N/A") +
                        ", End Date: " + (tournament.getDateFin() != null ? tournament.getDateFin() : "N/A") +
                        ", Spectator Number: " + tournament.getNombreDeSpectateurs() +
                        ", Ceremonie Duration: " + tournament.getTempsCeremonie() +
                        ", Pause Between Matches: " + tournament.getTempsPauseEntreMatchs() +
                        ", Status: " + tournament.getStatut());
            }
        }
    }

    private static void deleteTournament(Scanner scanner) {
        System.out.println("\n=== Delete Tournament ===");

        System.out.print("Enter the tournament ID to delete: ");
        int id = InputValidator.readValidPositiveInt("Tournament ID");;
        Tournament tournament = tournamentService.getTournament(id);

        tournamentService.deleteTournament(tournament);
        System.out.println("Tournament deleted successfully!");
    }
}