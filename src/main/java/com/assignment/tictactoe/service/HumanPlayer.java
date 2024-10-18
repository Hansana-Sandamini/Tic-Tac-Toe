package com.assignment.tictactoe.service;

public class HumanPlayer extends Player {

    public HumanPlayer(BoardImpl board) {
        super(board);
    }

    public void move(int row, int col) {
        if(board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X);
        } else {
            System.out.println("Invalid Move. Try Again!");
        }
    }
}
