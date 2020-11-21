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
    private TituloPropiedad tituloPropiedad;
    private Sorpresa sorpresa;
    private MazoSorpresas mazoSorpresas;
    private TipoCasillas tipo;
    
    //metodo init
    private void init(){
        nombre = "";
        tituloPropiedad = null;
        sorpresa = null;
        mazoSorpresas = null;
        importe = 0f;
    }
    //constructores
    Casilla(TituloPropiedad titulo){
        init();
        tituloPropiedad = titulo;
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
        mazoSorpresas = mazo;
        nombre = nom;
        tipo = TipoCasillas.SORPRESA;
    }
    
    
    public String getNombre(){
        return nombre;
    }
    
    public static int getCarcel(){
        return carcel;
    }
    
    public TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento(todos.get(actual).getNombre()+" esta en la casilla "+nombre+toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos ){
        boolean correcto=false;
        
        if(actual >= 0 && todos.size() < actual){
            correcto = true;
        }
        return correcto;
    }
    

    
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        switch(tipo){
            case CALLE:
                recibeJugador_calle(actual, todos);
                break;
            case IMPUESTO:
                recibeJugador_impuesto(actual, todos);
                break;
            case JUEZ:
                recibeJugador_juez(actual, todos);
                break;
            case SORPRESA:
                recibeJugador_sorpresa(actual, todos);
                break;
            default:
                informe(actual, todos);
            
        }
        
    }
    
    
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            Jugador jugadorActual = todos.get(actual);
            
            if(!tituloPropiedad.tienePropietario()){
                jugadorActual.puedeComprarCasilla();
            }
            else{
                tituloPropiedad.TramitarAlquiler(jugadorActual);
            }
        }
        
    }
    
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            sorpresa = mazoSorpresas.siguiente();
            informe(actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
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
        String parametros = "Nombre :"+nombre+"\nImporte: "+importe+
                "\nTítulo de propiedad: "+tituloPropiedad+"\nSorpresaa: "
                +sorpresa+"\nMazo sosrpresa: "+mazoSorpresas+ "\n";
        return parametros;
    }
}