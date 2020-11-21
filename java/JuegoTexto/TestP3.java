/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

import civitas.Dado;
import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class TestP3 {
    public static void main(final String[] args){
        final VistaTextual vista = new VistaTextual();
        final ArrayList<String> jugadores = new ArrayList();
        jugadores.add("Paco");
        jugadores.add("Lucía");
        final CivitasJuego juego = new CivitasJuego(jugadores);
        Dado.getInstance().setDebug(true);
        final Controlador controlador = new Controlador(juego, vista);
        controlador.juega();
    }
}
