# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  
  require_relative "casilla"
  
  class Tablero
    
    attr_reader :num_casillas_carcel, :casillas, :por_salida, :tiene_juez
    def initialize(num_casilla_carcel)
        if (num_casilla_carcel >= 1)
          @num_casillas_carcel = num_casilla_carcel
        else
          @num_casillas_carcel = 1
        end
        
        @casillas= Array.new
        @casillas<<@casillas.push("salida")
        @por_salida = 0
        @tiene_juez = false
        
    end
    
    def correct()
      return(@casillas.size()>@num_casillas_carcel && @tiene_juez)
    end
    
    def correcto(num_casilla)
      return(correct() && @casillas.size()>num_casilla)
    end
    
    def get_carcel()
      return @num_casillas_carcel
    end
    
    def get_por_salida()
      if (@por_salida>0)
        @por_salida -= 1
        return @por_salida
      else
        return @por_salida
      end
    end
    
    def aniadir_casilla(casilla)
      if (@casillas.size() == @num_casillas_carcel)
        @casillas << casilla.push("carcel")
      end
      @casillas << casilla 
    
      if(@casillas.size() == @num_casillas_carcel)
        @casillas << casilla.push("carcel")
      end
     
    end
    
    def aniade_juez()
      unless(@tiene_juez)
        aniadir_casilla(@casillas.push("juez"))
        @tiene_juez = true
      end
    end
    
    def get_casilla(num)
      if(correcto(num))
        return @casillas[num]
      else
        return null
      end
    end
    
    def nueva_posicion(actual , tirada)
      unless(correcto()) 
        posicion = -1
      else
        posicion=(tirada+actual)
        if(posicion>= @casillas.size())
          posicion %= @casillas.size()
          @por_salida = @por_salida + 1
        end
      end
     return posicion
    end
    
    def calcular_tirada(origen,destino)
      tirada = destino-origen
      
      if(tirada<0)
        tirada = tirada + @casillas.lenght()
      end
      return tirada
    end
    
      
  end
  
  
end

