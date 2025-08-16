package minesweeper;

import java.util.*;

public class MineSweeperGame {
    private final int size;
    private final int mineCount;
    private final Cell[][] grid;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private int revealedCount = 0;

    public MineSweeperGame(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
        placeMines();
        calculateAdjacents();
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (!grid[r][c].isMine) {
                grid[r][c].isMine = true;
                placed++;
            }
        }
    }

    private void calculateAdjacents() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c].isMine) continue;
                int count = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr, nc = c + dc;
                        if (nr >= 0 && nr < size && nc >= 0 && nc < size && grid[nr][nc].isMine) {
                            count++;
                        }
                    }
                }
                grid[r][c].adjacentMines = count;
            }
        }
    }

    public boolean reveal(String input) {
        if (input.length() < 2) return false;
        int row = input.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            return false;
        }
        if (row < 0 || row >= size || col < 0 || col >= size) return false;
        Cell cell = grid[row][col];
        if (cell.revealed) return false;
        cell.revealed = true;
        revealedCount++;
        if (cell.isMine) {
            gameOver = true;
            return true;
        }
        if (cell.adjacentMines == 0) {
            revealAdjacent(row, col);
        }
        if (revealedCount == size * size - mineCount) {
            gameWon = true;
        }
        return true;
    }

    private void revealAdjacent(int row, int col) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = row + dr, nc = col + dc;
                if (nr >= 0 && nr < size && nc >= 0 && nc < size) {
                    Cell cell = grid[nr][nc];
                    if (!cell.revealed && !cell.isMine) {
                        cell.revealed = true;
                        revealedCount++;
                        if (cell.adjacentMines == 0) {
                            revealAdjacent(nr, nc);
                        }
                    }
                }
            }
        }
    }

    public String displayGrid() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int i = 1; i <= size; i++) sb.append(i + " ");
        sb.append("\n");
        for (int r = 0; r < size; r++) {
            sb.append((char)('A' + r)).append(" ");
            for (int c = 0; c < size; c++) {
                Cell cell = grid[r][c];
                if (!cell.revealed) {
                    sb.append("_ ");
                } else if (cell.isMine) {
                    sb.append("* ");
                } else {
                    sb.append(cell.adjacentMines + " ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }

    private static class Cell {
        boolean isMine = false;
        boolean revealed = false;
        int adjacentMines = 0;
    }
}
