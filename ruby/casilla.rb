require_relative "jugador"
requiere_relative "tipo_casilla"
require_relative"titulo_propiedad"
requiere_relative "sorpresa"
requiere_relative "mazo_sorpresas"


module Civitas
  class Casilla

    def initialize(nombre, importe, carcel, tipo, sorpresa)
      @nombre=nombre
      @importe = importe
      @carcel = carcel
    end
    
    def self.new_con_nombre(nombre)
      init()
      return new(nombre, -1, -1)
    end
    
    def init()
      @carcel = -1
      @importe = -1
      @nombre = ""
    end
    
    def get_nombre()
      @nombre
    end
    
  end
end

