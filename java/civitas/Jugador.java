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
public class Jugador implements Comparable<Jugador> {
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PrecioLibertad = 200;
    protected static float PasoPorSalida = 1000;
    private boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    
    //asociaciones a otras clases
    private ArrayList<TituloPropiedad> propiedades;
    private Sorpresa salvoconducto;
    
    
    //constructor que recibe el nombre
    Jugador(String nom){
        
        nombre = nom;
        encarcelado = false;
        numCasillaActual = 0;
        puedeComprar = false;
        saldo = SaldoInicial;
        propiedades = new ArrayList<TituloPropiedad>();
        salvoconducto = null;
        
    }
    
    //constructor copia con acceso protected
    protected Jugador(Jugador nuevo){
        this.nombre = nuevo.nombre;
        this.encarcelado = nuevo.encarcelado;
        this.numCasillaActual = nuevo.numCasillaActual;
        this.puedeComprar = nuevo.puedeComprar;
        this.saldo = nuevo.saldo;
        this.propiedades = nuevo.propiedades;
        this.salvoconducto = nuevo.salvoconducto;
    }
    
    
    
    boolean cancelarHipoteca(int ip){
        boolean resultado = false;
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                if(puedoGastar(cantidad)){
                    resultado = propiedad.cancelarHipoteca(this);
                }
                
                if(resultado){
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ "cancela la ipoteca de la propiedad" + ip);
                }
            }
        }
        return resultado;
    }

    boolean comprar(TituloPropiedad titulo){
        boolean resultado = false;
        
        if(!encarcelado){
            if(getPuedeComprar()){
                float precio = titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    resultado = titulo.comprar(this);
                    if (resultado){
                        propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento("El jugador " +nombre+ "compra la propiedad" + titulo.getNombre());
                    }
                    puedeComprar = false;
                    
                }
            }
        }
        
        return resultado;
    }
    
    boolean construirCasa(int ip){
        boolean resultado = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float precio = propiedad.getPrecioEdificar();
                if (puedoGastar(precio) && puedoEdificarCasa(propiedad)){
                    resultado = propiedad.construirCasa(this);
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ "coosntruye una casa en la propiedad" + ip);
                }
            }
        }
        return resultado;
    }
    
    boolean construirHotel(int ip){
        boolean resultado = false;
        
        if (!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float precio = propiedad.getPrecioEdificar();
                
                if (puedoGastar(precio) && puedoEdificarHotel(propiedad) && propiedad.getNumCasas() == getCasasPorHotel()){
                    resultado = propiedad.construirHotel(this);
                    propiedad.derruirCasas(getCasasPorHotel(), this);
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ "coosntruye un hotel en la propiedad" + ip);
                }
            }
        }
        return resultado;
    }
    
    boolean hipotecar(int ip){
        boolean resultado = false;
        
        if (!encarcelado){
            if (existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                resultado = propiedad.hipotecar(this);
                Diario.getInstance().ocurreEvento("El jugador " +nombre+ "hipoteca la propiedad" + ip);
            }
        }
        
        return resultado;
    }
 
    
    int cantidadCasasHoteles(){
        int total = 0;
        
        //llamamos a la función cantidadCasasHoteles de TituloPropiedad
            for(int i=0; i<propiedades.size(); i++){
            total = propiedades.get(i).cantidadCasasHoteles();
        }
        
        return total;
    }
    
    @Override
    public int compareTo(Jugador otro){
        
        //se encarga de comparar el resultado de dos jugadores
        int resultado = Float.compare(saldo, otro.saldo);
        
        return resultado;
    }
    
    boolean tieneSalvoconducto(){
        boolean tiene = false;
        if(salvoconducto != null){
            tiene = true;
        }
        return tiene;
    }
    private void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean vaACarcel = false;
        
        if(!encarcelado){
            if(tieneSalvoconducto()){
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El "+nombre+" se salva de la carcel y pierde su tarjeta de salvoconducto");
            }
        }else{
            vaACarcel = true;
        }
        return vaACarcel;
    }
    
    boolean enBancarrota(){
        boolean pobre = false;
        if(saldo<0){
            pobre=true;
        }
        return pobre;
    }
    
    boolean moverACasilla(int numCasilla){
        boolean puedeMover = false;
        
        if(!encarcelado){
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("El "+nombre+" se mueve a la casilla "+numCasillaActual);
            puedeMover = true;
        }
        return puedeMover;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        boolean carcel = false;
        
        if(debeSerEncarcelado()){
            carcel = true;
            moverACasilla(numCasillaCarcel);
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" ha sido encarcelado");
        }
        return carcel;
    }
    
    private boolean existeLaPropiedad(int ip){
        boolean existe = false;
        if(!propiedades.isEmpty()){
            if(propiedades.get(ip).getPropietario()==this){
                existe = true;
            }
        }
        return existe;
        
    }
    
    private int getCasasMax(){
        return CasasMax;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    private int getHotelesMax(){
        return HotelesMax;
    }
    
    protected String getNombre(){
        return nombre;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    protected float getSaldo(){
        return saldo;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    boolean modificarSaldo(float cantidad){
        boolean verdadero = true;
        
        saldo = saldo + cantidad;
        Diario.getInstance().ocurreEvento("El jugador "+nombre+" tiene de saldo "+saldo);
        return verdadero;
    }
    
    boolean obtenerSalvoconducto(Sorpresa sorpresa){
        boolean resultado = false;
        
        if(!encarcelado){
            salvoconducto = sorpresa;
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean paga(float cantidad){
         boolean resultado;
         resultado = modificarSaldo(cantidad *- 1);
         
         return resultado;
    }
    
    boolean pagaImpuesto(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = paga(cantidad);
        }
        
        return resultado;
    }
    
    boolean pagaAlquiler(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = paga(cantidad);
        }
        
        return resultado;
    }
    
    boolean pasaPorSalida(){
        
        modificarSaldo(PasoPorSalida);
        
        Diario.getInstance().ocurreEvento("El jugador "+nombre+" pasa por salida e incrementa su saldo ");

        
        return true;
    }
    
    boolean puedeComprarCasilla(){
        boolean puedeComprar = true;
        
        if(encarcelado)
            puedeComprar = false;
        
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        boolean resultado = false;
        
        if(saldo>=PrecioLibertad)
            resultado = true;
        
        return resultado;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean resultado = false;
        if(propiedad.getNumCasas()<CasasMax){
            //podemos contruir más casas debido a que no hemos llegado al tope
            resultado = true;
        }
        return resultado;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean resultado = false;
        if(propiedad.getNumHoteles()<HotelesMax){
            //podemos contruir más hoteles debido a que no hemos llegado al tope
            resultado = true;
        }
        return resultado;
    }
    
    private boolean puedoGastar(float precio){
        boolean resultado = false;
        if(!encarcelado){
            if(saldo>= precio)
                resultado = true;
        }
        return resultado;
    }
    
    boolean recibe(float cantidad){
        boolean resultado = false;
        if(!encarcelado){
            resultado = modificarSaldo(cantidad);
            
        }
        return resultado;
    }
    
    boolean salirCarcelPagando(){
        boolean resultado = false;
        
        if(puedeSalirCarcelPagando()){
            encarcelado = false;
            paga(PrecioLibertad);
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" sale de la carcel ");
            resultado = true;
        }
        return resultado;
    }
    
    boolean salirCarcelTirando(){
        boolean puedo = Dado.getInstance().salgoDeLaCarcel();
        if(puedo){
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador "+nombre+" sale de la carcel tirando");
            puedo = true;
        }
        return puedo;
    }
  
    boolean tieneAlgoQueGestionar(){
        boolean tiene = false;
        
        if(!propiedades.isEmpty())
            tiene = true;
                    
        return tiene;
    }
    
    @Override
    public String toString(){
        String parametros = "Nombre: "+nombre+" está encarcelado: "+encarcelado+". Puede realizar compras: "+puedeComprar+" está en la casilla: "+numCasillaActual;
        return parametros;
    }

    boolean vender(int ip){
        boolean resultado = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
               resultado = propiedades.get(ip).vender(this);  
            }
            if(resultado){
                Diario.getInstance().ocurreEvento("vende la propiedad "+propiedades.get(ip).getNombre());
                propiedades.remove(ip);
            }
        }
        return resultado;
    }     
}
    
 
