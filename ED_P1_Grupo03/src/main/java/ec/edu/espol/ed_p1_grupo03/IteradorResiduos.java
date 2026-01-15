package ec.edu.espol.ed_p1_grupo03;

import java.util.Iterator;

// Iterador para recorrer la lista de residuos
public class IteradorResiduos implements Iterator<Residuo> {
    
    private NodoCircular<Residuo> actual;
    private NodoCircular<Residuo> cabeza; 
    private boolean primeraVuelta;

    public IteradorResiduos(ListaCircularDoble<Residuo> lista) {
        if (lista != null && !lista.isEmpty()) {
            this.cabeza = lista.getHeaderNode(); 
            this.actual = lista.getHeaderNode();
            this.primeraVuelta = true;
        } else {
            this.actual = null;
        }
    }

    @Override
    public boolean hasNext() {
        if (actual == null) return false;
        
        // Si dimos la vuelta completa y volvimos al inicio, paramos
        if (!primeraVuelta && actual == cabeza) {
            return false;
        }
        return true;
    }

    @Override
    public Residuo next() {
        if (actual == null) return null;

        Residuo dato = actual.getContenido(); 
        
        actual = actual.getSiguiente();
        primeraVuelta = false;
        
        return dato;
    }
}