require_relative "tipo_sorpresa"
require_relative "tablero"
require_relative "mazo_sorpresas"
require_relative "casilla"
require_relative "diario"
require_relative "jugador"


module Civitas
  class Sorpresa
    
    def initialize(tipo, tablero, valor, mazo, texto)
      init()
      @tipo_casilla = tipo
      @tablero = tablero
      @valor = valor
      @mazo = mazo
      @texto = texto
    end
    
    #CONSTRUCTORES
    
    def self.new_con_tablero(tipo, tablero)
      return new(tipo, tablero, -1, nill, "")
    end
    
    def self.new_con_tablero_valor(tipo, tablero, valor)
      return new(tipo, tablero, valor, nill, "")
    end
    
    def self.new_con_valor(tipo, valor)
      return new(tipo, nill, valor, nill, "")
    end
    
    def self.new_con_mazo(tipo, mazo)
      return new(tipo, nill, -1, mazo, "")
    end
    
    def init()
      @valor = -1
      @mazo = nill
      @tablero = nill
    end
    
    def jugador_correcto(actual, todos)
      correcto = false
      
      if(actual < todos.lenght() && actual >= 0)
        correcto = true
      end
      
      return correcto
    end
    
    def informe(actual, todos)
      evento = "El jugador #{todos[actual].nombre}, va a recibir una sorpresa"
      Diario.get_instance.ocurre_evento(evento)
    end
    
    def aplicar_a_jugador(actual, todos)
      case @tipo
      when TipoSorpresa::IRCARCEL
        aplicar_a_jugador_ir_carcel(actual, todos)
      when TipoSorpresa::IRCASILLA
        aplicar_a_jugador_ir_casilla(actual, todos)
      when TipoSorpresa::PAGARCOBRAR
        aplicar_a_jugador_pagar_cobrar(actual, todos)
      when TipoSorpresa::PORCASAHOTEL
        aplicar_a_jugador_por_casa_hotel(actual, todos)
      when TipoSorpresa::PORJUGADOR
        aplicar_a_jugador_por_jugador(actual, todos)
      when TipoSorpresa::SALIRCARCEL
        aplicar_a_jugador_salir_carcel(actual, todos)
      end
    end
    
    def aplicar_a_jugador_ir_carcel(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual]..encarcelar(@tablero.carcel)
      end
    end
    
    def aplicar_a_jugador_ir_casilla(actual,todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        casilla_actual = todos[actual].num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla_actual, @valor)
        nueva_posicion = @tablero.nueva_posicion(casilla_actual, tirada)
        
        todos[actual].mover_a_casilla(nueva_posicion)
        @tablero.casillas[nueva_posicion].recibe_jugador(todos[actual])
      end
    end
    
    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      if (jugador_correcto(actual,todos))
        informe(actual, todos)
        todos[actual].modificar_saldo(@valor)
      end
    end
    
    def aplicar_a_jugador_por_casa_hotel(actual, todos)
      if (jugador_correcto(actual,todos))
        informe(actual, todos)
        valor = @valor * (todos[actual].num_casas+todos[actual].num_hoteles)
        todos[actual].modificar_saldo(valor)
      end
    end
    
    def aplicar_jugador_por_jugador(actual, todos)
      if(jugador_correcto(actual, todos))
        sorpresa_cobrar = Sorpresa.new_con_valor(TipoSorpresa::PAGARCOBRAR, (todos.length - 1)*@valor)
        sorpresa_cobrar.aplicar_a_jugador_pagar_cobrar(actual, todos)
        jugadores = todos -todos[actual]
        sorpresa_pagar = Sorepresa.new_con_valor(TipoSorpresa::PAGARCOBRAR, (@valor*(-1)))
        
        for i in jugadores
          sorpresa_pagar.aplicar_a_jugador_pagar_cobrar(i,todos)
        end
      end
    end
    
    def aplicar_a_jugador_salid_carcel(actual, todos)
      if (jugador_correcto(actual, todos))
        informe(actual, todos)
        
        salvoconducto = false        
        for jugador in todos
          if (jugador.tiene_salvoconducto())
            salvoconducto = true
          end
        end
        
        if (!salvoconducto)
          todos[actual].obtener_salvoconducto()
        end
      end
    end
    
    def salir_del_mazo()
      
      salir = Sorpresa.new_con_tablero(TipoSorpresa::SALIRCARCEL, @tablero)
      
      if (@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(salir)
      end
    end
    
    def usada()
      salir = Sorpresa.new_con_tablero(TipoSorpresa::SALIRCARCEL, @tablero)
      
      if (@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.habilitar_carta_especial(salir)
      end
    end
    
    def to_string()
      st = "Tipo de sorpresa: #{@tipo}."
    end
    
  end
end