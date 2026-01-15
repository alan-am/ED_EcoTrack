package ec.edu.espol.ed_p1_grupo03;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaCircularDoble<E> implements Serializable, Iterable<E> {
    
    private static final long serialVersionUID = 1L;
    
    private NodoCircular<E> cabeza;
    private NodoCircular<E> ultimo;
    private int tamano;

    public ListaCircularDoble() {
        this.cabeza = null;
        this.ultimo = null;
        this.tamano = 0;
    }

    // Agrega un elemento al final
    public void addLast(E contenido) {
        if (contenido == null) return;
        
        NodoCircular<E> nuevo = new NodoCircular<>(contenido);
        
        if (isEmpty()) {
            cabeza = nuevo;
            ultimo = nuevo;
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(cabeza); 
            cabeza.setAnterior(nuevo);  
            ultimo = nuevo;
        }
        tamano++;
    }

    public boolean remove(E contenido) {
        if (isEmpty()) return false;

        NodoCircular<E> actual = cabeza;
        do {
            if (actual.getContenido().equals(contenido)) {
                unlink(actual);
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        
        return false;
    }

    private void unlink(NodoCircular<E> nodo) {
        if (tamano == 1) {
            cabeza = null;
            ultimo = null;
        } else {
            nodo.getAnterior().setSiguiente(nodo.getSiguiente());
            nodo.getSiguiente().setAnterior(nodo.getAnterior());
            
            if (nodo == cabeza) cabeza = nodo.getSiguiente();
            if (nodo == ultimo) ultimo = nodo.getAnterior();
        }
        tamano--;
    }

    public E get(int index) {
        if (index < 0 || index >= tamano) throw new IndexOutOfBoundsException();
        
        NodoCircular<E> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getContenido();
    }

    public int size() { return tamano; }
    public boolean isEmpty() { return tamano == 0; }
    public void clear() {
        cabeza = null;
        ultimo = null;
        tamano = 0;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new IteradorLista();
    }

    private class IteradorLista implements Iterator<E> {
        private NodoCircular<E> actual = cabeza;
        private int contador = 0;

        @Override
        public boolean hasNext() {
            return contador < tamano;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E dato = actual.getContenido();
            actual = actual.getSiguiente();
            contador++;
            return dato;
        }
    }
    
    
    public NodoCircular<E> getHeaderNode() {
        return cabeza;
    }

    public NodoCircular<E> getLastNode() {
        return ultimo;
    }
}