/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import civitas.Sorpresa;
import civitas.Diario;
/**
 *
 * @author carlos
 */
public class MazoSorpresas {    
   
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    
    private void init(){
        sorpresas = new ArrayList<Sorpresa>();
        cartasEspeciales = new ArrayList<Sorpresa>();
        barajada = false;
        usadas = 0;
    }
    
    
    public MazoSorpresas(boolean d){
        
        String evento;
        
        if(d){
            evento = "Modo debug: activado";
            Diario.getInstance().ocurreEvento(evento);
        }
                        
        init();
    }
    
    public MazoSorpresas(){
        init();
        debug = false;
    }
    
    public void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    public Sorpresa siguiente(){
        if(!barajada || usadas == sorpresas.size()){
            if(!debug){
                usadas = 0;
                barajada = true;            
            }
            
            usadas++;
            ultimaSorpresa = sorpresas.get(0);
             sorpresas.add(ultimaSorpresa);
             sorpresas.remove(0);                      
        }
        
        return ultimaSorpresa;
    }
    
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        
    }
    
}
