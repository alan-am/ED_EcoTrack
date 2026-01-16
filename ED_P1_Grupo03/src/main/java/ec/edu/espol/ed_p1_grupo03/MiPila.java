package ec.edu.espol.ed_p1_grupo03;

public class MiPila<T> {
    private NodoPila<T> cima;
    private int tamano;

    // Clase interna simple para la pila
    private class NodoPila<E> {
        E contenido;
        NodoPila<E> siguiente;
        public NodoPila(E contenido) { this.contenido = contenido; }
    }

    public MiPila() {
        this.cima = null;
        this.tamano = 0;
    }

    public void push(T elemento) {
        if (elemento == null) return;
        NodoPila<T> nuevo = new NodoPila<>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamano++;
    }

    public T pop() {
        if (isEmpty()) return null;
        T data = cima.contenido;
        cima = cima.siguiente;
        tamano--;
        return data;
    }

    public boolean isEmpty() { return cima == null; }
    public int size() { return tamano; }
}