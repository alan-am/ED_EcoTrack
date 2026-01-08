package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ec.edu.espol.ed_p1_grupo03.App;

public class RutasDespachosController implements Initializable {

    // Panel Izquierdo 
    @FXML
    private Label lblZonaUrgente;
    @FXML
    private Label lblUtilidadUrgente;
    @FXML
    private Label lblPendienteUrgente;
    @FXML
    private Label lblTipoDesechoUrgente;
    @FXML
    private Button btnDespacharVehiculo;

    // --Barra Superior -
    @FXML
    private Button btnVolverInicio;

    // tabla de la Cola de prioridad
    @FXML
    private TableView<TareaPrioritaria> tablaPrioridad;
    @FXML
    private TableColumn<TareaPrioritaria, String> colZona;
    @FXML
    private TableColumn<TareaPrioritaria, String> colDesecho;
    @FXML
    private TableColumn<TareaPrioritaria, Integer> colUtilidad;
    @FXML
    private TableColumn<TareaPrioritaria, String> colPendiente;
    @FXML
    private TableColumn<TareaPrioritaria, String> colRecolectado;
    //@FXML
    //private TableColumn colAccion; // stand by

    // Lista observable 
    private ObservableList<TareaPrioritaria> tareasPendientes = FXCollections.observableArrayList();

    // boton volver al inicio
    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    // carga de datos ejemplo a la tabla
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar las columnas de la tabla para que sepa de dónde
        //   obtener los datos de la clase TareaPrioritaria
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        colDesecho.setCellValueFactory(new PropertyValueFactory<>("desecho"));
        colUtilidad.setCellValueFactory(new PropertyValueFactory<>("utilidad"));
        colPendiente.setCellValueFactory(new PropertyValueFactory<>("pendiente"));
        colRecolectado.setCellValueFactory(new PropertyValueFactory<>("recolectado"));
        
        // Cargar los datos de ejemplo
        cargarDatosDeEjemplo();
        
        // Mostrar los datos en la tabla
        tablaPrioridad.setItems(tareasPendientes);
        
        // Actualizar el panel "Próximo Despacho" con el primer item de piroridad
        actualizarPanelDespacho();
    }
    
    //metodo de seteo de ejemplos
    private void cargarDatosDeEjemplo() {
        tareasPendientes.add(new TareaPrioritaria("Hospital", "Desechos Biológicos", -5, "10 KG", "5 KG"));
        tareasPendientes.add(new TareaPrioritaria("Centro Comercial", "Plásticos", -2, "20 KG", "18 KG"));
        tareasPendientes.add(new TareaPrioritaria("Zona Residencial A", "Orgánicos", 1, "2 KG", "3 KG"));
        tareasPendientes.add(new TareaPrioritaria("Parque", "Común", 4, "11 KG", "15 KG"));
        tareasPendientes.add(new TareaPrioritaria("Zona Residencial B", "Vidrio", 5, "20 KG", "25 KG"));
    }
    
    //metodo que actualiza el panel izquierdo
    private void actualizarPanelDespacho() {
        if (!tareasPendientes.isEmpty()) {
            // Obtiene el primer item de la lista (el más urgente)
            TareaPrioritaria urgente = tareasPendientes.get(0);
            
            lblZonaUrgente.setText(urgente.getZona());
            lblUtilidadUrgente.setText(urgente.getUtilidad() + " (Crítico)");
            lblPendienteUrgente.setText(urgente.getPendiente());
            lblTipoDesechoUrgente.setText(urgente.getDesecho());
        } else {
            // Si esque no hay tareas, limpia el panel
            lblZonaUrgente.setText("---");
            lblUtilidadUrgente.setText("---");
            lblPendienteUrgente.setText("---");
            lblTipoDesechoUrgente.setText("---");
        }
    }

    //boton despachar , para simular el despacho
    @FXML
    private void despacharSiguiente(ActionEvent event) {
        if (!tareasPendientes.isEmpty()) {
            //elimina el primer item
            tareasPendientes.remove(0);
            
            //actualiza el panel izquierdo con la nuev aurgencia
            actualizarPanelDespacho();
        }
    }
}