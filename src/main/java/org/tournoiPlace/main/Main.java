package org.tournoiPlace.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Tournament;
import org.tournoiPlace.service.GameService;
import org.tournoiPlace.service.TournamentService;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        displayMainMenu(scanner);
        scanner.close();
    }
    private static void displayMainMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Tournament Menu");
            System.out.println("2. Game Menu");
            System.out.println("3. Team Menu");
            System.out.println("4. Team Menu");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    TournamentMenu.showMenu(scanner);
                    break;
                case 2:
                    GameMenu.showMenu(scanner);
                    break;
                case 3:
                    TeamMenu.showMenu(scanner);
                    break;
                case 4:
                    PlayerMenu.showMenu(scanner);
                case 5:
                    exit = true;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}