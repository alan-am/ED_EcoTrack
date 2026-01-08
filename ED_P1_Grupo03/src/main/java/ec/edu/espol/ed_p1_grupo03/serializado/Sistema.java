
package ec.edu.espol.ed_p1_grupo03.serializado;

import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona;
import java.util.List;

public class Sistema {
 
    private static Sistema instancia;
    
    // la lista original
    private List<Zona> zonas;

    private Sistema() {
        //carga de datos al iniciar
        this.zonas = GestorPersistencia.cargarDatos();
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
    
    // Método para guardar todo antes de cerrar
    public void guardarEstado() {
        GestorPersistencia.guardarDatos(this.zonas);
    }
}
