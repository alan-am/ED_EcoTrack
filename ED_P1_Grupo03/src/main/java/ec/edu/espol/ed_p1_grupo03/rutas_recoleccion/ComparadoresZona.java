
package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import java.util.Comparator;

/**
 *
 * @author Alan
 */
public class ComparadoresZona{
        
        //Algunos comparadores adicionales.

    //Prioridad = menor utilidad
    public static Comparator<Zona> porUtilidad() {
        return Comparator.comparingDouble(Zona::getUtilidad);
    }

    // Prioridad = Mayor peso
    public static Comparator<Zona> porCantidadResiduos() {
        return (z1, z2) -> Double.compare(z2.getPPendiente(), z1.getPPendiente());
    }
    
    // prioridad = orden alfabetico Ascendente
    public static Comparator<Zona> porNombre() {
        return Comparator.comparing(Zona::getNombre);
    }
}
