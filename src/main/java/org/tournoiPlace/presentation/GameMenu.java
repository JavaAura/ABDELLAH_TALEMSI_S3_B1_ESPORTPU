package org.tournoiPlace.presentation;
import org.springframework.context.ApplicationContext;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.GameService;

import java.util.List;
import java.util.Scanner;

import static org.tournoiPlace.utilitaire.InputValidator.*;

public class GameMenu {
    private static GameService gameService;

    public static void showMenu(Scanner scanner) {
        ApplicationContext context = ApplicationContextProvider.getContext();
        gameService = (GameService) context.getBean("gameService");

        int choice;

        do {
            System.out.println("\n=== Game Management Menu ===");
            System.out.println("1. Add a new game");
            System.out.println("2. Update an existing game");
            System.out.println("3. View a game by ID");
            System.out.println("4. View all games");
            System.out.println("5. Delete a game");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addGame(scanner);
                    break;
                case 2:
                    updateGame(scanner);
                    break;
                case 3:
                    viewGameById(scanner);
                    break;
                case 4:
                    viewAllGames();
                    break;
                case 5:
                    deleteGame(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addGame(Scanner scanner) {
        System.out.println("\n=== Add New Game ===");

        // Use your validation method for game name
        String name = readValidString("game name");

        // Use your validation method for difficulty
        String difficulty;
        do {
            System.out.print("Enter difficulty (Easy/Medium/Hard): ");
            difficulty = scanner.nextLine();
            if (!validateDifficulty(difficulty)) {
                System.out.println("Invalid difficulty! Please enter one of: Easy, Medium, Hard.");
            }
        } while (!validateDifficulty(difficulty));

        // Use your validation method for average match duration
        double duration = readValidPositiveDouble("average match duration (in minutes)");

        // Create and add the new game
        Game newGame = new Game();
        newGame.setNom(name);
        newGame.setDifficulte(difficulty);
        newGame.setDureeMoyenneMatch(duration);

        gameService.addGame(newGame);
        System.out.println("Game added successfully!");
    }

    private static void updateGame(Scanner scanner) {
        System.out.println("\n=== Update Game ===");

        System.out.print("Enter the game ID to update: ");
        int id = readValidPositiveInt("game ID");

        Game existingGame = gameService.getGame(id);
        if (existingGame == null) {
            System.out.println("Game with ID " + id + " not found.");
            return;
        }

        // Use your validation method for new game name
        String newName = readValidString("new game name");

        String newDifficulty;
        do {
            System.out.print("Enter new difficulty (Easy/Medium/Hard): ");
            newDifficulty = scanner.nextLine();
            if (!validateDifficulty(newDifficulty)) {
                System.out.println("Invalid difficulty! Please enter one of: Easy, Medium, Hard.");
            }
        } while (!validateDifficulty(newDifficulty));

        double newDuration = readValidPositiveDouble("new average match duration (in minutes)");
        existingGame.setNom(newName);
        existingGame.setDifficulte(newDifficulty);
        existingGame.setDureeMoyenneMatch(newDuration);

        gameService.updateGame(existingGame);
        System.out.println("Game updated successfully!");
    }

    private static void viewGameById(Scanner scanner) {
        System.out.println("\n=== View Game by ID ===");

        System.out.print("Enter the game ID: ");
        int id = scanner.nextInt();

        Game game = gameService.getGame(id);
        if (game != null) {
            System.out.println("Game ID: " + game.getId());
            System.out.println("Name: " + game.getNom());
            System.out.println("Difficulty: " + game.getDifficulte());
            System.out.println("Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
        } else {
            System.out.println("Game with ID " + id + " not found.");
        }
    }
    private static void deleteGame(Scanner scanner) {
        System.out.println("\n=== Delete Game ===");

        System.out.print("Enter the game ID to delete: ");
        int id = scanner.nextInt();
        Game game = gameService.getGame(id);

        if (game != null) {
            gameService.deleteGame(game.getId());
            System.out.println("Game deleted successfully!");
        } else {
            System.out.println("Game with ID " + id + " not found.");
        }
    }
    private static void viewAllGames() {
        System.out.println("\n=== View All Games ===");

        List<Game> games = gameService.getGames();
        if (games.isEmpty()) {
            System.out.println("No games found.");
        } else {
            for (Game game : games) {
                System.out.println("Game ID: " + game.getId() +
                        ", Name: " + game.getNom() +
                        ", Difficulty: " + game.getDifficulte() +
                        ", Average Match Duration: " + game.getDureeMoyenneMatch() + " minutes");
            }
        }
    }
}
