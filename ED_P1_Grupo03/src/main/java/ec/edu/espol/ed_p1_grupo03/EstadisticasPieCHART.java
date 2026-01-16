package ec.edu.espol.ed_p1_grupo03;

import ec.edu.espol.ed_p1_grupo03.serializado.Sistema; 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class EstadisticasPieCHART {

    @FXML private Button btnVolverInicio;
    
    // Asegúrate de que en tu FXML los IDs sean fx:id="pieChartTipos" y fx:id="barChartZonas"
    @FXML private PieChart pieChartTipos; 
    @FXML private BarChart<String, Number> barChartZonas;

    @FXML
    public void initialize() {
        generarReportes();
    }

    private void generarReportes() {
        // CORRECCIÓN: Obtener la lista desde el Sistema centralizado
        ListaCircularDoble<Residuo> lista = Sistema.getInstance().getResiduos();
        
        // Usar tu iterador personalizado
        IteradorResiduos iterador = new IteradorResiduos(lista);
        
        // Mapas para agrupar datos
        Map<String, Double> porTipo = new HashMap<>();
        Map<String, Double> porZona = new HashMap<>();

        while (iterador.hasNext()) {
            Residuo r = iterador.next();
            if (r != null) {
                // Sumar al mapa de tipos
                porTipo.put(r.getTipo(), porTipo.getOrDefault(r.getTipo(), 0.0) + r.getPeso());
                // Sumar al mapa de zonas
                porZona.put(r.getZona(), porZona.getOrDefault(r.getZona(), 0.0) + r.getPeso());
            }
        }

        // Llenar PieChart (Tipos de residuo)
        if (pieChartTipos != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : porTipo.entrySet()) {
                pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            pieChartTipos.setData(pieData);
        }

        // Llenar BarChart (Peso por zona)
        if (barChartZonas != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Peso Total (Kg)");
            for (Map.Entry<String, Double> entry : porZona.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barChartZonas.getData().clear();
            barChartZonas.getData().add(series);
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}