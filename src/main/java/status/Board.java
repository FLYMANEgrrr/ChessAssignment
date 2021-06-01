package status;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * init king and knight piece.
 *
 */
@Slf4j
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

    /**
     * Define default position King and Knight.
     * king(2,1)
     * knight(2,2)
     */
    public static final List<PiecesSate> defaultPosition = List.of(
            new PiecesSate(PiecesAttr.King,2,1),
            new PiecesSate(PiecesAttr.Knight,2,2));

    //Game status
    private final ObjectProperty<GameState> GameState=new SimpleObjectProperty<>();

    private final List<PiecesSate> pieceList;
    private final Result result = new Result();
    private final ObjectProperty<PiecesSate> choicePiece = new SimpleObjectProperty<>();
    private final ObjectProperty<List<Position>> nextPosition = new SimpleObjectProperty<>();
    private final StringProperty message = new SimpleStringProperty();

    /**
     * create new board
     */
    public Board(){
        int defaultPositionSize = defaultPosition.size();
        pieceList = new ArrayList<>(defaultPositionSize);
        for (var t:defaultPosition){
            pieceList.add(new PiecesSate(t.getAttr(),
                    t.getPosition().getRow(),
                    t.getPosition().getCol()));
        }
        choicePiece.addListener((obj,older,newly)->{
            if (newly!=null){
                message.set(newly.getAttr()+" has choose");
                log.info("{} has choose", newly.getAttr());
            }
            else
                message.set("null");
        });
        choicePiece.addListener((obj,older,newly)->renewNextPosition(newly));
    }

    /**
     * start new game
     */
    public void start() {
        //setting all of pieces into a map
        Map<PiecesAttr, ArrayList<PiecesSate>> map = new HashMap<>();
        for (var temp : pieceList) {
            map.computeIfAbsent(temp.getAttr(), e -> new ArrayList<>());
            map.get(temp.getAttr()).add(temp);
        }
        pieceList.clear();
        //set pieces
        for (var temp : defaultPosition){
            var piece = map.get(temp.getAttr()).remove(0);
            piece.setPosition(temp.getPosition());
            pieceList.add(piece);
        }

        GameState.set(status.GameState.Running);
        nextPosition.set(List.of());
        choicePiece.set(null);
        result.start();
    }

    /**
     * to end of chess game aad confirm the game result
     */
    public void end(){
        result.end();
    }

    /**
     * choice the specified position on the board,
     * three status they have 1.has choice 2. cancel choice 3.move piece
     * @param pos position to choice
     */
    public void choice(Position pos){
        for (var p: pieceList){
            if(pos.equals(p.getPosition()))
                choicePiece.set(p);
        }

        PiecesSate piece=choicePiece.get();
        if (piece==null){
            return;
        }
        // if piece is newly choice
        if (piece.getPosition().equals(pos)){
            return ;
        }
        // cancel choice if choice position is not available into next move
        if (!nextPosition.get().contains(pos)) {
            choicePiece.set(null);
            return;
        }
        // move
        choicePiece.set(null);
        move(piece,pos);
        renewGameState();
        result.addMove();

    }

    /**
     * move the piece to the specified position
     * @param piece piece to move which piece wanna move
     * @param pos position where piece wanna to move here
     */
    public void move(PiecesSate piece,Position pos){
        log.info("Move {} ", piece.getAttr());
        piece.setPosition(pos);
    }

    /**
     * return the list of next possible position of specified piece.
     * a piece cannot move to other piece position.
     * @param pieceState Current piece choice, this param cannot be null!
     * @param pieceList list of pieces on the board
     * @return list of next possible position of the piece.
     */
    public List<Position> getNextMove(PiecesSate pieceState, List<PiecesSate> pieceList){
        List<Position> move = pieceState.getNextMove();

        Predicate<Position> onBoard = position -> position.getRow()< Board.totalOfRow && position.getRow()>=0 &&
                position.getCol()>=0&& position.getCol()< Board.totalOfCol;

        assert move != null;
        move=move.stream().filter(onBoard).collect(Collectors.toList());

        for (var p : pieceList){
            move.remove(p.getPosition());
        }
        return move;
    }

    /**
     * if choice the piece is under attack another piece shall return True;
     * @param piece test for piece
     * @return when it's move able return true;
     */
    public boolean checkMoveAble(PiecesSate piece){
        for (PiecesSate p:pieceList){
            if (p==piece)
                continue;
            if (Objects.requireNonNull(p.getNextMove()).contains(piece.getPosition()))
                return true;
        }
        //if wanna get normal chess rule, change here always to be true
        return false;
    }

    /**
     * renew current game status
     */
    public void renewGameState(){

        if(pieceList.stream().anyMatch(pos -> pos.getPosition().equals(targetPosition))){
            GameState.set(status.GameState.Win);
            result.end();
        }
        else if (pieceList.stream().noneMatch(this::checkMoveAble)){
            GameState.set(status.GameState.GameOver);
        }
    }

    public void renewNextPosition(PiecesSate p){
        if (p == null) {
           nextPosition.set(List.of());
           return;
        }
        if (!checkMoveAble(p)){
            nextPosition.set(List.of());
            message.set(choicePiece.get().getAttr()+" is can not move");
        }
        var moves = getNextMove(p, pieceList);
        nextPosition.set(moves);

        if (moves.size()==0)
            message.set(choicePiece.get().getAttr()+" is not able to moves");
        else
            log.debug("Next position: {}", nextPosition.get());

    }

    /**
     * get list of piece on the board
     * @return list of piece
     */
    public List<PiecesSate> getPieceList(){
        return pieceList;
    }

    /**
     *  get message property
     * @return message property
     */
    public StringProperty messageProperty(){
        return message;
    }

    /**
     * get next position property of choice piece
     * @return next position property
     */
    public ObjectProperty<List<Position>> nextPositionProperty(){
        return nextPosition;
    }

    /**
     * get result object
     * @return result
     */
    public Result getResult(){
        return result;
    }

    /**
     * get game property
     * @return game status property
     */
    public ObjectProperty<GameState> gameStateProperty(){
        return GameState;
    }

}
