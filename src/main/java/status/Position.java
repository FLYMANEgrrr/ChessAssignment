package status;


import javafx.scene.layout.Pane;

/**
 * We used a two dimensional array to represent a position.
 * The object is immutable.
 */
public class Position extends Pane implements Comparable<Position> {
    private final int row;
    private final int col;
    /**
     * Create a New Position object and with specified row and column.
     * @param col column
     * @param row row
     */
    public Position(int row,int col){
        this.row=row;
        this.col=col;
    }

    /**
     * Get the Row index num.
     * @return row
     */
    public int getRow(){
        return row;
    }

    /**
     * Get the Col index num.
     * @return col
     */
    public int getCol(){
        return col;
    }

    /**
     * Return Specified object offset
     * via Position of current moved.
     * @param offset Offset of current position
     * @return After calculated new position
     */
    public Position afterMoved(Position offset){
        return afterMoved(offset.row,offset.col);
    }

    /**
     * Return the Specified offset via the position of current moved.
     * @param row Row offset
     * @param col Col offset
     * @return Calculated new position and return the result.
     */
    public Position afterMoved(int row,int col){
        row=this.row+row;
        col=this.col+col;
        return new Position(row,col);

    }

    /**
     * @return Define the return String format
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", this.row, this.col);
    }

    /**
     * if two position in  same row and same column, will return true.
     * @param o object to compare
     * @return true if both position are same.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position))
            return false;
        var p = (Position) o;
        return row == p.row && col == p.col;
    }

    @Override
    public int compareTo(Position o) {
        if (row != o.row)
            return row - o.row;

        return col - o.col;
    }

}

