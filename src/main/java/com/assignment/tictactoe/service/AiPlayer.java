package com.assignment.tictactoe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiPlayer extends Player {
    private Random random;

    public AiPlayer(BoardImpl board) {
        super(board);
        this.random = new Random();
    }

    public int[] makeMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board.isLegalMove(row, col));

        board.updateMove(row, col, Piece.O);
        return new int[]{row, col};
    }

    public void move(int row, int col) {
        List<int[]> availableMoves = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.isLegalMove(i, j)) {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        if(!availableMoves.isEmpty()) {
            int[] move = availableMoves.get(random.nextInt(availableMoves.size()));
            board.updateMove(move[0], move[1], Piece.O);
        }
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.isLegalMove(i, j)) {
                    board.updateMove(i, j, Piece.O);
                    int score = minimax(false);
                    board.updateMove(i, j, Piece.EMPTY);
                    if(score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    private int minimax(boolean isMaximizing) {
        Piece winner = board.checkWinner();

        if(winner == Piece.O) return 1;
        if(winner == Piece.X) return -1;
        if(board.isFull()) return 0;

        if(isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.O);
                        int score = minimax(false);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(board.isLegalMove(i, j)) {
                        board.updateMove(i, j, Piece.X);
                        int score = minimax(true);
                        board.updateMove(i, j, Piece.EMPTY);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

}
