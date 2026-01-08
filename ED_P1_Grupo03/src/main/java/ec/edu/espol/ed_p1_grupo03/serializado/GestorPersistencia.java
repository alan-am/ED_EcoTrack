
package ec.edu.espol.ed_p1_grupo03.serializado;

import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {
    
    private static final String RUTA_ARCHIVO = "datos_ecotrack.ser";


    public static void guardarDatos(List<Zona> zonas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(zonas);
            System.out.println("Datos guardados exitosamente en " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static List<Zona> cargarDatos() {
        File archivo = new File(RUTA_ARCHIVO);
        
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
}