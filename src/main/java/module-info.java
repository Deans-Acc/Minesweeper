module com.example.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.jetbrains.annotations;
    requires io.vavr;
    requires z3.turnkey;


    opens com.example.minesweeper to javafx.fxml;
    exports com.example.minesweeper;
    exports com.example.minesweeper.game;
    opens com.example.minesweeper.game to javafx.fxml;
}