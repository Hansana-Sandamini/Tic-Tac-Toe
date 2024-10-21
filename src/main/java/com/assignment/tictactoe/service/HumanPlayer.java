package com.assignment.tictactoe.service;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(BoardImpl board) {
        super(board);
    }

    public int[] getMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter row (0-2): ");
        int row = scanner.nextInt();
        System.out.print("Enter column (0-2): ");
        int col = scanner.nextInt();
        return new int[]{row, col};
    }

    public void makeMove(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X); // Human player places 'X'
        }
    }


    public void move(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.X);
        } else {
            System.out.println("Invalid move! Try again.");
        }
    }
}
