module ec.edu.espol.ed_p1_grupo03 {
    
    requires javafx.controls;   // Botones, TableView, etc.
    requires javafx.fxml;       // FXMLLoader, archivos FXML
    requires javafx.graphics;   // Application, Scene, Stage

    
    opens ec.edu.espol.ed_p1_grupo03 to javafx.fxml;
    exports ec.edu.espol.ed_p1_grupo03;
}