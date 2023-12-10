module com.example.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires io.vavr;


    opens com.example.minesweeper to javafx.fxml;
    exports com.example.minesweeper;
}