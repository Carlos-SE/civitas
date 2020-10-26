# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "estados_juego"
require_relative "tipo_casilla"

module Civitas
  class Casilla
    attr_accesor :titulo_propiedad, :importe, :nombre, :carcel
    
    def initialize(nombre, importe, tipo, titulo_propiedad, mazo, sorpresa, carcel)
      init() 
      @nombre = nombre
      @carcel = num_casilla_carcel
      @importe = importe
      @tipo = tipo      
      @titulo_propiedad = titulo_propiedad
      @mazo = mazo
      @sorpresa = sorpresa
      @carcel = carcel
    end
    
    
    def self.new_descanso(nombre)
      new(nombre, 0, TipoCasilla::DESCANSO, nil, nil, nil, nil)
    end    
    
    def self.new_calle(titulo)
      new("", 0, TipoCasilla::CALLE, titulo, nil, nil, nil)
    end    
    
    def self.new_impuesto(importe)
      new("", importe, TipoCasilla::IMPUESTO, nil, nil, nil, nil)
    end
    
    def self.new_sorpresa(mazo)
      new("", 0,TipoCasilla::SORPRESA,nil,mazo, nil, nil)
    end
    
    def self.new_juez(carcel)
      new("", 0, TipoCasilla::CARCEL, nil, nil, nil, carcel)
    end
    
    def jugador_correcto(actual, todos)
      correcto = false
      if(actual < todos.lenght() && actual >= 0)
        correcto = true
      end
      return correcto
    end
    
    
    def informe(actual, todos)
      if(jugador_correcto(actual,todos))
        Diario.instance.ocurre_evento("El jugador se encuentra en la casilla #{@nombre}" )
      end
    end
    
    
    def recibe_jugador_impuesto(actual,todos)
      if(jugador_correcto(actual,todos))
          informe(actual,todos)
          todos[actual].pagaImpuesto(@importe)
        end
    end
    
    def init()
      @carcel = 0
      @importe = 0.0
      @nombre = nil
    end
    
    def recibe_jugador_juez(actual,todos)
        if(jugador_correcto(actual,todos))
          informe(actual,todos)
          todos[actual].encarcelar(@carcel)
        end 
    end
    
    def to_string()
      return "Casilla:  #{@nombre}"
    end
    
  end
end
