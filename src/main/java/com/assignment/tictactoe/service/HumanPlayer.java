package com.assignment.tictactoe.service;

import javafx.scene.control.Alert;

public class HumanPlayer extends Player {

    public HumanPlayer(BoardImpl board) {
        super(board);
    }

    @Override
    public void move(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid move! Try again.").show();
        }
    }
}
