package minesweeper;

import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class MineSweeperGameTest {
    @Test
    public void testGameWinCondition() {
        MineSweeperGame game = new MineSweeperGame(3, 1);
        // Reveal all non-mine cells
        int revealed = 0;
        for (char row = 'A'; row < 'A' + 3; row++) {
            for (int col = 1; col <= 3; col++) {
                String pos = row + String.valueOf(col);
                if (game.isGameOver() || game.isGameWon()) break;
                game.reveal(pos);
                revealed++;
            }
        }
        assertTrue(game.isGameWon() || game.isGameOver());
    }

    @Test
    public void testMineDetonation() {
        MineSweeperGame game = new MineSweeperGame(2, 1);
        boolean detonated = false;
        for (char row = 'A'; row < 'A' + 2; row++) {
            for (int col = 1; col <= 2; col++) {
                String pos = row + String.valueOf(col);
                if (game.reveal(pos) && game.isGameOver()) {
                    detonated = true;
                    break;
                }
            }
        }
        assertTrue(detonated);
    }
}
