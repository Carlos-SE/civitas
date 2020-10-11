# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module Civitas
  class Dado
    attr_reader :random , :ultimo_resultado
    attr_writer :debug
    
    
    @num_caras=6
    
    def initialize
       @debug = false
       @ultimo_resultado=0
       @random = Random.new
    end
    
    @@instance = Dado.new
    
    def tirar()
      resultado = 1
      if(!@debug)
        resultado = @random.rand(6) + 1
      end
      @ultimo_resultado =resultado
      return @ultimo_resultado
    end
    
    def salir_de_la_carcel()
      
      if(tirar() >= 5)
        return true
      else
        return false
      end
  
    end
    
    def quien_empieza(n)
      return @random.rand(n)
    end
  
    def set_debug(d)
      String modo = "desactivado";
      if(d == true)
        modo = "Activado"
      end
      Diario.instance.ocurre_evento("Modo debug: #{modo}")
      @debug = d
    end
    
    def get_ultimo_resultado()
      return @ultimo_resultado
    end
  end
end