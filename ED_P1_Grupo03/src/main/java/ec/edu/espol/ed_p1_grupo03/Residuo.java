/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.ed_p1_grupo03;

/**
 *
 * @author ambar
 */
public class Residuo {
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
    
}
