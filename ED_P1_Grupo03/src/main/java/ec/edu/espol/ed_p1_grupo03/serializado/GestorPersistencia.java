
package ec.edu.espol.ed_p1_grupo03.serializado;

import ec.edu.espol.ed_p1_grupo03.ListaCircularDoble;
import ec.edu.espol.ed_p1_grupo03.Residuo;
import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    
    private static final String ARCHIVO_ZONAS = "datos_zonas.ser";
    private static final String ARCHIVO_RESIDUOS = "datos_residuos.ser";


public static void guardarDatos(List<Zona> zonas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_ZONAS))) {
            oos.writeObject(zonas);
        } catch (IOException e) {
            System.out.println("Error al serializar y guardar ZONAS");
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static List<Zona> cargarDatos() {
        File archivo = new File(ARCHIVO_ZONAS);
        
        if (!archivo.exists()) {
            System.out.println("No se encontraron datos previos. Iniciando sistema limpio.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Zona>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos. Se iniciará una lista vacía.");
            return new ArrayList<>();
        }
    }
    
    
    public static void guardarResiduos(ListaCircularDoble<Residuo> residuos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_RESIDUOS))) {
            oos.writeObject(residuos);
            System.out.println("Residuos guardados en " + ARCHIVO_RESIDUOS);
        } catch (IOException e) {
            System.err.println("Error guardando residuos: " + e.getMessage());
        }
    }
    
    public static ListaCircularDoble<Residuo> cargarResiduos() {
        File archivo = new File(ARCHIVO_RESIDUOS);
        if (!archivo.exists()) {
            return new ListaCircularDoble<>(); // Retorna lista vacía
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (ListaCircularDoble<Residuo>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error cargando residuos. Se crea lista nueva.");
            return new ListaCircularDoble<>();
        }
    }
}