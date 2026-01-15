package ec.edu.espol.ed_p1_grupo03;

public class AlmacenDatos {
    private static AlmacenDatos instancia;
    

    private ListaCircularDoble<Residuo> listaResiduos; 
    

    private MiPila<Residuo> pilaReciclaje;    

    private AlmacenDatos() {
        listaResiduos = new ListaCircularDoble<>();
        pilaReciclaje = new MiPila<>();
    }

    public static AlmacenDatos getInstance() {
        if (instancia == null) instancia = new AlmacenDatos();
        return instancia;
    }

    public ListaCircularDoble<Residuo> getListaResiduos() { return listaResiduos; }
    public MiPila<Residuo> getPilaReciclaje() { return pilaReciclaje; }
}