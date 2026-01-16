/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona;

public class NodoArbol {
    
    private Zona contenido;
    private NodoArbol izquierda;
    private NodoArbol derecha;

    public NodoArbol(Zona contenido) {
        this.contenido = contenido;
        this.izquierda = null;
        this.derecha = null;
    }

    // Getters y Setters
    public Zona getContenido() { return contenido; }
    public void setContenido(Zona contenido) { this.contenido = contenido; }

    public NodoArbol getIzquierda() { return izquierda; }
    public void setIzquierda(NodoArbol izquierda) { this.izquierda = izquierda; }

    public NodoArbol getDerecha() { return derecha; }
    public void setDerecha(NodoArbol derecha) { this.derecha = derecha; }
}
