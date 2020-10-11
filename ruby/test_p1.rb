# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "tablero"
require_relative "dado"
require_relative "diario"
require_relative "mazo_sorpresa"
require_relative "sorpresa"
require_relative "casilla"
require_relative "tipo_sorpresa"
require_relative "estados_juegos"
require_relative "operaciones_juegos"
require_relative "tipo_casilla"
require_relative "tipo_sorpresa"



module Civitas
   #clase para probar el juego
  class TestP1
    
    # vector de contador de tiradas jugadores
    @jugadores=[0,0,0,0]
    #instancia de la clase Dado
    mi_dado = Dado.new()

      for i in(0..99)
       @empieza = mi_dado.quien_empieza(@jugadores.length) + 1

          if(@empieza == 1)
            @jugadores[0] += 1
          end
          if(@empieza == 2)
            @jugadores[1] += 1
          end
          if(@empieza == 3)
            @jugadores[2] += 1
          end
          if(@empieza == 4)
            @jugadores[3] += 1
          end

      end

      puts "Empieza el jugador"
      puts @empieza

      puts "el jugador 1 ha salido:"
      puts @jugadores[0]
      puts "el jugador 2 ha salido"
      puts @jugadores[1]
      puts "el jugador 3 ha salido:"
      puts @jugadores[2]
      puts "el jugador 4 ha salido"
      puts @jugadores[3]

    #llamar al modo debug pasandole true

      mi_dado.set_debug(true)
      puts "tirar con modo debug activado"
      puts mi_dado.tirar()
      puts mi_dado.tirar()
      puts mi_dado.tirar()

      mi_dado.set_debug(false)
      puts "tirar con modo debug desactivado"
      puts mi_dado.tirar()
      puts mi_dado.tirar()
      puts mi_dado.tirar()


    #mostrar el ultimo resultado+
      puts "ultimo resultado"
      puts mi_dado.get_ultimo_resultado()

    #mostrar si podemos salir de la carcel teniendo en cuenta el resultado de la tirada
  
      if(mi_dado.salir_de_la_carcel())
        puts "puede salir de la carcel"
      else
        puts "no puede salir de la carcel"
      end
      puts "su tirada vale:"
      puts mi_dado.get_ultimo_resultado()
      
    #Mostrar valor de enum
      puts Civitas::Tipo_sorpresa::PAGARCOBRAR
      puts Civitas::Estados_juegos::DESPUES_CARCEL
      puts Civitas::Operaciones_juegos::AVANZAR
      puts Civitas::Tipo_casilla::CALLE

  
    #creamos un objeto de la clase mazo_sorpresa para realizar una sreie de pruebas
      mi_mazo = Mazo_sorpresa.new()
      sorpresa1 = Sorpresa.new()
      sorpresa2 = Sorpresa.new()

    #introducimos al mazo las cartas sorpresa creadas anteriormente
      mi_mazo.al_mazo(sorpresa1)
      mi_mazo.al_mazo(sorpresa2)
  
  

    #habilita e inhabilitar las cartas sorpres
    
      mi_mazo.inhabilitar_carta_especial(sorpresa2)
      mi_mazo.habilitar_carta_especial(sorpresa2)
  
  
    #clase singleton, solo tiene una instancia, vamos a probar sus metodos
      
      puts Diario.instance.eventos_pendientes()
      puts Diario.instance.leer_evento()
  
    #crear un tablero
      mi_tablero = Tablero.new(1)
      mi_casilla1 = Casilla.new('primero')
      mi_casilla2 = Casilla.new('segundo')
      mi_casilla3 = Casilla.new('tercero')
  
      mi_tablero.aniadir_casilla(mi_casilla1)
      mi_tablero.aniadir_casilla(mi_casilla2)
      mi_tablero.aniadir_casilla(mi_casilla3)
  
      mi_tablero.aniade_juez()

      mi_tablero.correcto(3)
  
    #vamos a provocar una situación erronea
      mi_tablero2 = Tablero.new(20)
      puts "error provocado"
      puts mi_tablero2.get_carcel()
      puts mi_tablero2.correcto(3)
  
    #vamos a realizar las tiradas
      puts "tiramos el dado"
      dado_tirar= Dado.new()
  
      tirada=dado_tirar.tirar()
      puts tirada
  
    #calcular la posición en el tabelro tras realizar la tirada
      posicion=mi_tablero.calcular_tirada(0,tirada)
      puts posicion
  
  
  
  #Civitas.main
  end 
  
end

