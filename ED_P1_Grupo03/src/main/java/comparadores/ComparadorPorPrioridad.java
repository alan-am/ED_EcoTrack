/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparadores;

/**
 *
 * @author ambar
 */
import java.util.Comparator;
import ec.edu.espol.ed_p1_grupo03.Residuo;

public class ComparadorPorPrioridad implements Comparator<Residuo> {

    private int valorPrioridad(String p) {
        if (p == null) return 0;
        switch (p.toLowerCase()) {
            case "alta": return 1;
            case "media": return 2;
            case "baja": return 3;
            default: return 0;
        }
    }
    @Override
    public int compare(Residuo r1, Residuo r2) {
        return Integer.compare(valorPrioridad(r1.getPrioridad()), valorPrioridad(r2.getPrioridad()));
    }
}