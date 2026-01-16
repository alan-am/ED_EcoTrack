package ec.edu.espol.ed_p1_grupo03.exportar_importar;

/**
 *
 * @author Alan
 */


import ec.edu.espol.ed_p1_grupo03.Residuo;
import ec.edu.espol.ed_p1_grupo03.rutas_recoleccion.Zona;
import ec.edu.espol.ed_p1_grupo03.serializado.Sistema;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Importador {


    public static int importarResiduos(File archivo, boolean esCSV) throws IOException {
        if (esCSV) return importarResiduosCSV(archivo);
        else return importarResiduosJSON(archivo);
    }

    private static int importarResiduosCSV(File archivo) throws IOException {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; } // Saltar cabecera
                
                // Formato esperado: ID,NOMBRE,TIPO,PESO,ZONA,FECHA,PRIORIDAD
                String[] partes = linea.split(",");
                if (partes.length >= 7) {
                    Residuo r = new Residuo(
                        partes[0].trim(), // id
                        partes[1].trim(), // nombre
                        partes[2].trim(), // tipo
                        Double.parseDouble(partes[3].trim()), // peso
                        partes[4].trim(), // zona
                        partes[5].trim(), // fecha
                        partes[6].trim()  // prioridad
                    );
                    Sistema.getInstance().getResiduos().addLast(r); // Guardar en lista delsistema
                    contador++;
                }
            }
        }
        return contador;
    }

    private static int importarResiduosJSON(File archivo) throws IOException {
        String contenido = leerArchivoCompleto(archivo);
        int contador = 0;
        
        // Regex para capturar objetos JSON simples
        Pattern patron = Pattern.compile("\\{\\s*\"id\":\\s*\"(.*?)\",\\s*\"nombre\":\\s*\"(.*?)\",\\s*\"tipo\":\\s*\"(.*?)\",\\s*\"peso\":\\s*([0-9.]+),\\s*\"zona\":\\s*\"(.*?)\",\\s*\"prioridad\":\\s*\"(.*?)\"\\s*\\}");
        Matcher matcher = patron.matcher(contenido);

        while (matcher.find()) {
            try {
                Residuo r = new Residuo(
                    matcher.group(1), // id
                    matcher.group(2), // nombre
                    matcher.group(3), // tipo
                    Double.parseDouble(matcher.group(4)), // peso
                    matcher.group(5), // zona
                    "N/A",            // fecha (si no estaba en el json, colocamos la default)
                    matcher.group(6)  // prioridad
                );
                Sistema.getInstance().getResiduos().addLast(r);
                contador++;
            } catch (Exception e) {
                System.err.println("Error parseando un residuo JSON: " + e.getMessage());
            }
        }
        return contador;
    }
    
    
    
    

    // IMPORTAR ZONAS 
    public static int importarZonas(File archivo, boolean esCSV) throws IOException {
        if (esCSV) return importarZonasCSV(archivo);
        else return importarZonasJSON(archivo);
    }

    private static int importarZonasCSV(File archivo) throws IOException {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; }
                
                // Formato CSV Zona: NOMBRE,UTILIDAD,PENDIENTE,RECOLECTADO,DETALLES
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    
                    String nombre = partes[0].trim();
                    double pendiente = Double.parseDouble(partes[2].trim());   
                    double recolectado = Double.parseDouble(partes[3].trim());
                    
                    Zona z = new Zona(nombre, recolectado, pendiente);

                    Sistema.getInstance().getZonas().add(z);
                    contador++;
                }
            }
        }
        return contador;
    }

    private static int importarZonasJSON(File archivo) throws IOException {
        String contenido = leerArchivoCompleto(archivo);
        int contador = 0;

        Pattern patronZona = Pattern.compile("\"zona\":\\s*\"(.*?)\"[\\s\\S]*?\"pendiente\":\\s*([0-9.]+)[\\s\\S]*?\"recolectado\":\\s*([0-9.]+)");
        Matcher matcher = patronZona.matcher(contenido);

        while (matcher.find()) {
            try {
                String nombre = matcher.group(1);
                double pendiente = Double.parseDouble(matcher.group(2));   // Capturamos pendiente
                double recolectado = Double.parseDouble(matcher.group(3)); // Capturamos recolectado

                Zona nuevaZona = new Zona(nombre, recolectado, pendiente);
                
                Sistema.getInstance().getZonas().add(nuevaZona);
                contador++;
            } catch (Exception e) {
                 System.err.println("Error parseando zona JSON: " + e.getMessage());
            }
        }
        return contador;
    }

    // util
    private static String leerArchivoCompleto(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n"); // alto de línea para facilitar regex
            }
        }
        return sb.toString();
    }
}

