package ec.edu.espol.ed_p1_grupo03.exportar_importar;

import ec.edu.espol.ed_p1_grupo03.App;
import ec.edu.espol.ed_p1_grupo03.serializado.Sistema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

public class ExportarDatosController implements Initializable {

    @FXML private Button btnVolverInicio;
    @FXML private ToggleGroup dataGroup;
    @FXML private RadioButton radioResiduos;
    @FXML private RadioButton radioEstadisticas; // 
    
    @FXML private ToggleGroup formatGroup;
    @FXML private RadioButton radioCSV;
    @FXML private RadioButton radioJSON;
    @FXML private Button btnExportar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void exportarArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte EcoTrack");
        
        boolean esCSV = radioCSV.isSelected();
        boolean esResiduos = radioResiduos.isSelected();
        
        String extension = esCSV ? "*.csv" : "*.json";
        String descripcion = esCSV ? "Archivos CSV" : "Archivos JSON";
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extension));
        
        String nombreBase = esResiduos ? "reporte_residuos" : "reporte_zonas";
        fileChooser.setInitialFileName(nombreBase + (esCSV ? ".csv" : ".json"));

        File file = fileChooser.showSaveDialog(btnExportar.getScene().getWindow());

        if (file != null) {
            String contenido;
            
            if (esResiduos) {
                // cabecera especifica
                String cabecera = "ID,NOMBRE,TIPO,PESO,ZONA,FECHA,PRIORIDAD";
                contenido = generarReporte(Sistema.getInstance().getResiduos(), esCSV, cabecera, "Reporte de Residuos");
            } else {
                String cabecera = "NOMBRE_ZONA,UTILIDAD,PESO_PENDIENTE,PESO_RECOLECTADO";
                contenido = generarReporte(Sistema.getInstance().getZonas(), esCSV, cabecera, "Reporte de Zonas");
            }

            guardarEnDisco(file, contenido);
        }
    }


    private void guardarEnDisco(File file, String contenido) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contenido);
            mostrarAlerta("Éxito", "Archivo guardado correctamente.");
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo escribir el archivo.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    
    
    //metodo generico
    private String generarReporte(Iterable<? extends Formateable> datos, boolean esCSV, String cabeceraCSV, String tituloJSON) {
        StringBuilder sb = new StringBuilder();

        if (esCSV) {
            sb.append(cabeceraCSV).append("\n");
            for (Formateable item : datos) {
                sb.append(item.toCSV()).append("\n");
            }
        } else {
            // JSON 
            sb.append("{\n");
            sb.append("  \"sistema\": \"EcoTrack\",\n");
            sb.append("  \"contenido\": \"").append(tituloJSON).append("\",\n");
            sb.append("  \"data\": [\n");
            
            
            int count = 0;
            
            boolean primero = true;
            for (Formateable item : datos) {
                if (!primero) {
                    sb.append(",\n");
                }
                sb.append(item.toJSON());
                primero = false;
            }
            
            sb.append("\n  ]\n");
            sb.append("}");
        }
        return sb.toString();
    }
}