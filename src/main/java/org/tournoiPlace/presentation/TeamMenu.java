package org.tournoiPlace.presentation;

import org.springframework.context.ApplicationContext;
import org.tournoiPlace.model.Player;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.provider.ApplicationContextProvider;
import org.tournoiPlace.service.PlayerService;
import org.tournoiPlace.service.TeamService;
import org.tournoiPlace.service.TournamentService;

import java.util.List;
import java.util.Scanner;

import static org.tournoiPlace.utilitaire.InputValidator.readValidPositiveInt;
import static org.tournoiPlace.utilitaire.InputValidator.readValidString;

public class TeamMenu {
    private static TeamService teamService;
    private static TournamentService tournamentService;
    private static PlayerService playerService;

    public static void showMenu(Scanner scanner) {
        ApplicationContext context = ApplicationContextProvider.getContext();
        teamService = (TeamService) context.getBean("teamService");
        tournamentService = (TournamentService) context.getBean("tournamentService");
        playerService = (PlayerService) context.getBean("playerService");


        int choice;

        do {
            System.out.println("\n=== Team Management Menu ===");
            System.out.println("1. Add a new team");
            System.out.println("2. Update an existing team");
            System.out.println("3. View a team by ID");
            System.out.println("4. View all teams");
            System.out.println("5. Delete a team");
            System.out.println("6. Assign a Team to Tournament");
            System.out.println("7. Assign a Player to a Team");
            System.out.println("8. Retirer un joueur de l'équipe"); // Nouvelle option
            System.out.println("9. Retirer une équipe du tournoi"); // Nouvelle option
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTeam(scanner);
                    break;
                case 2:
                    updateTeam(scanner);
                    break;
                case 3:
                    viewTeamById(scanner);
                    break;
                case 4:
                    viewAllTeams();
                    break;
                case 5:
                    deleteTeam(scanner);
                    break;
                case 6:
                    System.out.print("Enter the team ID to assign: ");
                    int id = scanner.nextInt();
                    assignTournamentToTeam(scanner, teamService.getTeam(id));
                    break;
                case 7:  // Assign player to team
                    assignPlayerToTeam(scanner);
                    break;
                case 8:
                    retirerPlayerFromTeam(scanner);
                    break;
                case 9:
                    retirerTeamFromTournament(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

    }

    private static void addTeam(Scanner scanner) {
        System.out.println("\n=== Add New Team ===");


        String name = readValidString("team name");


        int classement = readValidPositiveInt("team rank");

        Team newTeam = new Team();
        newTeam.setNom(name);
        newTeam.setClassement(classement);

        System.out.print("Assign a tournament to this team? (y/n): ");
        String assignTournament = scanner.nextLine();
        if (assignTournament.equalsIgnoreCase("y")) {
            assignTournamentToTeam(scanner, newTeam);
        }

        teamService.addTeam(newTeam);
        System.out.println("Team added successfully!");
    }


    private static void updateTeam(Scanner scanner) {
        System.out.println("\n=== Update Team ===");

        // Using readValidPositiveInt to validate team ID input
        int id = readValidPositiveInt("team ID to update");

        Team existingTeam = teamService.getTeam(id);
        if (existingTeam == null) {
            System.out.println("Team with ID " + id + " not found.");
            return;
        }

        String newName = readValidString("new team name (leave blank to keep current)");
        if (!newName.trim().isEmpty()) {
            existingTeam.setNom(newName);
        }

        System.out.print("Update tournament assignment? (y/n): ");
        String updateTournament = scanner.nextLine();
        if (updateTournament.equalsIgnoreCase("y")) {
            assignTournamentToTeam(scanner, existingTeam);
        }

        teamService.updateTeam(existingTeam);
        System.out.println("Team updated successfully!");
    }


    private static void viewTeamById(Scanner scanner) {
        System.out.println("\n=== View Team by ID ===");

        // Using readValidPositiveInt to validate team ID input
        int id = readValidPositiveInt("team ID");

        Team team = teamService.getTeam(id);
        if (team != null) {
            System.out.println("Team ID: " + team.getId());
            System.out.println("Name: " + team.getNom());

            List<Player> players = team.getPlayers();
            if (players != null && !players.isEmpty()) {
                System.out.println("Players:");
                for (Player player : players) {
                    System.out.println(" - " + player.getPseudo());
                }
            } else {
                System.out.println("No players assigned.");
            }

            if (team.getTournament() != null) {
                System.out.println("Tournament: " + team.getTournament().getTitre());
            } else {
                System.out.println("No tournament assigned.");
            }

            System.out.println("Classement: " + (team.getClassement() != null ? team.getClassement() : "Not ranked"));
        } else {
            System.out.println("Team with ID " + id + " not found.");
        }
    }

    private static void deleteTeam(Scanner scanner) {
        System.out.println("\n=== Delete Team ===");

        // Using readValidPositiveInt to validate team ID input
        int id = readValidPositiveInt("team ID to delete");

        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(id);
            System.out.println("Team deleted successfully!");
        } else {
            System.out.println("Team with ID " + id + " not found.");
        }
    }

    private static void viewAllTeams() {
        System.out.println("\n=== View All Teams ===");

        List<Team> teams = teamService.getTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            for (Team team : teams) {
                System.out.println("Team ID: " + team.getId() + ", Name: " + team.getNom());
            }
        }
    }
    private static void assignTournamentToTeam(Scanner scanner, Team team) {
        List<Tournament> tournaments = tournamentService.getTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found. Please add a tournaments first.");
            return;
        }

