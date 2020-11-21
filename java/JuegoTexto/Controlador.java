/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.Jugador;
import civitas.Respuestas;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.TituloPropiedad;
import civitas.TituloPropiedad;
import civitas.OperacionesJuego;
import civitas.OperacionInmobiliaria;
import civitas.GestionesInmobiliarias;


public class Controlador {
    
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego _juego, VistaTextual _vista){
        juego = _juego;
        vista = _vista;
    }
    
    void juega(){
        
        vista.setCivitasJuego(juego);
        int inicio = 1;
        
        while(!juego.finalDelJuego()){
            if (inicio == 0){
                vista.actualizarVista();
            }
            inicio = 0;
            
            vista.pausa();
            
            
            OperacionesJuego operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);
            
            if (operacion != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                switch(operacion){
                    case COMPRAR:
                        if(vista.comprar() == Respuestas.SI){
                            juego.comprar();
                        }
                        break;
                    case GESTIONAR:
                        boolean termina = false;
                        vista.gestionar();
                        
                        GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[vista.getGestion()];
                        OperacionInmobiliaria operacionInm = new OperacionInmobiliaria(gestion, vista.getPropiedad());
                            
                        //OperacionInmobiliaria operacionInm = new OperacionInmobiliaria(GestionesInmobiliarias.values()[gestion], propiedad);
                            
                        switch(operacionInm.getGestion()){
                            case VENDER:
                                juego.vender(operacionInm.getNumPropiedad());
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(operacionInm.getNumPropiedad());
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipotecta(operacionInm.getNumPropiedad());
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(operacionInm.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(operacionInm.getNumPropiedad());
                                break;
                            case TERMINAR:
                                termina = true;
                                juego.siguientePaso();
                        }
                        
                        break;
                
                    case   SALIR_CARCEL:
                        if(vista.salirCarcel() == SalidasCarcel.PAGANDO){
                            juego.salirCarcelPagando();
                        }
                        else{
                            juego.salirCarcelTirando();
                        }
                        juego.siguientePaso();
                                    
                    }
                        
                }
            }
        }
        
    }
    
