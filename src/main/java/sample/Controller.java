package sample;

import javafx.event.ActionEvent;
import javafx.util.Callback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import status.*;
import util.JsonIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Realize interface control
 */
@Slf4j
public class Controller {
    // main gui
    @FXML
    private GridPane chessBoardGrid;
    @FXML
    private Label playerLabel;
    @FXML
    private Label timer;
    @FXML
    private Label countMove;
    @FXML
    private Label msg;

    //pop-ups start gui
    @FXML
    private GridPane ups_Start;
    @FXML
    private TextField playerField;
    @FXML
    private Button startGame_btn;

    //pop-ups ranking
    @FXML
    private GridPane ups_Ranking;
    @FXML
    private TableView<ResultRow> resultTable;
    @FXML
    private TableColumn<ResultRow,ResultRow> resultTableCol;

    //pop-ups result gui
    @FXML
    private GridPane ups_Result;
    @FXML
    private Label gotPoint;

    private Pane[][] chessBoard;
    private final Board board = new Board();
    private final File resultFile = new File(getClass().getProtectionDomain().
            getCodeSource().getLocation().getPath()+"-result.json");

    /**
     *When starting the program, prepare the required controls
     */
    public void initialize(){
        //init chessboard function
        initChessBoard();
        //setting piece
        var pieceList = board.getPieceList();
        for(var p : pieceList){
            initPiece(p);
        }
        //init result table function
        initResultTable();
        //set binding
        board.nextPositionProperty().addListener((obj,older,newly)-> renewNextPosition(older,newly));
        board.gameStateProperty().addListener((obj,older,newly)->onGameStateChange(newly));
        //when playerField is empty, the button startGame_btn is disable
        startGame_btn.disableProperty().bind(playerField.textProperty().isEmpty());

        msg.textProperty().bind(board.messageProperty());
        playerLabel.textProperty().bind(playerField.textProperty());
        timer.textProperty().bind(board.getResult().timeProperty().asString("%1$tM:%1$tS"));
        countMove.textProperty().bind(board.getResult().moveProperty().asString());

        ups_Start.setVisible(true);
        Platform.runLater(()->playerField.requestFocus());
    }

