/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Tablero {
    
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    
    public Tablero(int carcel){
        
        int indice;
        if (carcel >= 1){
            indice = carcel;
        }
        else{
            indice = 1;
        }
        numCasillaCarcel = indice;
        
        casillas = new ArrayList<Casilla>();
        casillas.add(new Casilla("Salida"));
        
        porSalida = 0;
        tieneJuez = false;
        
    }
    
    
    private boolean correcto(){
        return(casillas.size() > numCasillaCarcel && tieneJuez);
    }
    
    private boolean correcto(int numCasilla){
        return(correcto() && casillas.size() > numCasilla);
    }
    
    public int getCarcel(){
        return numCasillaCarcel;
    }
    
    public int getPorSalida(){
        if( porSalida > 0){
            porSalida--;
            return(porSalida+1);
        }
        else{
            return porSalida;
        }
    }
    
    public void añadeCasilla(Casilla casilla){
        
        if( casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Cárcel"));
        }
        
        casillas.add(casilla);
        
        if( casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Cárcel"));
        }
    }
    
    public void añadeJuez(){
        
        if(!tieneJuez){
            tieneJuez = true;
            casillas.add(new Casilla("Juez"));
        }
    }
    
    public Casilla getCasilla(int numCasilla){
        if( correcto(numCasilla)){
            return casillas.get(numCasilla);
        }
        else{
            return null;
        }
    }
    
    public int nuevaPosicion(int actual, int tirada){
        if(!correcto()){
            return(-1);
        }
        else{
            int nueva = actual+tirada;
            
            if(nueva >= casillas.size()){
                porSalida++;
                nueva = nueva%casillas.size();
            }
            
            return nueva;
        }
    }
    
    public int calcularTirada(int origen, int destino){
        int tirada = destino - origen;
        
        if (tirada < 0){
            tirada += casillas.size();
        }
        
        return tirada;
    }
    
    public int size(){
        return casillas.size();
    }
}