package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;

//import ec.edu.espol.ed_p1_grupo03.Residuo;
import java.util.LinkedList;
import java.util.List;


public class Zona implements Comparable<Zona>{
    private String nombre;
    private double pRecolectado;
    private double pPendiente;
    
    // Lista Temporal de java util
    private List<ResiduoTemp> residuos; 

    public Zona(String nombre, double pRecolectadoInicial) {
        this.nombre = nombre;
        this.pRecolectado = pRecolectadoInicial;
        this.pPendiente = 0;
        //temporal
        this.residuos = new LinkedList<>();
    }

    public void agregarResiduo(ResiduoTemp r) {
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
    public List<ResiduoTemp> getResiduos() { return residuos; }
}
