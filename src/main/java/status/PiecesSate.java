package status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.stream.Collectors;


public final class PiecesSate {

    private final ObjectProperty<Position> pos = new SimpleObjectProperty<>();
    private final PiecesAttr attr;

    /**
     *create new piece with it attribution and position
     * @param attr piece attribution
     * @param row where row index position in the board
     * @param col where col index position in the board
     */
    public PiecesSate(PiecesAttr attr, int row, int col) {
        this.attr=attr;
        this.pos.set(new Position(row,col));
    }

    /**
     *Get Piece Attribute
     * @return piece attribute
     */
    public PiecesAttr getAttr(){
        return this.attr;
    }

    /**
     *Get piece current position
     * @return current position
     */
    public Position getPosition(){
        return this.pos.get();
    }
    /**
     *setting piece position
     * @param pos selected confirm position of piece
     */
    public void setPosition(Position pos){
        this.pos.set(pos);
    }

    /**
     *Get position attribute
     * @return position attribute
     */
    public ObjectProperty<Position> positionProperty(){
        return this.pos;
    }

    /**
     * get the piece can move position
     * @return next can move position
     */
    public List<Position> getNextMove(){
        if (pos.get()!=null){
            return attr.getMoves().stream().map(pos.get()::afterMoved).collect(Collectors.toList());
        }
        return null;
    }



}
