/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;


import civitas.Casilla;
import civitas.Dado;
import civitas.Diario;
import civitas.EstadosJuego;
import civitas.MazoSorpresas;
import civitas.OperacionesJuego;
import civitas.Sorpresa;
import civitas.Tablero;
import civitas.TipoCasillas;
import civitas.TipoSorpresa;
import java.util.List;

/**
 *
 * @author carlos
 */
public class TestP1 {
    
    public static void main(String [] args){
        
        int resultados[];
        resultados = new int[4];
        for (int i = 0; i < 4; i++){
            resultados[i] = 0;
        }
        
        int resultado;
        
        for (int i = 0; i < 100; i++){
            resultado = Dado.getInstance().quienEmpieza(4);
            
            resultados[resultado]++;
        }
        
        System.out.println("Resultados quienEmpieza");
        System.out.println("Primero: "+resultados[0]);
        System.out.println("Segundo: "+resultados[1]);
        System.out.println("Tercero: "+resultados[2]);
        System.out.println("Cuarto: "+resultados[3]);
        
        System.out.println("Último resultado="+Dado.getInstance().getUltimoResultado());
        
        Dado.getInstance().setDebug(true);
        
        resultados = new int[4];
        
        for (int i = 0; i < 4; i++){
            resultados[i] = 0;
        }
                
        for (int i = 0; i < 100; i++){
            resultado = Dado.getInstance().quienEmpieza(4);
            
            resultados[resultado]++;
        }
        
        System.out.println("Resultados quienEmpieza con debug=true");
        System.out.println("Primero: "+resultados[0]);
        System.out.println("Segundo: "+resultados[1]);
        System.out.println("Tercero: "+resultados[2]);
        System.out.println("Cuarto: "+resultados[3]);
        
        Dado.getInstance().setDebug(false);        
        
        System.out.println("Último resultado="+Dado.getInstance().getUltimoResultado());
        
        String salgo = "NO";
        if (Dado.getInstance().salgoDeLaCarcel() == true){
            salgo = "SÍ";
        }
        System.out.println("Salgo de la carcel: "+salgo);
        
        
        
        MazoSorpresas mazo;
        mazo = new MazoSorpresas();
        Sorpresa s1, s2, s3;
        s1 = new Sorpresa();
        s2 = new Sorpresa();
        
        mazo.alMazo(s1);
        mazo.alMazo(s2);
        
        s3 = mazo.siguiente();
        mazo.inhabilitarCartaEspecial(s2);
        mazo.habilitarCartaEspecial(s2);
        
        Tablero table;
        table = new Tablero(2);
        table.añadeJuez();
        
        Casilla c1, c2, c3;
        c1 = new Casilla("Casilla1");
        c2 = new Casilla("Casilla2");
        c3 = new Casilla("Casilla3");
        
        table.añadeCasilla(c1);
        table.añadeCasilla(c2);
        table.añadeCasilla(c3);
        
        
        int posicion, tirada;
        tirada = Dado.getInstance().tirar();
        posicion = table.nuevaPosicion(0,tirada);
        System.out.println("Tirada = "+tirada);
        System.out.println("Nueva posicion = "+posicion);
        

    }
    
}
