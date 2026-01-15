package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import ec.edu.espol.ed_p1_grupo03.App;
//import ec.edu.espol.ed_p1_grupo03.modelo.Residuo; 


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import ec.edu.espol.ed_p1_grupo03.serializado.Sistema;
import ec.edu.espol.ed_p1_grupo03.Residuo;

//Por ahora esta implementado solo para los datos relacionado a rutas de recoleccion

public class ExportarDatosController implements Initializable {

    @FXML private Button btnVolverInicio;
    @FXML private ToggleGroup dataGroup;
    @FXML private RadioButton radioResiduos;     // Opción "Detalle de Zonas/Residuos"
    @FXML private RadioButton radioEstadisticas; // Opción "Estadísticas" (Simulado temporalmente)
    
    @FXML private ToggleGroup formatGroup;
    @FXML private RadioButton radioCSV;
    @FXML private RadioButton radioJSON;
    @FXML private Button btnExportar;
    
    //--------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializaciones
    }

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    // MÉTODO PRINCIPAL DE EXPORTACION 
    @FXML
    private void exportarArchivo(ActionEvent event) {
        // config diálogo de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte EcoTrack");
        
        String extension = radioCSV.isSelected() ? "*.csv" : "*.json";
        String descripcion = radioCSV.isSelected() ? "Archivos CSV" : "Archivos JSON";
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extension));
        
        // Nombre sugerido por defecto
        fileChooser.setInitialFileName("reporte_ecotrack" + (radioCSV.isSelected() ? ".csv" : ".json"));

        // mostrar diálogo
        File file = fileChooser.showSaveDialog(btnExportar.getScene().getWindow());

        if (file != null) {
            // obtencion datos de prueba
            List<Zona> datos = obtenerDatosDelSistema(); 
            
            String contenido = "";

            // Construccion de acuerdo a la seleccion
            if (radioCSV.isSelected()) {
                contenido = generarContenidoCSV(datos);
            } else {
                contenido = generarContenidoJSON(datos);
            }

            // guardar en disco
            guardarEnDisco(file, contenido);
        }
    }

    // generador csv
    private String generarContenidoCSV(List<Zona> zonas) {
        StringBuilder sb = new StringBuilder();
        
        // Cabecera del CSV
        sb.append("NOMBRE_ZONA,UTILIDAD,PESO_PENDIENTE,PESO_RECOLECTADO,DETALLE_RESIDUOS\n");

        for (Zona z : zonas) {
            sb.append(z.getNombre()).append(",");
            sb.append(String.format("%.2f", z.getUtilidad())).append(",");
            sb.append(z.getPPendiente()).append(",");
            sb.append(z.getPRecolectado()).append(",");

            // Manejo de la lista interna de residuos (separados por punto y coma)
            StringBuilder residuosStr = new StringBuilder();
            if (z.getResiduos() != null && !z.getResiduos().isEmpty()) {
                for (Residuo r : z.getResiduos()) {
                    residuosStr.append(r.getTipo()).append(" (").append(r.getPeso()).append("kg); ");
                }
            } else {
                residuosStr.append("Sin residuos");
            }
            
            // Limpiar caracteres peligrosos para CSV(, " ")
            String residuosLimpios = residuosStr.toString().replace(",", " ");
            sb.append(residuosLimpios).append("\n");
        }
        return sb.toString();
    }

    //GENERADOR JSON 
    private String generarContenidoJSON(List<Zona> zonas) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{\n");
        sb.append("  \"sistema\": \"EcoTrack\",\n");
        sb.append("  \"reporte\": \"Estado de Zonas\",\n");
        sb.append("  \"data\": [\n");

        for (int i = 0; i < zonas.size(); i++) {
            Zona z = zonas.get(i);
            sb.append("    {\n");
            sb.append("      \"zona\": \"").append(z.getNombre()).append("\",\n");
            sb.append("      \"utilidad\": ").append(z.getUtilidad()).append(",\n");
            sb.append("      \"pendiente\": ").append(z.getPPendiente()).append(",\n");
            sb.append("      \"recolectado\": ").append(z.getPRecolectado()).append(",\n");
            
            // Array anidado para residuos
            sb.append("      \"residuos\": [");
            if (z.getResiduos() != null) {
                for (int j = 0; j < z.getResiduos().size(); j++) {
                    Residuo r = z.getResiduos().get(j);
                    sb.append("\n        { \"tipo\": \"").append(r.getTipo()).append("\", \"peso\": ").append(r.getPeso()).append(" }");
                    if (j < z.getResiduos().size() - 1) sb.append(",");
                }
            }
            if (z.getResiduos() != null && !z.getResiduos().isEmpty()) sb.append("\n      ");
            sb.append("]\n");

            sb.append("    }");
            
            // Coma JSON para separar objetos (excepto el ultimo)
            if (i < zonas.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("  ]\n");
        sb.append("}");
        
        return sb.toString();
    }

    // GUARDAR EN DISCO 
    private void guardarEnDisco(File file, String contenido) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contenido);
            mostrarAlerta("Éxito", "Archivo guardado correctamente en:\n" + file.getAbsolutePath());
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo escribir el archivo.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    // datos prueba
    private List<Zona> obtenerDatosDelSistema() {
        
        return Sistema.getInstance().getZonas();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}