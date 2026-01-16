package ec.edu.espol.ed_p1_grupo03;

import ec.edu.espol.ed_p1_grupo03.serializado.Sistema; 
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CentroReciclaje implements Initializable {

    @FXML private TableView<Residuo> tablaResiduoProcesado;
    @FXML private VBox vboxPilaLIFO; 
    
    // Columnas
    @FXML private TableColumn<Residuo, String> colId; 
    @FXML private TableColumn<Residuo, String> colNombre;
    @FXML private TableColumn<Residuo, String> colTipo;
    @FXML private TableColumn<Residuo, Double> colPeso;
    @FXML private TableColumn<Residuo, String> colZona;
    @FXML private TableColumn<Residuo, String> colFecha;

    private MiPila<Residuo> pila;
    private ObservableList<Residuo> listaVisual = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Obtener la pila COMPARTIDA del Sistema
        pila = Sistema.getInstance().getPilaReciclaje();

        // 2. LOGICA IMPORTANTE: 
        // Si la pila está vacía, jalamos los residuos que están en la LISTA GENERAL
        // para simular que llegaron al centro de reciclaje.
        if (pila.isEmpty()) {
            ListaCircularDoble<Residuo> listaGlobal = Sistema.getInstance().getResiduos();
            // Usamos iterador para recorrer la lista y llenar la pila
            IteradorResiduos it = new IteradorResiduos(listaGlobal);
            while(it.hasNext()) {
                Residuo r = it.next();
                if(r != null) {
                    pila.push(r);
                }
            }
        }

        configurarTabla();
        actualizarPilaVisual();
    }

    private void configurarTabla() {
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
            mostrarAlerta("Pila vacía", "No hay más residuos por procesar.");
            return;
        }

        // LÓGICA LIFO (POP): Sacar el de arriba
        Residuo r = pila.pop();
        
        // Mostrar en la tabla el que estamos procesando
        listaVisual.clear();
        listaVisual.add(r);
        
        // Actualizar los cuadritos de la izquierda
        actualizarPilaVisual();
    }

    private void actualizarPilaVisual() {
        vboxPilaLIFO.getChildren().clear(); // Borrar lo anterior
        
        int n = pila.size();
        // Dibujar un cuadrito verde por cada elemento restante
        for (int i = 0; i < n; i++) {
            Label lbl = new Label("Residuo en Espera"); 
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