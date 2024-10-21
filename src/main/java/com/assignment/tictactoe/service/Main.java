package com.assignment.tictactoe.service;

import com.assignment.tictactoe.service.controller.GamePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            BoardImpl board = new BoardImpl();
            HumanPlayer humanPlayer = new HumanPlayer(board);
            AiPlayer aiPlayer = new AiPlayer(board);

            Parent load = FXMLLoader.load(getClass().getResource("/view/GamePage.fxml"));
            Scene scene = new Scene(load);

            //GamePageController controller = FXMLLoader.getController();
            //controller.setGameComponents(board, humanPlayer, aiPlayer);

            stage.setScene(scene);
            stage.setTitle("Tic-Tac-Toe");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


