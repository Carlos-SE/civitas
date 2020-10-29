/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
/**
 *
 * @author mota
 */
public class CivitasJuego {
    private int indiceJugadorActual;
    
    //asociaciones
    private MazoSorpresas mazoS;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private GestorEstados gestorE;
    private EstadosJuego estado;
    
    //constructor
    
    CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList<Jugador>();
        
        for(String n:nombres){
            jugadores.add(new Jugador(n));
        }
        
        mazoS = new MazoSorpresas();
        
        gestorE = new GestorEstados();
        
        estado = gestorE.estadoInicial();
        
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        
        //inicializar
        //inicializaTablero(mazoS);
        inicializarMazoSorpresa(tablero);
        
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(Casilla.getCarcel());
        
        for(int i=1; i<3; i++){
            TituloPropiedad propiedad = new TituloPropiedad("calle"+i,100*i,3,50*i,500+100*i,200);
            Casilla cas = new Casilla(propiedad);
            tablero.añadeCasilla(cas);
        }
        
        Casilla cas1;
        
        cas1 = new Casilla(mazoS,"SorpresaCasilla");
        tablero.añadeCasilla(cas1);
        tablero.añadeJuez();
        
    }
    
    private void inicializarMazoSorpresa(Tablero tablero){
        
        Random random = new Random();
        
        for(TipoSorpresa tipo:TipoSorpresa.values()){
        
            switch(tipo){
                case IRCARCEL:
                    mazoS.alMazo(new Sorpresa(tipo,tablero));
                break;
                case SALIRCARCEL:
                    mazoS.alMazo(new Sorpresa(tipo,mazoS));
                break;
                case IRCASILLA:
                    
                    mazoS.alMazo(new Sorpresa(tipo,tablero,random.nextInt(tablero.size()),tipo.toString()));
                break;
                default:
                    mazoS.alMazo(new Sorpresa(tipo,random.nextInt(400)+100,tipo.toString()));

            }
        
        }
    }
    
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida()>0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    private void pasarTurno(){
        if(indiceJugadorActual<=jugadores.size()){
            indiceJugadorActual++;
        }else{
            indiceJugadorActual=0;
        }
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorE.siguienteEstado(getJugadorActual(), estado, operacion);
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    /*
    public boolean cancelarHipotecta(){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca();
    }
    */
    /*
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    } 
    */
    /*
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    } 
    */
    /*
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar();
    } 
    */
            
    private ArrayList<Jugador> ranking(){
        Collections.sort(jugadores);
        return jugadores;
    }
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }
    
   public String infoJugadorTexto(){
       return jugadores.get(indiceJugadorActual).toString();
   }
   
   public boolean finalDelJuego(){
       boolean resultado = false;
       
       //recorrer el array jugadores
       for(int i=0; i<jugadores.size(); i++){
           resultado = jugadores.get(i).enBancarrota();
       }
       return resultado;
   }
}
