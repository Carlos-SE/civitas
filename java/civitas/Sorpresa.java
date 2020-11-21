/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;


/**
 *

 */
public class Sorpresa {
    private String texto;
    private int valor;
    
    //pertenecientes a otras clases
    MazoSorpresas mazoS;
    TipoSorpresa tipoS;
    Tablero tablero;
    
    //contructores 
    
    //constructor para construir la sorpresa que se envia a la carcel
    Sorpresa(TipoSorpresa tipoS, Tablero tablero){
        this(tipoS,tablero,-1,tipoS.toString());
    }
    
    //constructor para construir la sorpresa que se envia al jugador a otra casilla
    Sorpresa(TipoSorpresa tipoS, Tablero tablero, int valor, String texto){
        this.tipoS = tipoS;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
        this.mazoS = null;
    }
    
    //constructor para construir la sorpresa que permite evitar la carcel
    Sorpresa(TipoSorpresa tipoS,  int valor, String texto){
        this(tipoS, null, valor, texto);
    }
     
     
    //constructor para el resto de sorpresas
    Sorpresa(TipoSorpresa tipoS, MazoSorpresas mazoS){
         this.tipoS = tipoS;
         this.mazoS = mazoS;
         
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        
        switch(tipoS){
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual,todos);
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual,todos);
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual,todos);
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual,todos);
            case PORCASAHOTEL:
                aplicarAJugador_porCasaHotel(actual,todos);
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual,todos);
        }
        
    }
    
    private void init(){
        valor = -1;
        mazoS = null;
        tablero = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return (actual>= 0) && (actual<todos.size());
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("se esta aplicando una sorpresa al jugador" +todos.get(actual).getNombre());
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos); 
            
            int casilla=todos.get(actual).getNumCasillaActual();
            int tirada=tablero.nuevaPosicion(actual,valor);
            int posicion=tablero.nuevaPosicion(actual,tirada);
            
            todos.get(actual).moverACasilla(posicion);
           // tableroS.getCasilla(posicion).recibeJugador(actual,todos);
        }
    }
    
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos){
        boolean correcto = jugadorCorrecto(actual,todos);
        
        
        if(correcto){
            todos.get(actual).encarcelar(tablero.getCarcel());
            informe(actual,todos);
        }
    }
    
    void salirDelMazo(){
        if(tipoS == TipoSorpresa.SALIRCARCEL){
            mazoS.inhabilitarCartaEspecial(this);
        }
    }
    
    void usada(){
        if(tipoS ==TipoSorpresa.SALIRCARCEL){
            mazoS.habilitarCartaEspecial(this);
        }
    }
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos){
        
        Jugador jugador = todos.get(actual);
        boolean correcto = jugadorCorrecto(actual,todos);
        
        if(correcto){
            informe(actual,todos);
            boolean haySalvoConducto = false;
            
            for(int i=0; i<todos.size(); i++){
                if(todos.get(i).tieneSalvoconducto()){
                    haySalvoConducto=true;
                }
            }
            
            if(!haySalvoConducto){
                jugador.obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }
    
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        Jugador jugador = todos.get(actual);
        boolean correcto = jugadorCorrecto(actual,todos);
        
        if(correcto){
            informe(actual,todos);
            jugador.modificarSaldo(valor);
        }
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        Jugador jugador = todos.get(actual);
        boolean correcto = jugadorCorrecto(actual,todos);
        
        if(correcto){
            informe(actual,todos);
            jugador.modificarSaldo(valor * jugador.cantidadCasasHoteles());
        }
    }
    
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos){
        Jugador jugador = todos.get(actual);
        boolean correcto = jugadorCorrecto(actual,todos);
        
        if(correcto){
            informe(actual,todos);
            Sorpresa sorpresa1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, -1*valor , "dar dinero al jugador actual");
            Sorpresa sorpresa2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, (todos.size()-1)*valor , "dar dinero al jugador actual");
        

            for(int i=0 ; i<todos.size(); i++){
                if(todos.get(i) != jugador){
                    sorpresa1.aplicarAJugador_pagarCobrar(i,todos);
                }
            }
            sorpresa2.aplicarAJugador_pagarCobrar(actual,todos);
        }
    }
}