package com.assignment.tictactoe.service.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GamePageController {

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
    void btnRestartOnAction(ActionEvent event) {

    }
}