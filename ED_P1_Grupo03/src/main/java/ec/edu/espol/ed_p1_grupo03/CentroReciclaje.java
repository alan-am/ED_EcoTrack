/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.ed_p1_grupo03;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CentroReciclaje {

    @FXML
    private TableView tablaResiduoProcesado; // Muestra el elemento que está en el TOP de la pila

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void switchToEstadisticas() throws IOException {
        App.setRoot("Estadisticas");
    }
    
   
    @FXML
    private void procesarResiduo() {

    }
}

