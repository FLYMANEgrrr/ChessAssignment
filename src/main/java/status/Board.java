package status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class Board {
    /**
     * Define the number of rows in the entire board,
     * and used final keyword to protected the variable.
     */
    public static final int totalOfRow=8;
    /**
     * Define the number of columns in the entire board,
     * and used final keyword to protected the variable.
     */
    public static final int totalOfCol=8;

    /**
     * Define the target position,
     * and used final keyword to protected the variable
     */
    public static final Position targetPosition=new Position(0,6);

    public static final List<PiecesSate> beginGame = List.of(
            new PiecesSate(PiecesType.King,2,1),
            new PiecesSate(PiecesType.Knight,2,2));

    /**
     * Game status
     */
    private final ObjectProperty<GameState> GameState=new SimpleObjectProperty<>();

}
