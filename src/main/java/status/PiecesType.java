package status;

import java.util.List;

/**
 * This file is define the Pieces Type and rule.
 * For each type of chess contains a list of possible actions
 * that cannot be changed.
 */
public enum PiecesType {
    /**
     * King Piece and can move relative position
     */
    King(new Position[]{
            new Position(1,-1), //top-left
            new Position(1,0),  //top
            new Position(1,1),  //top-right
            new Position(0,-1), //left
            new Position(0,1),  //right
            new Position(-1,-1), //bottom-left
            new Position(-1,0),  //bottom
            new Position(-1,1)  //bottom-right
    }),

    /**
     * Knight Piece and can move relative position
     */
    Knight(new Position[]{
            new Position(-1,2), //left 1, up 2
            new Position(1,2),  //right 1, up 2
            new Position(-2,1), //left 2, up 1
            new Position(2,1),  //right 2, up1
            new Position(-2,-1), //left 2, down 1
            new Position(2,-1), //right 2, down 1
            new Position(-1,-2), //left 1, down 2
            new Position(1,-2)  //right 1, down 2
    });

    /**
     * Declare List Array of Relative Position as moves.
     * moves be private not to be public for safety.
     */
    private final List<Position> moves;

    PiecesType(Position[] moves){
        this.moves=List.of(moves);
    }

    /**
     * Returns a list of relative positions to
     * which the piece type can be moved.
     * @return next positions of piece.
     */
    public List<Position> getMoves(){
        return moves;
    }
}
