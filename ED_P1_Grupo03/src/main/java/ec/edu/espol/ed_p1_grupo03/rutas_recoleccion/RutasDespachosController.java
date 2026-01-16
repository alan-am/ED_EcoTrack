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
import java.util.Comparator;

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
        
        colaZonas.addAll(zonasDelSistema);
        actualizarVista();
        imprimirDemostracionComparativa();
        
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
   

    //metodo para crear e imprimir en consola el maxheap y visualizar su correcta ejecucion
    public void imprimirDemostracionComparativa() {
        System.out.println("\n=========================================================================");
        System.out.println("       DEMOSTRACIÓN DE ESTRUCTURA DE DATOS: MAX-HEAP");
        System.out.println("=========================================================================\n");

        // 1. Preparación de Datos
        List<Zona> listaSinOrden = new ArrayList<>();
        
        // Se añaden zonas simuladas con diferentes niveles de pendiente/utilidad
        listaSinOrden.add(new Zona("Zona Industrial", 100.0, 500.0));  
        listaSinOrden.add(new Zona("Parque Central", 20.0, 15.0));     
        listaSinOrden.add(new Zona("Mercado Mayorista", 50.0, 300.0)); 
        listaSinOrden.add(new Zona("Residencial Norte", 80.0, 80.0));  
        listaSinOrden.add(new Zona("Hospital General", 10.0, 950.0));  
        
        // Se añaden los datos reales que ya existen en el sistema
        listaSinOrden.addAll(Sistema.getInstance().getZonas());

        // ----------------------------------------------------------------------------------
        // VISUALIZACIÓN 1: ESTADO ANTES (Lista Desordenada / Orden de Inserción)
        // ----------------------------------------------------------------------------------
        System.out.println(">>> 1. ESTADO INICIAL (Lista sin procesar)");
        System.out.println("    (El orden depende solo del momento en que se agregaron)");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-20s | %-15s | %-15s\n", "ZONA", "UTILIDAD", "PENDIENTE");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Zona z : listaSinOrden) {
            System.out.printf("%-20s | %-15.2f | %-15.2f\n", 
                              z.getNombre(), 
                              z.getUtilidad(), 
                              z.getPPendiente());
        }
        System.out.println("-------------------------------------------------------------------------\n");


        // ----------------------------------------------------------------------------------
        // PROCESAMIENTO: CONSTRUCCIÓN DEL MAX-HEAP
        // ----------------------------------------------------------------------------------
        // Definición del comparador inverso: (z2, z1) para ordenar de Mayor a Menor Utilidad
        Comparator<Zona> comparadorMax = (z1, z2) -> Double.compare(z2.getUtilidad(), z1.getUtilidad());
        
        PriorityQueue<Zona> maxHeap = new PriorityQueue<>(comparadorMax);
        
        // La "magia" del Heap ocurre aquí: al agregar, se reordenan internamente
        maxHeap.addAll(listaSinOrden);


        // ----------------------------------------------------------------------------------
        // VISUALIZACIÓN 2: ESTADO DESPUÉS (Extracción del Heap)
        // ----------------------------------------------------------------------------------
        System.out.println(">>> 2. ESTADO FINAL (Extracción desde Max-Heap)");
        System.out.println("    (Se garantiza que siempre sale el elemento con MAYOR utilidad primero)");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-15s | %-15s\n", "RANK", "ZONA", "UTILIDAD", "PENDIENTE");
        System.out.println("-------------------------------------------------------------------------");

        int ranking = 1;
        // extraer la raíz del árbol (el máximo) sucesivamente
        while (!maxHeap.isEmpty()) {
            Zona z = maxHeap.poll();
            System.out.printf("#%-4d | %-20s | %-15.2f | %-15.2f\n", 
                              ranking++, 
                              z.getNombre(), 
                              z.getUtilidad(), 
                              z.getPPendiente());
        }
        System.out.println("-------------------------------------------------------------------------\n");
    }
    

}