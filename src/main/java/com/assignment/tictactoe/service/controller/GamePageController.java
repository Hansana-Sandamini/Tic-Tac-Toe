package com.assignment.tictactoe.service.controller;

import com.assignment.tictactoe.service.AiPlayer;
import com.assignment.tictactoe.service.BoardImpl;
import com.assignment.tictactoe.service.HumanPlayer;
import com.assignment.tictactoe.service.Piece;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnRestart;

    @FXML
    private AnchorPane gamePage;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane headingPane;

    @FXML
    private FontAwesomeIcon iconClose;

    @FXML
    private FontAwesomeIcon iconMinimize;

    @FXML
    private Label lblHeading;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblAiScore;

    @FXML
    private Label lblTieCount;

    @FXML
    private Label lblYourScore;

    private Line winningLine;

    private int scoreX = 0;
    private int scoreO = 0;
    private int tieCount = 0;

    private BoardImpl board;
    private HumanPlayer humanPlayer;
    private AiPlayer aiPlayer;
    private boolean isHumanTurn = true;

    public void setGameComponents(BoardImpl board, HumanPlayer humanPlayer, AiPlayer aiPlayer) {
        this.board = board;
        this.humanPlayer = humanPlayer;
        this.aiPlayer = aiPlayer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new BoardImpl();
        humanPlayer = new HumanPlayer(board);
        aiPlayer = new AiPlayer(board);
        lblStatus.setText("Your Turn!");
        updateUI();
    }

    @FXML
    public void handleCellClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        int row = clickedButton == null? 0: gridPane.getRowIndex(clickedButton);
        int col = clickedButton == null? 0: gridPane.getColumnIndex(clickedButton);

        if (isHumanTurn && board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X);
            updateUI();

//            if (board.checkWinner() != null) {
//                displayResult(board.checkWinner());
//                return;
//            }
            isHumanTurn = false;
            lblStatus.setText("AI's Turn!");
            aiMakeMove();
        }
    }

    private void aiMakeMove() {
        System.out.println("AI");
        if (!isHumanTurn) {
            int[] aiMove = aiPlayer.makeMove();
            board.updateMove(aiMove[0], aiMove[1], Piece.O);

            updateUI();

            if (board.checkWinner() != null) {
                displayResult(board.checkWinner());
                return;
            }

            isHumanTurn = true;
            lblStatus.setText("Your turn!");
            //displayResult(null);
        }
    }

    private void updateUI() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Piece piece = board.getPieces()[row][col];

                Optional<Button> optionalButton = getButtonAt(row, col);
                if (optionalButton.isPresent()) {
                    Button button = optionalButton.get();
                    if (piece == Piece.X) {
                        button.setText("X");
                        button.setDisable(true);
                        scoreX++;
                    } else if (piece == Piece.O) {
                        button.setText("O");
                        button.setDisable(true);
                        scoreO++;
                    } else {
                        button.setText("");
                        button.setDisable(false);
                        tieCount++;
                    }
                }
                updateScoreLabels();
            }
        }
    }

    private void displayResult(Piece winner) {
        if(winner == Piece.X) {
            lblStatus.setText("You win!");
        } else if(winner == Piece.O) {
            lblStatus.setText("AI win!");
        } else {
            lblStatus.setText("It's a Tie!");
        }
        int[][] winningLine = board.checkWinningLine();
        if (winningLine != null) {
            highlightWinningLine(winningLine);
        }
        disableAllButtons();
    }

    private Optional<Button> getButtonAt(int row, int col) {
        for(javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if((nodeRow == null ? 0 : nodeRow) == row && (nodeCol == null ? 0 : nodeCol) == col) {
                if(node instanceof Button) {
                    return Optional.of((Button) node);
                }
            }
        }
        return Optional.empty();
    }

    private void disableAllButtons() {
        for(javafx.scene.Node node : gridPane.getChildren()) {
            if(node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    private void highlightWinningLine(int[][] winningLinePositions) {
        if (winningLinePositions == null) return;

        if (winningLine == null) {
            winningLine = new Line();
            gamePage.getChildren().add(winningLine);
        }

        int[] startPosition = winningLinePositions[0];
        int[] endPosition = winningLinePositions[2];

        Button startButton = getButtonAt(startPosition[0], startPosition[1]).orElse(null);
        Button endButton = getButtonAt(endPosition[0], endPosition[1]).orElse(null);

        if (startButton != null && endButton != null) {
            double startX = startButton.getLayoutX() + startButton.getWidth() / 2;
            double startY = startButton.getLayoutY() + startButton.getHeight() / 2;
            double endX = endButton.getLayoutX() + endButton.getWidth() / 2;
            double endY = endButton.getLayoutY() + endButton.getHeight() / 2;

            winningLine.setStartX(startX);
            winningLine.setStartY(startY);
            winningLine.setEndX(endX);
            winningLine.setEndY(endY);
            winningLine.setStroke(Color.RED);
            winningLine.setStrokeWidth(3);
            winningLine.setStrokeType(StrokeType.CENTERED);
            winningLine.setVisible(true);
        }
    }

    private void updateScoreLabels() {
        lblYourScore.setText("Your Score: " + scoreX);
        lblAiScore.setText("AI's Score: " + scoreO);
        lblTieCount.setText("Tie Count: " + tieCount);
    }

    @FXML
    void btnRestartOnAction(ActionEvent event) {
        board.reset();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setDisable(false);
            }
        }
        lblStatus.setText("Your Turn!");
        winningLine.setVisible(false);
        isHumanTurn = true;
    }
}
