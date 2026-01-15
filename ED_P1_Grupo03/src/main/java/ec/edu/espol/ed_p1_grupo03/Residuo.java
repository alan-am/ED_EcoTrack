
package ec.edu.espol.ed_p1_grupo03;

import java.io.Serializable;

/**
 *
 * @author ambar
 */
public class Residuo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
    private String tipo;
    private double peso;
    private String zona;
    private String fecha;
    private String prioridad;

    public Residuo(String id, String nombre, String tipo,double peso, String zona, String fecha, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
        this.zona = zona;
        this.fecha = fecha;
        this.prioridad = prioridad;
    }

    //Constructor para rutasRecoleccion
    public Residuo(String nombre, String tipo, double peso) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
    }
    
    
    public String getId() { 
        return id; 
    }
    
    public String getNombre() { 
        return nombre;
    }
    
   
    public String getTipo() {
        return tipo;
    }
    
    public double getPeso() { 
        return peso; 
    }
    
    public String getZona() { 
        return zona; 
    }
    
    public String getFecha() { 
        return fecha; 
    }
    
    public String getPrioridad() { 
        return prioridad; 
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    
    public void setPeso(double peso) { 
        this.peso = peso;
    }
    
    public void setZona(String zona) { 
        this.zona = zona;
    }
    
    public void setFecha(String fecha) { 
        this.fecha = fecha;
    }
    
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    
    @Override
    public String toString() { return nombre + " (" + peso + "kg)"; }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Residuo otro = (Residuo) obj;
        // Asumimos que el ID es único. Si no compara por nombre.
        return this.id != null && this.id.equals(otro.id);
    }
    
}
