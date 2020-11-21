/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author mota
 */
public class TituloPropiedad {
    
    private String nombre;
    private float precioBaseAlquiler;
    private float factorRevalorizacion;
    private float precioBaseHipoteca;
    private float precioCompra;
    private float precioEdificar;
    
    private float factorInteresesHipoteca = 1.1f; //la f del final indica que es un float
    private boolean hipotecado;
    private int numCasas;
    private int numHoteles;
    
    //asociacion de la clase jugador
    Jugador propietario;
    
    //constructor
    TituloPropiedad(String nom, float precioBaseA, float factorR, float precioBaseH , float precioC, float precioE){
        nombre = nom;
        precioBaseAlquiler = precioBaseA;
        factorRevalorizacion = factorR;
        precioBaseHipoteca = precioBaseH;
        precioCompra = precioC;
        precioEdificar = precioE;
        
        //inicializados a 0 y a false 
        hipotecado = false;
        numCasas = 0;
        numHoteles = 0;
        
        //asociacion de la clase jugador a null
        propietario = null;
    }
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        propietario=jugador;
    }
    
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean resultado = false;
        
        if (getHipotecado()){
            if(esEsteElPropietario(jugador)){
                propietario.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                resultado = true;
            }
        }
        
        return resultado;
    }

    
    int cantidadCasasHoteles(){
        return numCasas + numHoteles;
    }
    
    
    boolean comprar(Jugador jugador){
        boolean resultado = false;
        if(!tienePropietario()){
            propietario = jugador;
            propietario.paga(precioCompra);
            resultado = true;
        }
        
        return resultado;
    }
    
    
    boolean construirCasa(Jugador jugador){
        boolean resultado = false;
        
        if (esEsteElPropietario(jugador)){
            propietario.paga(precioEdificar);
            numCasas++;
            resultado = true;
        }
        
        return resultado;
    }
    
    
    
    boolean construirHotel(Jugador jugador){
        boolean resultado = false;
        
        if (esEsteElPropietario(jugador)){
            propietario.paga(precioEdificar);
            numHoteles++;
            resultado = true;
        }
        
        return resultado;
    }
    
    
    boolean derruirCasas(int n, Jugador jugador){
        //booleano para saber si se ha derruido la casa o no
        boolean derruido = false;
        
        if(jugador == propietario && numCasas >= n){
            numCasas -= n;
            derruido = true;
        }
        
        return derruido;
    }
    
    private boolean esEsteElPropietario(Jugador jugador ){
        boolean EsPropietario = false;
        
        if(jugador == propietario){
            EsPropietario = true;
        }
        
        return EsPropietario;
    }
    
    public boolean getHipotecado(){
         return hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        float precioHipoteca = precioBaseAlquiler*(1+(numCasas*0.5f)+(numHoteles*2.5f));
        return precioHipoteca * factorInteresesHipoteca;
    }
    
    private float getImporteHipoteca(){
        return precioBaseAlquiler*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    }
    
    public String getNombre(){
        return nombre;
    }
    
    int getNumCasas(){
        return numCasas;
    }
    
    int getNumHoteles(){
        return numHoteles;
    }
    
    //esta función referencia a la clase jugador
    private boolean propietarioEncarcelado(){
        boolean encarcelado = true;
        
        if(!propietario.encarcelado){
            encarcelado = false;
        }
        
        return encarcelado;
    }
    
    private float getPrecioAlquiler(){
        float alquiler;
        
        if(!propietarioEncarcelado() && !hipotecado){
            alquiler = precioBaseAlquiler*(1+(numCasas*0.5f)+(numHoteles*2.5f));
        }
        else
            alquiler = 0;
        
        return alquiler;
    }
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        return precioCompra+((cantidadCasasHoteles()*precioEdificar)*factorRevalorizacion);
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    
    boolean hipotecar(Jugador jugador){
        boolean resultado = false;
        
        if (!propietario.isEncarcelado() && !hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            resultado = true;
        }
        
        return resultado;
    }
    
    
    boolean tienePropietario(){
        return propietario!=null;
    }
   
    @Override
    public String toString(){
        String parametros = "Nombre :"+nombre+" Alquiler base : "+precioBaseAlquiler+" precio base de la hipoteca "
                +precioBaseHipoteca+" factor de revalorización: "+factorRevalorizacion+" factor de la compra : "
                +precioCompra+" precio para edificar "+precioEdificar;
        return parametros;
    }
    
    
    void TramitarAlquiler(Jugador jugador){
        if(tienePropietario() && esEsteElPropietario(jugador)){
            float alquiler = getPrecioAlquiler();
            jugador.pagaAlquiler(alquiler);
            propietario.recibe(alquiler);
        }
    }
    
    boolean vender(Jugador jugador){
        boolean venta = false;
        
        if(esEsteElPropietario(jugador) && !this.hipotecado){
            jugador.recibe(getPrecioVenta());
            propietario=null;
            numHoteles=0;
            numCasas=0;
            venta=true;
        }
        
        return venta;
    }
}
