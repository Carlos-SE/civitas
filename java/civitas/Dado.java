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
    
    static private Dado instance = new Dado();
    
    private Random random;
    
    private int ultimoResultado;
    private boolean debug;
    
    private static int SalidaCarcel = 5;
    private static int numCaras = 6;
    
    
    //Constructor
    private Dado(){
        debug = false;
        random = new Random();
        ultimoResultado = 0;
        
    }
    
    //Métodos
    
    public int tirar(){
        if (!debug){
            ultimoResultado = random.nextInt(numCaras) + 1;
        }
        else{
            ultimoResultado = 1;
        }
        
        return ultimoResultado;
    }
    
    
    public boolean salgoDeLaCarcel(){
        boolean salir = false;
        int resultado = tirar();
        if (resultado >= 5){
            salir = true;
        }
        
        return salir;
    }
    
    
    public int quienEmpieza(int n){
        int jugador = random.nextInt(n);
        
        return jugador;
    }
    
    public void setDebug(boolean d){
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
    
    public int getUltimoResultado(){
        return ultimoResultado;
    }
    
    public static Dado getInstance(){
		return instance;
	}
    
}