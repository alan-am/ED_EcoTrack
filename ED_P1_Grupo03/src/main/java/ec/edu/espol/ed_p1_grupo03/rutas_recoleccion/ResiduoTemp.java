
package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import java.io.Serializable;

/**
 *
 * @author Alan
 */

public class ResiduoTemp implements Serializable{
    private String nombre;
    private String tipo;
    private double peso;
    private static final long serialVersionUID = 1L;

    public ResiduoTemp(String nombre, String tipo, double peso) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
    }

    public double getPeso() { return peso; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    
    @Override
    public String toString() { return nombre + " (" + peso + "kg)"; }
}