require 'singleton'
module Civitas
  class Diario
    include Singleton
    
    @@instance = Dado.new
    
    def self.get_instance
      return @@instance
    end
    
    def initialize
      @eventos = []
    end

    def ocurre_evento(e)
      @eventos << e
    end

    def eventos_pendientes
      return (@eventos.length > 0)
    end

    def leer_evento
      e = @eventos.shift
      return e
    end

  end
end