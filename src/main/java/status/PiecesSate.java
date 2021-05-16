package status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public final class PiecesSate {
    private final ObjectProperty<Position> pos = new SimpleObjectProperty<>();
    private final PiecesType attr;
    public PiecesSate(PiecesType attr, int row, int col) {
        pos.set(new Position(row,col));
        this.attr=attr;
    }

    public PiecesType getAttr(){
        return attr;
    }

    public ObjectProperty<Position> getPos(){
        return pos;
    }

}
