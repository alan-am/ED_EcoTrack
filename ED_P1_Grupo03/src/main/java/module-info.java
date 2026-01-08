module ec.edu.espol.ed_p1_grupo03 {
    
    requires javafx.controls;   // Botones, TableView, etc.
    requires javafx.fxml;       // FXMLLoader, archivos FXML
    requires javafx.graphics;   // Application, Scene, Stage

    
    opens ec.edu.espol.ed_p1_grupo03 to javafx.fxml;
    opens ec.edu.espol.ed_p1_grupo03.rutas_recoleccion to javafx.fxml, javafx.base;
    exports ec.edu.espol.ed_p1_grupo03;
}