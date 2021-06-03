package status;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board board = new Board();

    @Test
    public void testStart() throws NoSuchFieldException, IllegalAccessException{
        try {
            board.start();
        }catch (NullPointerException e){
            board.start();
        }
        var list = board.getPieceList();
        var expectedList = (List<PiecesSate>)board.getClass().getDeclaredField("defaultPosition").get(board);
        assertEquals(expectedList.size(), list.size());

        for (int temp = 0; temp < expectedList.size(); temp++) {
            assertEquals(expectedList.get(temp).getAttr(), list.get(temp).getAttr());
            assertEquals(expectedList.get(temp).getPosition(), list.get(temp).getPosition());
        }
    }

    @Test
    public void testChoice()throws NullPointerException{
        try {
            board.start();
        }catch (NullPointerException e){
            board.start();
        }
        PiecesSate piece = null;
        var list = board.getPieceList();
        for (var temp:list){
            if (board.checkMoveAble(temp)){
                piece= temp;
            }
        }
        assertNotNull(piece);
        board.choice(piece.getPosition());
        var list2 = board.getNextMove(piece,board.getPieceList());
        assertTrue(list2.size()>0);
        board.choice(list2.get(0));
        assertEquals(list2.get(0),piece.getPosition());
    }

    @Test
    public void testMove(){
        var piece = board.getPieceList().get(0);
        var pos = new Position(0,0);
        board.move(piece,pos);
        assertEquals(pos,piece.getPosition());
    }

    @Test
    public void testGetKingNextMove(){
        var king = new PiecesSate(PiecesAttr.King,0,0);
        var other= new PiecesSate(PiecesAttr.King,0,1);
        var other1 = new PiecesSate(PiecesAttr.Knight,1,0);
        var list = List.of(king,other,other1);
        var actual = board.getNextMove(king,list);
        var expected = List.of(new Position(1,1));
        assertEquals(expected,actual);
    }

    @Test
    public void testGetKnightMove(){
        var knight = new PiecesSate(PiecesAttr.Knight,0,0);
        var other = new PiecesSate(PiecesAttr.King,0,1);
        var other1 = new PiecesSate(PiecesAttr.Knight,2,1);
        var list=List.of(knight,other,other1);
        var actual = board.getNextMove(knight,list);
        var expected = List.of(new Position(1,2));
        assertEquals(expected,actual);
    }
}
