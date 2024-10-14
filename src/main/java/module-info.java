module com.assignment.tictactoe.service {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;


    opens com.assignment.tictactoe.service.controller to javafx.fxml;
    exports com.assignment.tictactoe.service;
}