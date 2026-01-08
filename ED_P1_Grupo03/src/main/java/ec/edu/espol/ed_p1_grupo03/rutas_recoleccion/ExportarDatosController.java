package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

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
import ec.edu.espol.ed_p1_grupo03.App;


public class ExportarDatosController implements Initializable {

    // Boton Barra Superior
    @FXML
    private Button btnVolverInicio;

    // opcciones de datos 
    @FXML
    private ToggleGroup dataGroup;
    @FXML
    private RadioButton radioResiduos;
    @FXML
    private RadioButton radioEstadisticas;

    // Opciones de formato
    @FXML
    private ToggleGroup formatGroup;
    @FXML
    private RadioButton radioCSV;
    @FXML
    private RadioButton radioJSON;

    // Botón de exportar
    @FXML
    private Button btnExportar;

    
    //funcion de inicializacion
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //funcion para regresar al menu
    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    // simula el guardado de un archivo X
    @FXML
    private void exportarArchivo(ActionEvent event) {
        
        // dialogo del sistema
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");

        //ejemplo de seleccion
        String initialName = "reporte_residuos";
        String extension = "*.txt"; // Por ahora
        String extensionName = "Archivo de Texto";

        if (radioCSV.isSelected()) {
            initialName = "reporte_residuos.csv";
            extension = "*.csv";
            extensionName = "Archivo CSV";
        } else if (radioJSON.isSelected()) {
            initialName = "reporte_residuos.json";
            extension = "*.json";
            extensionName = "Archivo JSON";
        }

        // configd del dialogo
        fileChooser.setInitialFileName(initialName);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(extensionName, extension),
                new FileChooser.ExtensionFilter("Todos los Archivos", "*.*")
        );

        //mostrar dialogo de guardado
        File file = fileChooser.showSaveDialog(btnExportar.getScene().getWindow());

        // 5. Si el usuario selecciona una ubicación, guardar el archivo
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                
                // aqui implementar logica
                
                writer.write("--- ARCHIVO DE EJEMPLO EcoTrack --- \n\n");
                writer.write("Este es un archivo de prueba.\n");
                
                if (radioResiduos.isSelected()) {
                    writer.write("El usuario seleccionó: Lista de residuos\n");
                } else {
                    writer.write("El usuario seleccionó: Estadísticas globales\n");
                }
                
                if (radioCSV.isSelected()) {
                    writer.write("Formato seleccionado: CSV\n");
                } else {
                    writer.write("Formato seleccionado: JSON\n");
                }
               

                // pop up de exito
                mostrarAlerta("Éxito", "El archivo se ha guardado correctamente en:\n" + file.getAbsolutePath());

            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo guardar el archivo.\n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    //config del pop up
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}