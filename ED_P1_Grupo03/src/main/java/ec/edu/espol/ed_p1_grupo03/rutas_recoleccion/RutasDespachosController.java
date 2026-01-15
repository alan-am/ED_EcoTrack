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
import ec.edu.espol.ed_p1_grupo03.Residuo;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import ec.edu.espol.ed_p1_grupo03.serializado.Sistema;

public class RutasDespachosController implements Initializable {
    
    
    //--------------------
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
    // boton volver al inicio
    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
    //@FXML
    //private TableColumn colAccion; // stand by
    
    
    //--------------------------------------Atributos de Negocio
    private  PriorityQueue<Zona> colaZonas;
    
    
    // Lista observable(Para tabla visual)
    private ObservableList<TareaPrioritaria> tareasPendientes = FXCollections.observableArrayList();


    //inicializa la ventana
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colaZonas = new PriorityQueue<>(ComparadoresZona.porUtilidad());
        configurarColumnasVista();
        
        tablaPrioridad.setItems(tareasPendientes);
        
        List<Zona> zonasDelSistema = Sistema.getInstance().getZonas();
        
        // Si el sistema está vacío (primera vez), creamos datos de prueba y los guardamos en el sistema
        if (zonasDelSistema.isEmpty()) {
            cargarDatosSimulados(zonasDelSistema);
        }
        
        colaZonas.addAll(zonasDelSistema);
        actualizarVista();
    }
    
    //metodo de seteo de ejemplos
    private void cargarDatosSimulados(List<Zona> destino) {
        Zona z1 = new Zona("Hospital", 50); 
        z1.agregarResiduo(new Residuo("Jeringas", "Biológico", 80));
        
        Zona z2 = new Zona("Parque", 20);
        z2.agregarResiduo(new Residuo("Botellas", "Plástico", 10));
        
        destino.add(z2);
        destino.add(z1);
    }
    

    private void actualizarVista() {
        tareasPendientes.clear();
        
        //implementacion no propia temporal
        List<Zona> listaVisual = new ArrayList<>(colaZonas);
        // ordenamos la lista visual usando el MISMO comparador que tenga la cola actualmente
        listaVisual.sort(colaZonas.comparator());
        
        
        // Convertir Zona a TareaPrioritaria
        for (Zona z : listaVisual) {
            
             String tiposResiduos = "Varios"; //luego concatenar tipos
             tareasPendientes.add(new TareaPrioritaria(
                 z.getNombre(), 
                 tiposResiduos, 
                 (int) z.getUtilidad(), 
                 z.getPPendiente() + " KG", 
                 z.getPRecolectado() + " KG"
             ));
        }

        // Actualizar Panel "Proximo despacho"
        Zona proxima = colaZonas.peek();
        if (proxima != null) {
            lblZonaUrgente.setText(proxima.getNombre()); 
            lblUtilidadUrgente.setText(String.format("%.2f (Crítico)", proxima.getUtilidad())); 
            lblPendienteUrgente.setText(proxima.getPPendiente() + " KG");
            String tipo = proxima.getResiduos().isEmpty() ? "---" : proxima.getResiduos().get(0).getTipo();
            lblTipoDesechoUrgente.setText(tipo);
        } else {
            lblZonaUrgente.setText("---");
            lblUtilidadUrgente.setText("---");
            lblPendienteUrgente.setText("---");
            lblTipoDesechoUrgente.setText("---");
        }
    }


    //boton despachar , para simular el despacho
    @FXML
    private void despacharSiguiente(ActionEvent event) {
        if (!colaZonas.isEmpty()) {
            Zona atendida = colaZonas.poll(); // Saca el de mayor prioridad 
            Sistema.getInstance().getZonas().remove(atendida); //sacamos tmb de la lista padre
            System.out.println("Despachando camión a: " + atendida.getNombre());
            //luego implementar adicional de ventana "Residuos Procesados"
            actualizarVista();
            
        }
        
    }
    
    private void configurarColumnasVista(){
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        colDesecho.setCellValueFactory(new PropertyValueFactory<>("desecho"));
        colUtilidad.setCellValueFactory(new PropertyValueFactory<>("utilidad"));
        colPendiente.setCellValueFactory(new PropertyValueFactory<>("pendiente"));
        colRecolectado.setCellValueFactory(new PropertyValueFactory<>("recolectado"));
    }
}