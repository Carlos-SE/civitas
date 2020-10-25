# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

equiere_relative "jugador"

module Civitas

  class TituloPropiedad
    
    attr_accessor :nombre, :alquiler_base, :factor_revalorizacion, :hipoteca_base, :precio_compra, :precio_edificar, :hipotecado, :num_casas, :num_hoteles, :propietario
    
    def initialize(nombre, alquiler_base, factor_revalorizacion, hipoteca_base, precio_compra, precio_edificar)
      @nombre = nombre
      @alquiler_base
      @factor_revalorizacion = factor_revalorizacion
      @hipoteca_base = hipoteca_base
      @precio_compra = precio_compra
      @precio_edificar = precio_edificar
      @hipotecado = false
      @num_casas = 0
      @num_hoteles = 0
      @propietario = nil
      @@factor_interes_hipoteca = 1.1
    end
    
    def to_string()
      cad = "La propiedad #{@nombre}, tiene un precio de alquiler base de #{@alquiler_base}, un factor de revalorizacion #{@factor_revalorizacion}, un precio de hipoteca de #{@hipoteca_base}, un precio de compra de #{@precio_compra}, un precio de edificación de #{@precio_edificar}"
      return cad
    end
    
    def get_precio_alquiler()
      if (@hipotecado || propietario_encarcelado())
        precio = 0
      else
        precio = alquiler_base*(1+(@num_casas*0.5)+(num_hoteles*2.5)) 
      end
            
      return precio
    end
    
    def get_importe_cancelar_hipoteca()
      return @hipoteca_base*@@factor_intereses_hipoteca
    end
    
    def tramitar_alquiler(jugador)
      if (@propietario != nil && @propietario != jugador)
        precio = get_precio_alquiler()
        
        #Implementar métodos
        jugador.paga_alquiler(precio)
        @propietario.recibe(precio)
      end
    end
    
    def propietario_encarcelado()
      #Implementar clase Jugador y métodos
      return @propietario.encarcelado()
    end
    
    def cantidad_casas_hoteles()
      return @num_casas+@num_hoteles
    end
    
    def get_precio_venta()
      return (@precio_compra + @precio_edificar*(@num_casas+@num_hoteles)*@factor_revalorizacion)
    end
    
    def derruir_casas(n, jugador)
      hecho = false
      es_propietario = @propietario == jugador
      if (es_propietario && @num_casas > n)
        @num_casas -= n
        hecho = true        
      end
      
      return hecho
    end
    
    def vender(jugador)
      
      hecho = false
      
      if (@propietario == jugador && !@hipotecado)
        jugador.recibe(get_precio_venta())
        @propietario = nill
        @num_hoteles = 0
        @num_casas = 0
        hecho = true
      end
      
      return hecho
    end
    
    def es_este_el_propietario(jugador)
      return @propietario == jugador
    end
    
    def tiene_propietario()
      return (@propietario != nill)
    end
    
  end
end
