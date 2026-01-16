package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

import ec.edu.espol.ed_p1_grupo03.Residuo;
import ec.edu.espol.ed_p1_grupo03.exportar_importar.Formateable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Zona implements Comparable<Zona>, Serializable, Formateable{
    private String nombre;
    private double pRecolectado;
    private double pPendiente;
    
    private static final long serialVersionUID = 1L;
    
    // Lista Temporal de java util
    private List<Residuo> residuos; 

    public Zona(String nombre, double pRecolectadoInicial) {
        this.nombre = nombre;
        this.pRecolectado = pRecolectadoInicial;
        this.pPendiente = 0;
        //temporal
        this.residuos = new LinkedList<>();
    }
    
    public Zona(String nombre, double pRecolectado, double pPendiente) {
        this.nombre = nombre;
        this.pRecolectado = pRecolectado;
        this.pPendiente = pPendiente;
        this.residuos = new LinkedList<>(); 
    }

    public void agregarResiduo(Residuo r) {
        residuos.add(r);
        pPendiente += r.getPeso(); //actualizar peso
    }

    // U = P_recolectado - P_pendiente
    public double getUtilidad() {
        return pRecolectado - pPendiente;
    }

    


    // menor a mayor - las zonas con negativos salen primero
    @Override
    public int compareTo(Zona o) {
        return Double.compare(this.getUtilidad(), o.getUtilidad());
    }
    
    // Para futura exportación
    @Override
    public String toString() {
        return String.format("Zona: %s | Utilidad: %.2f | Pendiente: %.2f", nombre, getUtilidad(), pPendiente);
    }
    
    public String getNombre() { return nombre; }
    public double getPPendiente() { return pPendiente; }
    public double getPRecolectado() { return pRecolectado; }
    public List<Residuo> getResiduos() { return residuos; }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Zona otra = (Zona) obj;
        return this.nombre.equals(otra.nombre); 
    }
    
    
    
    //Metodos de Formatable
    @Override
    public String toCSV() {
        // Sae concantena el residuo junto con sus residuos anidados
        StringBuilder residuosStr = new StringBuilder();
        if (getResiduos() != null && !getResiduos().isEmpty()) {
            for (Residuo r : getResiduos()) {
                residuosStr.append(r.getTipo()).append("; ");
            }
        } else {
            residuosStr.append("Ninguno");
        }
        
        return String.format(java.util.Locale.US, "%s,%.2f,%.2f,%.2f,%s",
                nombre, getUtilidad(), pPendiente, pRecolectado, residuosStr.toString());
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"zona\": \"").append(nombre).append("\",\n");
        sb.append("      \"utilidad\": ").append(getUtilidad()).append(",\n");
        sb.append("      \"pendiente\": ").append(pPendiente).append(",\n");
        sb.append("      \"residuos\": [");
        
        //
        if (getResiduos() != null && !getResiduos().isEmpty()) {
            List<Residuo> lista = getResiduos();
            for (int i = 0; i < lista.size(); i++) {
                Residuo r = lista.get(i);
               sb.append(String.format(java.util.Locale.US, 
                        "\n        { \"tipo\": \"%s\", \"peso\": %.2f }", 
                        r.getTipo(), r.getPeso()));
                if (i < lista.size() - 1) sb.append(",");
            }
            sb.append("\n      ");
        }
        sb.append("]\n");
        sb.append("    }");
        return sb.toString();
    }
}

