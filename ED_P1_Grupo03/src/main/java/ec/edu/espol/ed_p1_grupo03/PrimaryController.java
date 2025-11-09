package ec.edu.espol.ed_p1_grupo03;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controlador de la pantalla de inicio (primary.fxml).
 */
public class PrimaryController {

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        // Ir a la pantalla de Gestión de residuos (secondary.fxml)
        App.setRoot("secondary");
    }
}
