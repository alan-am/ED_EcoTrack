package ec.edu.espol.ed_p1_grupo03;

import ec.edu.espol.ed_p1_grupo03.Residuo;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controlador para la visualización de reportes gráficos.
 * Incluye PieChart, BarChart y un Mapa de Calor generado dinámicamente.
 */
public class EstadisticasPieCHART {

    @FXML private Button btnVolverInicio;
    
    // Gráficos estadísticos
    @FXML private PieChart pieChartTipos; 
    @FXML private BarChart<String, Number> barChartZonas;

    // Contenedor para la renderización del Mapa de Calor
    @FXML private TilePane panelMapaCalor; 

    // Inicializa la vista y dispara la generación de reportes
    @FXML
    public void initialize() {
        generarReportes();
    }

    // Procesa los datos del Singleton Sistema y puebla los gráficos
    private void generarReportes() {
        
        ListaCircularDoble<Residuo> lista = Sistema.getInstance().getResiduos();
        
        // Estructuras auxiliares para agregación de datos
        Map<String, Double> porTipo = new HashMap<>();
        Map<String, Double> porZona = new HashMap<>();
        double pesoMaximoZona = 0;

        // Iteración sobre la lista propia para sumar pesos por categoría y zona
        for(Residuo r : lista) {
            // Agrupación por Tipo
            porTipo.put(r.getTipo(), porTipo.getOrDefault(r.getTipo(), 0.0) + r.getPeso());
            
            // Agrupación por Zona
            double nuevoPeso = porZona.getOrDefault(r.getZona(), 0.0) + r.getPeso();
            porZona.put(r.getZona(), nuevoPeso);
            
            // Determinación del máximo para la escala de colores
            if (nuevoPeso > pesoMaximoZona) {
                pesoMaximoZona = nuevoPeso;
            }
        }

        // 1. Configuración del PieChart (Distribución por Tipo)
        if (pieChartTipos != null) {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : porTipo.entrySet()) {
                pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            pieChartTipos.setData(pieData);
        }

        // 2. Configuración del BarChart (Peso total por Zona)
        if (barChartZonas != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Peso (Kg)");
            for (Map.Entry<String, Double> entry : porZona.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barChartZonas.getData().clear();
            barChartZonas.getData().add(series);
        }

        // 3. Generación del Mapa de Calor (Zonas Críticas)
        if (panelMapaCalor != null) {
            panelMapaCalor.getChildren().clear();
            
            for (Map.Entry<String, Double> entry : porZona.entrySet()) {
                String zonaNombre = entry.getKey();
                double peso = entry.getValue();
                
                // Cálculo de opacidad basado en el peso relativo (Normalización)
                double intensidad = (pesoMaximoZona > 0) ? (peso / pesoMaximoZona) : 0.1;
                if (intensidad < 0.2) intensidad = 0.2; // Visibilidad mínima
                
                // Construcción de la celda visual
                VBox celda = new VBox(5);
                celda.setAlignment(javafx.geometry.Pos.CENTER);
                
                Rectangle rect = new Rectangle(100, 100);
                rect.setFill(Color.web("#e74c3c", intensidad)); 
                rect.setStroke(Color.DARKGRAY);
                
                Label lblNombre = new Label(zonaNombre);
                lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");
                
                Label lblPeso = new Label(String.format("%.1f Kg", peso));
                
                celda.getChildren().addAll(rect, lblNombre, lblPeso);
                
                // Tooltip para detalles al pasar el cursor
                Tooltip.install(celda, new Tooltip("Zona: " + zonaNombre + "\nPeso: " + peso));
                
                panelMapaCalor.getChildren().add(celda);
            }
        }
    }

    // Navegación hacia la pantalla principal
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}