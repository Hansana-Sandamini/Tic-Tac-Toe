package com.assignment.tictactoe.service.controller;

import com.assignment.tictactoe.service.AiPlayer;
import com.assignment.tictactoe.service.BoardImpl;
import com.assignment.tictactoe.service.HumanPlayer;
import com.assignment.tictactoe.service.Piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {

    @FXML
    private Button btnContinue;

    @FXML
    private AnchorPane gamePage;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblAiScore;

    @FXML
    private Label lblTieCount;

    @FXML
    private Label lblYourScore;

    @FXML
    private Button btnNewGame;

    private int scoreX = 0;
    private int scoreO = 0;
    private int tieCount = 0;

    private BoardImpl board;
    private HumanPlayer humanPlayer;
    private AiPlayer aiPlayer;
    private boolean isHumanTurn = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScoreLabels();
        startGame();
    }

    private void startGame() {
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

            isHumanTurn = false;
            lblStatus.setText("AI's Turn!");
            aiMakeMove();
        }
    }

    private void aiMakeMove() {
        if (displayTieResult()) return;

        if (!isHumanTurn) {
            int[] aiMove = aiPlayer.makeMove();
            board.updateMove(aiMove[0], aiMove[1], Piece.O);
            updateUI();
            if (displayTieResult()) return;
            isHumanTurn = true;
            lblStatus.setText("Your turn!");
        }
    }

    private boolean displayTieResult() {
        if (board.checkWinner() != null) {
            displayResult(board.checkWinner());
            return true;
        }else if(isFull()){
            lblStatus.setText("It's a Tie!");
            tieCount++;
            updateScoreLabels();
            disableAllButtons();
            updateUI();
            return true;
        }
        return false;
    }

    public boolean isFull() {
        int fullCount = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.getPieces()[i][j] == Piece.EMPTY) {
                    fullCount++;
                }
            }
        }
        return fullCount == 0;
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
                    } else if (piece == Piece.O) {
                        button.setText("O");
                        button.setDisable(true);
                    } else {
                        button.setText("");
                        button.setDisable(false);
                    }
                }
            }
        }
    }

    private void displayResult(Piece winner) {
        if(winner == Piece.X) {
            lblStatus.setText("You win!");
            scoreX += 1;
        } else if(winner == Piece.O) {
            lblStatus.setText("AI win!");
            scoreO += 1;
        }
        int[][] winningLine = board.checkWinningLine();
        if (winningLine != null) {
            highlightWinningLine(winningLine);
        }
        updateScoreLabels();
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

        for (int[] position : winningLinePositions) {
            int row = position[0];
            int col = position[1];
            Optional<Button> winningButton = getButtonAt(row, col);
            winningButton.ifPresent(button -> button.setStyle("-fx-background-color: #7bed9f;"));
        }
    }

    private void updateScoreLabels() {
        lblYourScore.setText("Your Score: " + scoreX);
        lblAiScore.setText("AI's Score: " + scoreO);
        lblTieCount.setText("Tie Count: " + tieCount);
    }

    @FXML
    void btnContinueOnAction(ActionEvent event) {
        board.reset();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setDisable(false);
                button.setStyle("-fx-background-color: #dfe4ea; -fx-background-radius: 10;");
            }
        }
        lblStatus.setText("Your Turn!");
        isHumanTurn = true;
    }

    @FXML
    void btnNewGameOnActon(ActionEvent event) {
        scoreX = 0;
        scoreO = 0;
        tieCount = 0;
        updateScoreLabels();
        btnContinueOnAction(event);
        startGame();
    }
}