        System.out.println("\nAvailable tournaments:");
        for (Tournament tournament : tournaments) {
            System.out.println("Team ID: " + tournament.getId() + ", Name: " + tournament.getTitre());
        }
        System.out.print("Enter the tournament ID to assign: ");
        int tournamentId = readValidPositiveInt("tournament ID to assign");
        scanner.nextLine();

        team.setTournament(tournamentService.getTournament(tournamentId));
        if (team.getTournament() != null) {
            System.out.println("Tournament assigned: " + team.getTournament().getTitre());
        } else {
            System.out.println("Invalid tournament ID.");
        }
    }
    private static void assignPlayerToTeam(Scanner scanner) {
        System.out.println("\n=== Assign Player to Team ===");

        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found. Please add a players first.");
            return;
        }

        System.out.println("\nAvailable players:");
        for (Player player : players) {
            System.out.println("Player ID: " + player.getId() + ", Name: " + player.getPseudo());
        }

        // Using readValidPositiveInt to validate player ID input
        int playerId = readValidPositiveInt("player ID to assign");
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("Player with ID " + playerId + " not found.");
            return;
        }

        List<Team> teams = teamService.getTeams();
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

    private static void retirerPlayerFromTeam(Scanner scanner) {
        System.out.println("\n=== Retirer Player de l'Équipe ===");

        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println("Aucun joueur trouvé. Veuillez d'abord ajouter des joueurs.");
            return;
        }

        System.out.println("\nJoueurs disponibles :");
        for (Player player : players) {
            System.out.println("ID Joueur : " + player.getId() + ", Nom : " + player.getPseudo());
        }

        int playerId = readValidPositiveInt("ID du joueur à retirer");
        Player player = playerService.getPlayer(playerId);

        if (player == null) {
            System.out.println("Joueur avec l'ID " + playerId + " non trouvé.");
            return;
        }


        if (player.getTeam() == null) {
            System.out.println("Le joueur " + player.getPseudo() + " n'est pas assigné à une équipe.");
            return;
        }

        player.setTeam(null);
        playerService.updatePlayer(player);
        System.out.println("Le joueur " + player.getPseudo() + " a été retiré de son équipe avec succès !");
    }

    private static void retirerTeamFromTournament(Scanner scanner) {
        System.out.println("\n=== Retirer Équipe du Tournoi ===");

        List<Tournament> tournaments = tournamentService.getTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("Aucun tournoi trouvé. Veuillez d'abord ajouter des tournois.");
            return;
        }

        System.out.println("\nTournois disponibles :");
        for (Tournament tournament : tournaments) {
            System.out.println("ID Tournoi : " + tournament.getId() + ", Titre : " + tournament.getTitre());
        }

        int tournamentId = readValidPositiveInt("ID du tournoi duquel retirer une équipe");
        Tournament tournament = tournamentService.getTournament(tournamentId);

        if (tournament == null) {
            System.out.println("Tournoi avec l'ID " + tournamentId + " non trouvé.");
            return;
        }


        List<Team> teams = tournament.getTeams();
        if (teams.isEmpty()) {
            System.out.println("Aucune équipe n'est associée au tournoi " + tournament.getTitre());
            return;
        }

        System.out.println("\nÉquipes disponibles :");
        for (Team team : teams) {
            System.out.println("ID Équipe : " + team.getId() + ", Nom : " + team.getNom());
        }

        int teamId = readValidPositiveInt("ID de l'équipe à retirer du tournoi");
        Team team = teamService.getTeam(teamId);

        if (team == null) {
            System.out.println("Équipe avec l'ID " + teamId + " non trouvée.");
            return;
        }


        team.setTournament(null);
        teamService.updateTeam(team);
        System.out.println("L'équipe " + team.getNom() + " a été retirée du tournoi " + tournament.getTitre() + " avec succès !");
    }



}
