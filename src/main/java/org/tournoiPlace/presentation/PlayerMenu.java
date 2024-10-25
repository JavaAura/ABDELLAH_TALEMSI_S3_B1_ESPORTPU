package org.tournoiPlace.presentation;

import org.springframework.context.ApplicationContext;
import org.tournoiPlace.model.Player;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.PlayerService;
import org.tournoiPlace.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

import static org.tournoiPlace.utilitaire.InputValidator.readValidPositiveInt;
import static org.tournoiPlace.utilitaire.InputValidator.readValidString;

public class PlayerMenu {
    private static PlayerService playerService;
    private static TeamService teamService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerMenu.class);

    public static void showMenu(Scanner scanner) {
        ApplicationContext context = ApplicationContextProvider.getContext();
        playerService = (PlayerService) context.getBean("playerService");
        teamService = (TeamService) context.getBean("teamService");
        int choice;

        do {
            logger.info("logger initialized");
            System.out.println("\n=== Player Management Menu ===");
            System.out.println("1. Add a new player");
            System.out.println("2. Update an existing player");
            System.out.println("3. View a player by ID");
            System.out.println("4. View all players");
            System.out.println("5. Delete a player");
            System.out.println("6. Assign a player to a team");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPlayer(scanner);
                    break;
                case 2:
                    updatePlayer(scanner);
                    break;
                case 3:
                    viewPlayerById(scanner);
                    break;
                case 4:
                    viewAllPlayers();
                    break;
                case 5:
                    deletePlayer(scanner);
                    break;
                case 6:
                    assignPlayerToTeam(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
    private static void addPlayer(Scanner scanner) {
        System.out.println("\n=== Add New Player ===");

        String pseudo = readValidString("player pseudo");
        int age = readValidPositiveInt("player age");

        Player newPlayer = new Player();
        newPlayer.setPseudo(pseudo);
        newPlayer.setAge(String.valueOf(age));  // assuming age is stored as a String

        playerService.addPlayer(newPlayer);
        System.out.println("Player added successfully!");
    }

    private static void updatePlayer(Scanner scanner) {
        System.out.println("\n=== Update Player ===");

        int id = readValidPositiveInt("player ID to update");

        Player existingPlayer = playerService.getPlayer(id);
        if (existingPlayer == null) {
            System.out.println("Player with ID " + id + " not found.");
            return;
        }

        String newPseudo = readValidString("new player pseudo");
        int newAge = readValidPositiveInt("new player age");

        existingPlayer.setPseudo(newPseudo);
        existingPlayer.setAge(String.valueOf(newAge));

        playerService.updatePlayer(existingPlayer);
        System.out.println("Player updated successfully!");
    }

    private static void viewPlayerById(Scanner scanner) {
        System.out.println("\n=== View Player by ID ===");

        int id = readValidPositiveInt("player ID");

        Player player = playerService.getPlayer(id);
        if (player != null) {
            System.out.println("Player ID: " + player.getId());
            System.out.println("Pseudo: " + player.getPseudo());
            System.out.println("Age: " + player.getAge());
            if (player.getTeam() != null) {
                System.out.println("Team: " + player.getTeam().getNom());
            } else {
                System.out.println("Team: Not assigned");
            }
        } else {
            System.out.println("Player with ID " + id + " not found.");
        }
    }

    private static void viewAllPlayers() {
        System.out.println("\n=== View All Players ===");

        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            for (Player player : players) {
                System.out.println("Player ID: " + player.getId() +
                        ", Pseudo: " + player.getPseudo() +
                        ", Age: " + player.getAge() +
                        ", Team: " + (player.getTeam() != null ? player.getTeam().getNom() : "Not assigned"));
            }
        }
    }

    private static void deletePlayer(Scanner scanner) {
        System.out.println("\n=== Delete Player ===");

        int id = readValidPositiveInt("player ID to delete");

        Player player = playerService.getPlayer(id);

        if (player != null) {
            playerService.deletePlayer(player.getId());
            System.out.println("Player deleted successfully!");
        } else {
            System.out.println("Player with ID " + id + " not found.");
        }
    }

    private static void assignPlayerToTeam(Scanner scanner) {
        System.out.println("\n=== Assign Player to Team ===");
        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found. Please add players first.");
            return;
        }

        System.out.println("\nAvailable players:");
        for (Player player : players) {
            System.out.println("Player ID: " + player.getId() + ", Pseudo: " + player.getPseudo());
        }

        int playerId = readValidPositiveInt("player ID to assign");
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("Player with ID " + playerId + " not found.");
            return;
        }

        List<Team> teams = teamService.getTeams();  // Assuming this method exists in your TeamService
        if (teams.isEmpty()) {
            System.out.println("No teams found. Please add a team first.");
            return;
        }

        System.out.println("\nAvailable Teams:");
        for (Team team : teams) {
            System.out.println("Team ID: " + team.getId() + ", Name: " + team.getNom());
        }

        int teamId = readValidPositiveInt("team ID to assign the player to");
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        player.setTeam(team);
        playerService.updatePlayer(player);
        System.out.println("Player " + player.getPseudo() + " has been assigned to team " + team.getNom() + " successfully!");
    }

}
