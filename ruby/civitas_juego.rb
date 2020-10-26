# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


require_relative "jugador"
require_relative 'gestor_estados'
require_relative 'estados_juego'
require_relative 'dado'
require_relative 'sorpresa'
require_relative 'tablero'
require_relative 'titulo_propiedad'
require_relative 'diario'
require_relative "mazo_sorpresas"
require_relative "tipo_sorpresa"
require_relative "casilla"


module Civitas
  class CivitasJuego
    
    attr_accessor :jugadores, :indice_jugador_actual
    
    
    def initialize(jugadores)
      @jugadores = Array.new
      for nombre in jugadores
        jugador = Jugador.new_con_nombre(nombre)
        @jugadores<<jugador
      end
      
      
      @gestor_estados = Gesor_estados.new
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual = Dado.get_instance.quien_empieza(@jugadores.length)
      @mazo = Mazo_sorpresas.new
      inicializa_mazo_sorpresas
      @tablero = Tablero.new(Casilla.carcel)
      inicializa_tablero
      
    end
    
    def inicializa_tablero(mazo)
      #No se como funciona
    end
    
    def inicializa_mazo_sorpresas(tablero)
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::IRCARCEL,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::IRCASILLA,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PAGARCOBRAR,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PORCASAHOTEL,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::PORJUGADOR,tablero))
      @mazo.alMazo(Sorpresa.new_por_mazo(TipoSorpresa::SALIRCARCEL,tablero))
    end
    
    def contabilizar_pasos_por_salida(jugador_actual)
      contador = @tablero.get_por_salida
      while contador > 0
        jugador_actual.pasa_por_salida
        contador = @tablero.get_por_salida
      end
    end
    
    def pasar_turno
      if (@indice_jugador_actual == (@jugadores.length -1))
        @indice_jugador_actual = 0
      else
        @indice_jugador_actual += 1
      end
    end
    
    def siguiente_paso_completado(operacion)
      @estado = @gestor_estados.siguiente_estado(get_jugador_actual, @estado, operacion)
    end
    
    def get_jugador_actual
      return @jugadores[@indice_jugador_actual]
    end
    
    def construir_casa(ip)
      @jugadores[@indice_jugador_actual].construir_casa(ip)
    end
    
    def construir_hotel(ip)
      @jugadores[@indice_jugador_actual].construir_hotel(ip)
    end
    
    def vender(ip)
      @jugadores[@indice_jugador_actual].vender(ip)
    end
    
    def hipotecar(ip)
      @jugadores[@indice_jugador_actual].hipotecar(id)
    end
    
    def cancelar_hipoteca(id)
      @jugadores[@indice_jugador_actual].cancelar_hipoteca(id)
    end
    
    def salir_carcel_pagando
      @jugadores[@indice_jugador_actual].salir_carcel_pagando
    end
    
    def salir_carcel_tirando
      @jugadores[@indice_jugador_actual].salir_carcel_tirando
    end
    
    def final_del_juego
      bancarrota = false
      for jugador in @jugadores
        if jugador.en_bancarrota
          bancarrota = true
        end
      end
      return bancarrota
    end
    
    def ranking
      @jugadores.sort! { |a,b| a.saldo <=> b.saldo }
    end
    
  end
end
