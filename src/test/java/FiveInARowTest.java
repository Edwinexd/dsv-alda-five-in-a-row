import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.edsv.FiveInARow;
import com.edsv.MoveInfo;
import com.edsv.MoveResult;
import com.edsv.Placement;

public class FiveInARowTest {
    @Test
    public void testEmptyBoard() {
        FiveInARow game = new FiveInARow(true);
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                assertTrue(game.isEmpty(x, y));
            }
        }
    }

    @Test
    public void testPlace() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        assertFalse(game.isEmpty(0, 0));
    }

    @Test
    public void testPlaceTwoPlayerThrows() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        assertThrows(IllegalArgumentException.class, () -> game.place(0, 1, Placement.PLAYER));
    }

    @Test
    public void testPlaceTwoComputerThrows() {
        FiveInARow game = new FiveInARow(false);
        game.place(0, 0, Placement.COMPUTER);
        assertThrows(IllegalArgumentException.class, () -> game.place(0, 1, Placement.COMPUTER));
    }

    @Test
    public void testHorizontalVictory() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        game.place(0, 14, Placement.COMPUTER);
        game.place(1, 0, Placement.PLAYER);
        game.place(1, 14, Placement.COMPUTER);
        game.place(2, 0, Placement.PLAYER);
        game.place(2, 14, Placement.COMPUTER);
        game.place(3, 0, Placement.PLAYER);
        game.place(3, 14, Placement.COMPUTER);
        game.place(4, 0, Placement.PLAYER);
        assertTrue(game.isVictoryMove(4, 0));
    }

    @Test
    public void testHorizontalNoVictory() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        game.place(0, 14, Placement.COMPUTER);
        game.place(1, 0, Placement.PLAYER);
        game.place(1, 14, Placement.COMPUTER);
        game.place(2, 0, Placement.PLAYER);
        game.place(2, 14, Placement.COMPUTER);
        game.place(3, 0, Placement.PLAYER);
        game.place(3, 14, Placement.COMPUTER);
        assertFalse(game.isVictoryMove(3, 0));
    }

    @Test
    public void testDiagonalVictory() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        game.place(1, 1, Placement.COMPUTER);
        game.place(1, 0, Placement.PLAYER);
        game.place(2, 2, Placement.COMPUTER);
        game.place(2, 0, Placement.PLAYER);
        game.place(3, 3, Placement.COMPUTER);
        game.place(3, 0, Placement.PLAYER);
        game.place(4, 4, Placement.COMPUTER);
        game.place(14, 0, Placement.PLAYER);
        game.place(5, 5, Placement.COMPUTER);
        assertTrue(game.isVictoryMove(4, 4));
    }

    @Test
    public void testImmediatePlayerWin() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        game.place(0, 14, Placement.COMPUTER);
        game.place(1, 0, Placement.PLAYER);
        game.place(1, 14, Placement.COMPUTER);
        game.place(2, 0, Placement.PLAYER);
        game.place(2, 14, Placement.COMPUTER);
        game.place(3, 0, Placement.PLAYER);
        game.place(3, 14, Placement.COMPUTER);
        // game.place(4, 0, Placement.PLAYER);
        assertTrue(game.isPlayerTurn());
        MoveInfo p = game.immediatePlayerWin();
        assertEquals(4, p.x());
        assertEquals(0, p.y());
        assertEquals(MoveResult.PLAYER_WIN, p.result());
    }

    @Test
    public void testNoImmediatePlayerWinExists() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        game.place(0, 14, Placement.COMPUTER);
        game.place(1, 0, Placement.PLAYER);
        game.place(1, 14, Placement.COMPUTER);
        game.place(2, 0, Placement.PLAYER);
        game.place(2, 14, Placement.COMPUTER);
        game.place(0, 1, Placement.PLAYER);
        game.place(3, 14, Placement.COMPUTER);
        assertNull(game.immediatePlayerWin());
    }

    @Test
    public void testIsPlayerTurn() {
        FiveInARow game = new FiveInARow(true);
        assertTrue(game.isPlayerTurn());
        game.place(0, 0, Placement.PLAYER);
        assertFalse(game.isPlayerTurn());
    }
}
