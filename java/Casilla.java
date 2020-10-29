/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author mota
 */
public class Casilla {
    
    private static int carcel;
    
    private String nombre;
    private float importe;
    
    //asociaciones
    private TituloPropiedad tituloP;
    private Sorpresa sorpresa;
    private MazoSorpresas mazoS;
    private TipoCasillas tipo;
    
    //metodo init
    private void init(){
        nombre = "";
        tituloP = null;
        sorpresa = null;
        mazoS = null;
        importe = 0f;
    }
    //constructores
    Casilla(TituloPropiedad titulo){
        init();
        tituloP = titulo;
        nombre = titulo.getNombre();
        tipo = TipoCasillas.CALLE;
    }
    
    Casilla(String nom){
        init();
        tipo = TipoCasillas.DESCANSO;
        nombre = nom;
    }
    
    Casilla(float cantidad, String nom){
        init();
        importe = cantidad;
        nombre = nom;
        tipo = TipoCasillas.IMPUESTO;
    }
    
    Casilla(int numCasillaCarcel , String nom){
        init();
        carcel = numCasillaCarcel;
        nombre = nom;
        tipo = TipoCasillas.JUEZ;
        
    }
  
    Casilla(MazoSorpresas mazo, String nom){
        init();
        mazoS = mazo;
        nombre = nom;
        tipo = TipoCasillas.SORPRESA;
    }
    
    
    public String getNombre(){
        return nombre;
    }
    
    public static int getCarcel(){
        return carcel;
    }
    
    private TituloPropiedad getTituloPropiedad(){
        return tituloP;
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento(todos.get(actual).getNombre()+"esta en la casilla"+nombre+toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos ){
        boolean correcto=false;
        
        if(actual >= 0 && todos.size() < actual){
            correcto = true;
        }
        return correcto;
    }
    
   
    
    /* NO necesario implementar en esta practica
    
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        
    }
    
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos){
        
    }
    
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos){
        
    }*/
    
    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    
    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
    
    
   
    @Override
    public String toString(){
        String parametros = "Nombre :"+nombre+" importe: "+importe+" carcel "
                +carcel+" titulo de propiedad: "+tituloP+" sorpresaa : "
                +sorpresa+" mazo sosrpresa: "+mazoS;
        return parametros;
    }
}