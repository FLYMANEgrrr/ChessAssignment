package status;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PiecesSateTest {
    @Test
    public void testGetKingNextMove(){
        var king= new PiecesSate(PiecesAttr.King,5,6);
        var actual = king.getNextMove();
        var expected = Arrays.asList(
                new Position(6,5),
                new Position(6,6),
                new Position(6,7),
                new Position(5,7),
                new Position(4,7),
                new Position(4,6),
                new Position(4,5),
                new Position(5,5)
        );
        Collections.sort(actual);
        Collections.sort(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetKnightMove(){
        var knight = new PiecesSate(PiecesAttr.Knight, 5, 6);
        var actual = knight.getNextMove();
        var expected = Arrays.asList(
                new Position(7,7),
                new Position(6,8),
                new Position(4,8),
                new Position(3,7),
                new Position(3,5),
                new Position(4,4),
                new Position(6,4),
                new Position(7,5)
        );
        Collections.sort(actual);
        Collections.sort(expected);
        assertEquals(expected, actual);
    }
}
