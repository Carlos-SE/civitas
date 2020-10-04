/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;
import civitas.Diario;


/**
 *
 * @author carlos
 */
public class Dado {
    
    static final private Dado instance = new Dado();
    
    private Random random;
    
    private int UltimoResultado;
    private boolean debug;
    
    private static int SalidaCarcel = 5;
    private static int numCaras = 6;
    
    
    //Constructor
    private Dado(){
        setDebug(false);
        random = new Random();
        
    }
    
    //Métodos
    
    int tirar(){
        if (!debug){
            UltimoResultado = random.nextInt(numCaras);
        }
        else{
            UltimoResultado = 1;
        }
        
        return UltimoResultado;
    }
    
    
    boolean salgoDeLaCarcel(){
        boolean salir = false;
        
        if (UltimoResultado >= 5){
            salir = true;
        }
        
        return salir;
    }
    
    
    int quienEmpieza(int n){
        int jugador = random.nextInt(n);
        
        return jugador;
    }
    
    void setDebug(boolean d){
        //Añadir registro en el diario
        debug = d;
        
        String evento;
        
        if (d){
            evento = "Dado modo Debug";
        }
        else{
            evento = "Dado modo normal";
        }
        
        Diario.getInstance().ocurreEvento(evento);
    }
    
    int getUltimoResultado(){
        return UltimoResultado;
    }
    
}
