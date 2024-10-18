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
}
