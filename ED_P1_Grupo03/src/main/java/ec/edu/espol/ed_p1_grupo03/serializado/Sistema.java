package ec.edu.espol.ed_p1_grupo03.serializado;

import ec.edu.espol.ed_p1_grupo03.ListaCircularDoble;
import ec.edu.espol.ed_p1_grupo03.MiPila; // <--- IMPORTANTE IMPORTAR TU PILA
import ec.edu.espol.ed_p1_grupo03.Residuo;
import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona;
import java.util.List;

public class Sistema {
 
    private static Sistema instancia;
    
    // Listas Padres
    private List<Zona> zonas;
    private ListaCircularDoble<Residuo> residuos;
    
    // AGREGAMOS LA PILA AQUÍ
    private MiPila<Residuo> pilaReciclaje; 

    private Sistema() {
        // carga de datos al iniciar
        this.zonas = GestorPersistencia.cargarDatos();
        this.residuos = GestorPersistencia.cargarResiduos();
        this.pilaReciclaje = new MiPila<>(); // Inicializamos la pila vacía
    }

    public static Sistema getInstance() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public List<Zona> getZonas() {
        return zonas;
    }
    
    public ListaCircularDoble<Residuo> getResiduos() {
        return residuos;
    }
    
    // GETTER PARA LA PILA
    public MiPila<Residuo> getPilaReciclaje() {
        return pilaReciclaje;
    }
    
    // Método para guardar todo antes de cerrar
    public void guardarEstado() {
        GestorPersistencia.guardarDatos(this.zonas);
        GestorPersistencia.guardarResiduos(residuos);
    }
}