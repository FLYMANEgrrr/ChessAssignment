package status;

import java.util.Comparator;

/**
 * We used a two dimensional array to represent a position.
 * The object is immutable.
 */
public class Position{
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
     * Return Specified object offset
     * via Position of current moved.
     * @param offset  current position call offset
     * @return After calculated new position
     */
    public Position afterMoved(Position offset){
        return afterMoved(offset.row,offset.col);
    }

    /**
     * Return the Specified offset via the position of current moved.
     * @param row Row offset
     * @param col Col offset
     * @return Calculated new position
     */
    public Position afterMoved(int row,int col){
        return new Position(this.row+row,this.col+col);
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
     * @return Define the return String format
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}

