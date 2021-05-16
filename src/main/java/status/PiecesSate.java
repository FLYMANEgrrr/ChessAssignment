package status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;



public final class PiecesSate {

    private final ObjectProperty<Position> pos = new SimpleObjectProperty<>();
    private final PiecesType attr;


    /**
     *
     * @param attr
     * @param row
     * @param col
     */
    public PiecesSate(PiecesType attr, int row, int col) {
       this.pos.set(new Position(row,col));
        this.attr=attr;
    }

    /**
     *
     * @return
     */
    public PiecesType getAttr(){
        return attr;
    }

    /**
     *
     * @return
     */
    public ObjectProperty<Position> getPos(){
        return pos;
    }

    /**
     *
     * @param pos
     */
    public void settingPosition(Position pos){
        this.pos.set(pos);
    }

}
