/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;
import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona;

public class ArbolBinarioUtilidad {
    
    private NodoArbol raiz;

    public ArbolBinarioUtilidad() {
        this.raiz = null;
    }

    // Método  para agregar 
    public void agregar(Zona nuevaZona) {
        if(nuevaZona == null) return;
        this.raiz = agregarRecursivo(this.raiz, nuevaZona);
    }

    // Método privado recursivo para agregar
    private NodoArbol agregarRecursivo(NodoArbol actual, Zona nuevaZona) {
        //casoos base
        if (actual == null) {
            return new NodoArbol(nuevaZona);
        }

        //  Comparar utilidades
        double utilidadNueva = nuevaZona.getUtilidad();
        double utilidadActual = actual.getContenido().getUtilidad();

        // Si es MENOR, va a la IZQUIERDA
        if (utilidadNueva < utilidadActual) {
            actual.setIzquierda(agregarRecursivo(actual.getIzquierda(), nuevaZona));
        } 
        // Si es MAYOR o IGUAL, va a la DERECHA
        else if (utilidadNueva >= utilidadActual) {
            actual.setDerecha(agregarRecursivo(actual.getDerecha(), nuevaZona));
        }

        return actual;
    }
    

    //visualizar
    public void imprimirEnOrden() {
        System.out.println("--- Árbol Ordenado por Utilidad (Menor a Mayor) ---");
        imprimirEnOrdenRecursivo(raiz);
        System.out.println("---------------------------------------------------");
    }

    private void imprimirEnOrdenRecursivo(NodoArbol nodo) {
        if (nodo != null) {
            imprimirEnOrdenRecursivo(nodo.getIzquierda());
            System.out.printf("Utilidad: %.2f | Zona: %s\n", 
                              nodo.getContenido().getUtilidad(), 
                              nodo.getContenido().getNombre());
            imprimirEnOrdenRecursivo(nodo.getDerecha());
        }
    }
    
    // Método para ver la estructura (quién es padre de quién)
    public void imprimirEstructura() {
        System.out.println("\n--- Estructura Visual del Árbol ---");
        imprimirEstructuraRecursiva(raiz, "", true);
    }
    
    private void imprimirEstructuraRecursiva(NodoArbol nodo, String prefijo, boolean esCola) {
        if (nodo != null) {
            System.out.println(prefijo + (esCola ? "└── " : "├── ") + 
                    "[" + nodo.getContenido().getUtilidad() + "] " + nodo.getContenido().getNombre());
            
            imprimirEstructuraRecursiva(nodo.getIzquierda(), prefijo + (esCola ? "    " : "│   "), false);
            imprimirEstructuraRecursiva(nodo.getDerecha(), prefijo + (esCola ? "    " : "│   "), true);
        }
    }
}
