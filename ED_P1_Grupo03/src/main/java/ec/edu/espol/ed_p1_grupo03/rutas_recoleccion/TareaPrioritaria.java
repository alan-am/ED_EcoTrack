package ec.edu.espol.ed_p1_grupo03.rutas_recoleccion;


//*ignorar*    Clase de ejemplo para simular datos en la interdaz de despacho y recoleccion
public class TareaPrioritaria {
    
    private String zona;
    private String desecho;
    private int utilidad;
    private String pendiente;
    private String recolectado;

    public TareaPrioritaria(String zona, String desecho, int utilidad, String pendiente, String recolectado) {
        this.zona = zona;
        this.desecho = desecho;
        this.utilidad = utilidad;
        this.pendiente = pendiente;
        this.recolectado = recolectado;
    }

    
    public String getZona() {
        return zona;
    }

    public String getDesecho() {
        return desecho;
    }

    public int getUtilidad() {
        return utilidad;
    }

    public String getPendiente() {
        return pendiente;
    }

    public String getRecolectado() {
        return recolectado;
    }
}


