package com.assignment.tictactoe.service;

public class BoardImpl implements Board {
    private Piece[][] pieces;

    public BoardImpl() {
        initializeBoard();
    }

    public void initializeBoard() {
        pieces = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    public boolean isLegalMove(int row, int col) {
        return pieces[row][col] == Piece.EMPTY;
    }

    public void updateMove(int row, int col, Piece piece) {
        if(isLegalMove(row, col)) {
            pieces[row][col] = piece;
        }
    }

    public Piece checkWinner() {
        for(int i = 0; i < 3; i++) {
            if(pieces[i][0] != Piece.EMPTY && pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                return pieces[i][0];
            }
            if(pieces[0][i] != Piece.EMPTY && pieces[0][i] == pieces[1][i] && pieces[1][i] == pieces[2][i]) {
                return pieces[0][i];
            }
        }

        if(pieces[0][0] != Piece.EMPTY && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return pieces[0][0];
        }

        if(pieces[0][2] != Piece.EMPTY && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return pieces[0][2];
        }

        boolean isBoardFull = true;
        for(int i = 0; i < 3 && isBoardFull; i++) {
            for(int j = 0; j < 3 && isBoardFull; j++) {
                if(pieces[i][j] == Piece.EMPTY) {
                    isBoardFull = false;
                }
            }
        }
        return isBoardFull ? Piece.EMPTY : null;
    }

    public void printBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(pieces[i][j] == Piece.EMPTY ? "-" : pieces[i][j]);
                if(j < 2) System.out.print(" | ");
            }
            System.out.println();
            if(i < 2) System.out.println("--+---+--");
        }
    }

    public boolean isFull() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(pieces[i][j] == Piece.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}

