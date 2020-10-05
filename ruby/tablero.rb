# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
encoding:utf-8


module Civitas
  
  require_relative "casilla"
  
  class Tablero
    attr_reader :numCasillaCarcel , :porSalida , :casillas , :tieneJuez
    
    def initialize(numCasillaCarcel)
      #atributos de instacia declarados aquí
      
      if numCasillaCarcel >= 1
        @numCasillaCarcel = numCasillaCarcel
      else
        @numCasillaCarcel = 1
      end
      
      @casillas = Array.new 
      @porSalida = 0
      @tieneJuez = true
      
    end
    
    def correcto()
      return(@casillas.size() > @numCasillaCarcel && @tieneJuez);
    end
    
    def correcto(numCasilla) 
      return(correcto() && @casillas.size() > numCasilla);
    end
    
    def get_carcel()
      return @numCasillaCarcel
    end
    
    def get_por_salida()

      if @porSalida > 0 then 
        @porSalida -= 1
        return @porSalida
      else
        return @porSalida
      end
   
    end
    
    
    def aniade_casilla(casilla)
      
      if (@casillas.size == @numCasillaCarcel)
        @casillas << casilla.push("Carcel")
      end
      
      @casillas << casilla
      
      if (@casillas.size == @numCasillaCarcel)
        @casillas << casilla.push("Cárcel")
      end
      
    end
    
    def aniade_juez()
      if(!@tieneJuez)
        aniade_casilla(casilla.push("Juez"))
        @tieneJuez = true
      end
    end
    
    
    def get_casilla(num_casilla)
      
      if(correcto(num_casilla))
        return @casillas[num_casilla]
      else
        return null
      end
    end
    
    def nueva_posicion(actual , tirada)
      unless(correcto())
        nueva = -1  
      else
        nueva = (tirada + actual) 
        
        if(nueva >= @casillas.size())

          nueva %= @casillas.size()
          @porSalida  = @porSalida + 1

        end
      end
      
      return nueva
      
    end
    
    
    def calcular_tirada(origen , destino) 
      
      tirada = destino - origen
      
      if (tirada <0)
        tirada += @casillas.length
      end
      
      return tirada      
    end
    
  end
end

