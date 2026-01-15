
package ec.edu.espol.ed_p1_grupo03;

import java.io.Serializable;


public class NodoCircular<E> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private E contenido;
    private NodoCircular<E> siguiente;
    private NodoCircular<E> anterior;

    // Constructor 
    public NodoCircular(E contenido) {
        this.contenido = contenido;
        this.siguiente = null;
        this.anterior = null;
    }
    
    // Getters y Setters
    public E getContenido() { return contenido; }
    public void setContenido(E contenido) { this.contenido = contenido; }
    
    public NodoCircular<E> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoCircular<E> siguiente) { this.siguiente = siguiente; }
    
    public NodoCircular<E> getAnterior() { return anterior; }
    public void setAnterior(NodoCircular<E> anterior) { this.anterior = anterior; }
}