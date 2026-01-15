package ec.edu.espol.ed_p1_grupo03;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CentroReciclaje {

    @FXML private TableView<Residuo> tablaResiduoProcesado;
    @FXML private VBox vboxPilaLIFO; 
    
    // Columnas (Asegúrate que los fx:id coincidan en el FXML)
    @FXML private TableColumn<Residuo, String> colId; 
    @FXML private TableColumn<Residuo, String> colNombre;
    @FXML private TableColumn<Residuo, String> colTipo;
    @FXML private TableColumn<Residuo, Double> colPeso;
    @FXML private TableColumn<Residuo, String> colZona;
    @FXML private TableColumn<Residuo, String> colFecha;

    private MiPila<Residuo> pila;
    private ObservableList<Residuo> listaVisual = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        pila = AlmacenDatos.getInstance().getPilaReciclaje();

        // TRUCO: Si la pila está vacía, llenémosla con lo que haya en la lista principal
        // para que no se vea vacía al probar.
        if (pila.isEmpty()) {
            ListaCircularDoble<Residuo> lista = AlmacenDatos.getInstance().getListaResiduos();
            IteradorResiduos it = new IteradorResiduos(lista);
            while(it.hasNext()) {
                pila.push(it.next());
            }
        }

        configurarTabla();
        actualizarPilaVisual();
    }

    private void configurarTabla() {
        // Enlazar columnas con atributos de la clase Residuo
        if(colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if(colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if(colTipo != null) colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        if(colPeso != null) colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        if(colZona != null) colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        if(colFecha != null) colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        tablaResiduoProcesado.setItems(listaVisual);
    }

    @FXML
    private void procesarResiduo() {
        if (pila.isEmpty()) {
            mostrarAlerta("Pila vacía", "No hay más residuos para procesar.");
            return;
        }

        // LÓGICA LIFO (POP)
        Residuo r = pila.pop();
        
        // Mostrar en la tabla
        listaVisual.clear();
        listaVisual.add(r);
        
        actualizarPilaVisual();
    }

    private void actualizarPilaVisual() {
        vboxPilaLIFO.getChildren().clear();
        int n = pila.size();
        for (int i = 0; i < n; i++) {
            Label lbl = new Label("Residuo en Espera");
            // Estilo verde tipo EcoTrack
            lbl.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 5; -fx-min-width: 150; -fx-alignment: CENTER;");
            vboxPilaLIFO.getChildren().add(lbl);
        }
    }

    @FXML
    private void switchToPrimary() throws IOException { App.setRoot("primary"); }
    
    @FXML
    private void switchToEstadisticas() throws IOException { App.setRoot("Estadisticas"); }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setContentText(m);
        a.show();
    }
}