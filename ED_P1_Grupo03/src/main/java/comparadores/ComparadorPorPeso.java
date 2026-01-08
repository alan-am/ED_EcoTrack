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

public class ComparadorPorPeso implements Comparator<Residuo> {

    @Override
    public int compare(Residuo r1, Residuo r2) {
        return Double.compare(r1.getPeso(), r2.getPeso());
    }
}

