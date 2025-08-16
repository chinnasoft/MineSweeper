package minesweeper;

import java.util.Scanner;

public class MineSweeperCLI {
    private final Scanner scanner = new Scanner(System.in);
    private MineSweeperGame game;

    public void start() {
        System.out.println("Welcome to Minesweeper!\n");
        while (true) {
            int size = promptGridSize();
            int mines = promptMineCount(size);
            game = new MineSweeperGame(size, mines);
            playGame();
            System.out.println("Press Enter to play again...");
            scanner.nextLine();
        }
    }

    private int promptGridSize() {
        System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        return Integer.parseInt(scanner.nextLine());
    }

    private int promptMineCount(int size) {
        int maxMines = (int)Math.floor(size * size * 0.35);
        System.out.print("Enter the number of mines to place on the grid (maximum is " + maxMines + "): ");
        int mines = Integer.parseInt(scanner.nextLine());
        return Math.min(mines, maxMines);
    }

    private void playGame() {
        while (!game.isGameOver() && !game.isGameWon()) {
            System.out.println("\nHere is your minefield:");
            System.out.println(game.displayGrid());
            System.out.print("Select a square to reveal (e.g. A1): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!game.reveal(input)) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            if (game.isGameOver()) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                break;
            }
            if (game.isGameWon()) {
                System.out.println("Congratulations, you have won the game!");
                break;
            }
        }
    }
}
