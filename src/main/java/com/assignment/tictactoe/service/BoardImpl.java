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

    public Piece[][] getPieces() {
        return pieces;
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

        for (int row = 0; row < 3; row++) {
            if (pieces[row][0] == pieces[row][1] && pieces[row][1] == pieces[row][2] && pieces[row][0] != Piece.EMPTY) {
                return pieces[row][0];
            }
        }

        for (int col = 0; col < 3; col++) {
            if (pieces[0][col] == pieces[1][col] && pieces[1][col] == pieces[2][col] && pieces[0][col] != Piece.EMPTY) {
                return pieces[0][col];
            }
        }

        if (pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2] && pieces[0][0] != Piece.EMPTY) {
            return pieces[0][0];
        }

        if (pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0] && pieces[0][2] != Piece.EMPTY) {
            return pieces[0][2];
        }

        int[][] winningLine = checkWinningLine();
        boolean boardFull = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (pieces[row][col] == Piece.EMPTY) {
                    boardFull = false;
                    break;
                }
            }
        }

        if (boardFull) {
            return null;
        }
        if (winningLine != null) {
            return null;
        }
        return null;
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

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(pieces[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] checkWinningLine() {
        for (int row = 0; row < 3; row++) {
            if (pieces[row][0] != null && pieces[row][0] == pieces[row][1] && pieces[row][1] == pieces[row][2]) {
                return new int[][]{{row, 0}, {row, 1}, {row, 2}};
            }
        }

        for (int col = 0; col < 3; col++) {
            if (pieces[0][col] != null && pieces[0][col] == pieces[1][col] && pieces[1][col] == pieces[2][col]) {
                return new int[][]{{0, col}, {1, col}, {2, col}};
            }
        }

        if (pieces[0][0] != null && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }

        if (pieces[0][2] != null && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }
        return null;
    }

    public void reset() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                pieces[row][col] = null;
            }
        }
    }

}

