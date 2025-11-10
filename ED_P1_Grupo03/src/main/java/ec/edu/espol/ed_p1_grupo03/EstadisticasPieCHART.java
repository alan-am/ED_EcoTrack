/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.ed_p1_grupo03;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class EstadisticasPieCHART {

    @FXML
    private Button btnVolverInicio;

    @FXML
    private VBox vboxGraficos;
    
    @FXML
    private PieChart pieChartTipos;
    
    @FXML
    private BarChart barChartZonas;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}