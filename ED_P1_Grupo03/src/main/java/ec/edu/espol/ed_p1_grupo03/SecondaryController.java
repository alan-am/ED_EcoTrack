package ec.edu.espol.ed_p1_grupo03;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import comparadores.ComparadorPorPeso;
import comparadores.ComparadorPorTipo;
import comparadores.ComparadorPorPrioridad;
import java.util.Collections;


/**
 * Controlador de la pantalla de Gestión de residuos (secondary.fxml).
 */
public class SecondaryController implements Initializable {

    // -------- Formulario (lado izquierdo) --------
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPeso;
    @FXML private TextField txtFecha;

    @FXML private ComboBox<String> cbTipo;
    @FXML private ComboBox<String> cbZona;
    @FXML private ComboBox<String> cbPrioridad;

    // Combo de ordenamiento (encima de la tabla)
    @FXML private ComboBox<String> cbOrden;

    // -------- Tabla (lado derecho) --------
    @FXML private TableView<Residuo> tablaResiduos;
    @FXML private TableColumn<Residuo, String> colId;
    @FXML private TableColumn<Residuo, String> colNombre;
    @FXML private TableColumn<Residuo, String> colTipo;
    @FXML private TableColumn<Residuo, Double> colPeso;
    @FXML private TableColumn<Residuo, String> colZona;
    @FXML private TableColumn<Residuo, String> colPrioridad;
    @FXML private TableColumn<Residuo, String> colFecha;

    private final ObservableList<Residuo> listaResiduos =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Combos de tipo, zona y prioridad
        cbTipo.getItems().addAll("Orgánico", "Plástico", "Vidrio",
                                 "Metal", "Electrónico", "Otro");

        cbZona.getItems().addAll("Zona Norte", "Zona Sur",
                                 "Zona Este", "Zona Oeste");

        cbPrioridad.getItems().addAll("Alta", "Media", "Baja");

        // Combo de ordenamiento
        if (cbOrden != null) { // por seguridad
            cbOrden.getItems().addAll(
                "Peso",
                "Tipo",
                "Prioridad"
            );
            cbOrden.setValue("Peso");

        }

        // Configurar columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tablaResiduos.setItems(listaResiduos);

        // Datos de ejemplo
        listaResiduos.add(new Residuo("R-001", "Botellas PET", "Plástico",
                12.5, "Zona Norte", "01/05/2025", "Alta"));
        listaResiduos.add(new Residuo("R-002", "Latas de aluminio", "Metal",
                1.2, "Zona Oeste", "05/05/2025", "Media"));
        listaResiduos.add(new Residuo("R-003", "Residuos orgánicos", "Orgánico",
                4.8, "Zona Sur", "03/07/2025", "Alta"));
    }

    // -------- Navegación --------
    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    // -------- Acciones del formulario --------

    // Agregar nuevo residuo a la tabla
    @FXML
    private void agregarResiduo(ActionEvent event) {
        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String tipo = cbTipo.getValue();
            String zona = cbZona.getValue();
            String prioridad = cbPrioridad.getValue();
            String fecha = txtFecha.getText();
            double peso = Double.parseDouble(txtPeso.getText());

            Residuo r = new Residuo(id, nombre, tipo, peso, zona, fecha, prioridad);
            listaResiduos.add(r);
            limpiarFormulario(null);
        } catch (NumberFormatException e) {
            System.out.println("Peso inválido");
        }
    }

    // Eliminar residuo seleccionado
    @FXML
    private void eliminarResiduo(ActionEvent event) {
        Residuo r = tablaResiduos.getSelectionModel().getSelectedItem();
        if (r != null) {
            listaResiduos.remove(r);
        }
    }

    // Limpiar campos
    @FXML
    private void limpiarFormulario(ActionEvent event) {
        txtId.clear();
        txtNombre.clear();
        txtPeso.clear();
        txtFecha.clear();
        cbTipo.getSelectionModel().clearSelection();
        cbZona.getSelectionModel().clearSelection();
        cbPrioridad.getSelectionModel().clearSelection();
    }

    // Botón Modificar (puedes implementarlo luego)
    @FXML
    private void modificarResiduo(ActionEvent event) {
        // TODO: implementación de modificar
    }

    // -------- Ordenamiento --------

    @FXML
    private void aplicarOrden(ActionEvent event) {

        String criterio = cbOrden.getValue();
        if (criterio == null) return;

        if (criterio.equals("Peso")) {
            Collections.sort(listaResiduos, new ComparadorPorPeso());
        } 
        else if (criterio.equals("Tipo")) {
            Collections.sort(listaResiduos, new ComparadorPorTipo());
        } 
        else if (criterio.equals("Prioridad")) {
            Collections.sort(listaResiduos, new ComparadorPorPrioridad());
        }

        tablaResiduos.refresh();
    }


    // Convierte prioridad a número para poder ordenar
    private int prioridadValor(String p) {
        if (p == null) return 0;
        switch (p.toLowerCase()) {
            case "alta":  return 3;
            case "media": return 2;
            case "baja":  return 1;
            default:      return 0;
        }
    }
}