    /**
     * create color style for each grid and add event handler to each
     */
    private void initChessBoard(){
        chessBoard = new StackPane[Board.totalOfRow][Board.totalOfCol];
        for( int row = 0 ; row< chessBoard.length;row++){
            for (int col =0 ;col < chessBoard.length;col++ ){
                var pane = new StackPane();
                chessBoard[row][col] = pane;
                chessBoardGrid.add(pane,col,Board.totalOfRow-row-1);
                if((row+col)%2==0)
                    pane.getStyleClass().add("CBBackground1");
                else
                    pane.getStyleClass().add("CBBackground2");

                pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                    var p = (Pane) e.getSource();
                    var pos = findPanePosition(p);
                    board.choice(pos);
                });
            }
        }
        chessBoard[Board.targetPosition.getRow()][Board.targetPosition.getCol()].setStyle("-fx-background-color: red");
    }

    /**
     * return a imageView which given size
     * @param path image path
     * @return imageview
     */
    private ImageView createImageView(String path){
        var img= new Image(getClass().getResourceAsStream(path));
        var view = new ImageView(img);
        view.fitWidthProperty().bind(chessBoard[0][0].widthProperty().subtract(14));
        view.fitHeightProperty().bind(chessBoard[0][0].heightProperty().subtract(14));
        return view;
    }

    /**
     * init piece gui component and added event listener.
     * @param piece piece
     */
    private void initPiece(PiecesSate piece){
        String imgPath;
        if (piece.getAttr()== PiecesAttr.King){
            imgPath ="/images/king.png";
        }
        else {
            imgPath ="/images/knight.png";
        }
        var view = createImageView(imgPath);
        var title = chessBoard[piece.getPosition().getRow()][piece.getPosition().getCol()];
        title.getChildren().add(view);
        title.setCursor(Cursor.HAND);
        piece.positionProperty().addListener((obj,older,newly)->{
            var preTitle = chessBoard[older.getRow()][older.getCol()];
            var newTitle = chessBoard[newly.getRow()][newly.getCol()];
            newTitle.getChildren().add(view);
            preTitle.setCursor(Cursor.DEFAULT);
            newTitle.setCursor(Cursor.HAND);
        });
    }

    /**
     * renew display color of pane, display which can move position
     * @param preList previous move able position
     * @param newList newly move able position
     */
    private void renewNextPosition(List<Position> preList, List<Position> newList){
        if(preList!=null){
            for (var p:preList){
                var tile = chessBoard[p.getRow()][p.getCol()];
                tile.setCursor(Cursor.DEFAULT);
                tile.getStyleClass().remove("nextCanMove");
            }
        }
        for (var p : newList){
            var tile = chessBoard[p.getRow()][p.getCol()];
            tile.getStyleClass().add("nextCanMove");
            tile.setCursor(Cursor.HAND);
        }
    }

    /**
     * find and return specific pane position in chessboard
     * @param pane pane to be find
     * @return pane position
     */
    private Position findPanePosition (Pane pane){
        for(int row =0 ; row<Board.totalOfRow;row++){
            for (int col=0 ; col<Board.totalOfCol;col++){
                if (pane == chessBoard[row][col])
                    return new Position(row,col);
            }
        }
        return null;
    }

    /**
     * init result table with component
     */
    private void initResultTable(){
        log.info("init Result table");
        var playerCol=new TableColumn<ResultRow,String>("Player Name");
        var resultCol=new TableColumn<ResultRow,Integer>("Result");
        resultTable.getColumns().addAll(playerCol,resultCol);
        playerCol.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

        resultCol.setComparator(resultCol.getComparator().reversed());
        resultTable.getSortOrder().add(resultCol);

        //auto pull line number
        resultTableCol.setCellValueFactory(o -> new ReadOnlyObjectWrapper<>(o.getValue()));
        resultTableCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ResultRow, ResultRow> call(TableColumn<ResultRow, ResultRow> x) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(ResultRow item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null)
                            setText(this.getTableRow().getIndex() + 1 + "");
                        else
                            setText("");
                    }
                };
            }
        });
        // load result if present
        try (var is = new FileInputStream(resultFile)){
            log.info("Result file has found, and adding into the table");
            var list = JsonIO.readJsonStream(is, ResultRow.class);
            log.debug("Adding {} items", list.size());
            for (var item:list){
                resultTable.getItems().add(item);
            }
            resultTable.sort();
        } catch (IOException|NullPointerException e){
            log.trace("No Result file can found");
            System.out.println("No Result File can found");
        }
    }

    /**
     * structure of data to be stored in result file
     */
    @Data
    @AllArgsConstructor
    public static class ResultRow{
        private String PlayerName;
        private int result;
    }

    /**
     * add new record to the result table
     * @param PlayerName player name
     * @param result result
     */
    private void addResultRow(String PlayerName, int result){
        log.debug("Player Name: {} , Result: {}",PlayerName,result);
        resultTable.getItems().add(new ResultRow(PlayerName, result));
        resultTable.sort();
        int size = resultTable.getItems().size();
        if(size>10){
            resultTable.getItems().remove(size-1);
        }
    }

    /**
     * reset button pressed, restart the game
     */
    @FXML
    public void restart(){
        log.info("Restart the game!");
        board.start();
    }

    /**
     * start game button pressed, starts playing chess game
     */
    @FXML
    public void onPlayButtonPressed(){
        log.info("Start the New Game!");
        ups_Start.setVisible(false);
        board.start();
    }

    /**
     * new game button pressed.
     */
    @FXML
    public void onNewGame(){
    log.info("Is new game start");
    ups_Result.setVisible(false);
    ups_Start.setVisible(true);
    playerField.requestFocus();
    }

    /**
     * change gui when game state is change
     * @param newState game newly state
     */
    public void onGameStateChange(GameState newState){
        if (newState==GameState.Running)
            return;
        if (newState==GameState.GameOver){
            log.info("Game Over!!!");
            board.end();
            gotPoint.setText("Game Over!");
        }
        if (newState== GameState.Win){
            log.info("Your WIN!!!");
            board.end();
            int result = board.getResult().getResult();
            gotPoint.setText("Your Win!!! Your result is "+ result);
            addResultRow(playerLabel.getText(),result);
        }

        ups_Result.setVisible(true);
    }

    /**
     * save result when game is exit
     */
    public void onExit(){
        log.info("Saving result into {}...", resultFile.getName());
        try {
            resultFile.createNewFile();
            JsonIO.writterJsonStream(new FileOutputStream(resultFile),resultTable.getItems());
        }catch (IOException e){
            log.error("Cannot saving!!!");
            e.printStackTrace();
        }
        log.info("Exiting app ...");
    }

    /**
     * display ranking table pop-ups
     * @param event
     */
    @FXML
    public void onRankingDisplay(ActionEvent event){
        log.debug("Display {} table button pressed",((Button)event.getSource()).getText());
        log.info("Display Ranking table");
        ups_Ranking.setVisible(true);
    }

    /**
     * close ranking table
     * @param event
     */
    @FXML
    public void onRankingDisplayOff(ActionEvent event){
        log.info("closing ranking table");
        log.debug("{} button has pressed",((Button) event.getSource()).getText());
        ups_Ranking.setVisible(false);
    }
}