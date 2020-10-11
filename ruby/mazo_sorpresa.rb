# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative "sorpresa"
require_relative "diario"

module Civitas
  class Mazo_sorpresa
    attr_reader :sorpresa, :usadas, :ultima_sorpresa
    
    
    def initialize
      
      @sorpresa = Array.new
      @barajada = false
      @usadas = 0
      @debuj = false
      @carta_especial = Array.new
      @ultima_sorpresa = nil
      
    end
    
    def al_mazo(sorpresas)
      unless(@barajada)
        @sorpresa<<sorpresas
      end
    end
    
    def siguiente
      if(!@barajada or @sorpresa.lenght==@usadas)
        
        if(!@debuj)
          #función de ruby para barajar 
          @sorpresa.shuffle!
        end
        
        @usadas = true
        @barajada = 0
        
        @usadas = +1
        
        
        primera_carta = @sorpresa.shift
        
        @sorpresa.push(primera_carta)  
        
        @ultima_sorpresa = primera_carta
        
        return @ultima_sorpresa
      end
    end
    
    def inhabilitar_carta_especial(sorpresas)
      for i in (0..@sorpresa.length() -1 )
        if @sorpresa[i]==sorpresas
          carta=@sorpresa[i]
          @carta_especial<<carta
          @sorpresa.delete_at(i)
          Diario.instance.ocurre_evento('nueva carta incluida en el mazo de especial')
        end
      end
    end
    
    def habilitar_carta_especial(sorpresas)
      for i in (0..@carta_especial.length() -1 )
        if @carta_especial[i] == sorpresas
          carta=@carta_especial[i]
          @carta_especial.delete_at(i)
          @sorpresa<<carta
          Diario.instance.ocurre_evento('nueva carta en la coleccion de sorpresa')
          
        end
      end
    end
    
    
  end
end