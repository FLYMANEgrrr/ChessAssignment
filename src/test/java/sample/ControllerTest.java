package sample;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import status.Board;
import status.Position;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {
    private static Controller controller;
    private static Pane[][] chessBoard;

    @BeforeAll
    public static void setUp() throws NoSuchFieldException, IllegalAccessException{
        controller = new Controller();
        Pane[][] pane= new Pane[Board.totalOfRow][Board.totalOfCol];
        for (int row=0;row< pane.length;row++){
            for (int col=0 ; col<pane.length;col++){
                pane[row][col] = new Pane();
            }
        }

        var f= Controller.class.getDeclaredField("chessBoard");
        f.setAccessible(true);
        f.set(controller,pane);
        chessBoard=(Pane[][]) f.get(controller);

    }

    @Test
    public void findPaneTest() throws NoSuchMethodException, InvocationTargetException,IllegalAccessException{
        var findMethod = Controller.class.getDeclaredMethod("findPanePosition",Pane.class);
        findMethod.setAccessible(true);
        Position pos = (Position) findMethod.invoke(controller,chessBoard[2][3]);
        assertEquals(new Position(2,3),pos);
    }
}