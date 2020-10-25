# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "diario"
require_relative "titulo_propiedad"
require_relative "dado"


module Civitas
  
  class Jugador
    
    @@precio_libertad = 200.0
    @@paso_por_salida = 1000.0
    @@casas_max = 4
    @@casas_por_hotel = 4
    @@hoteles_max = 4
    
    attr_reader :nombre , :num_casilla_actual , :encarcelado , :casas_por_hotel , :puede_comprar,
        :hoteles_max , :precio_libertad , :paso_por_salida , :propiedades , :saldo , :salvoconducto 
      
    attr_accessor :nombre , :num_casilla_actual , :encarcelado , :casas_por_hotel , :puede_comprar,
        :hoteles_max , :precio_libertad , :paso_por_salida , :propiedades , :saldo , :salvoconducto
    
    def self.new_copia(jugador)
      new(jugador.encarcelado, jugador.nombre, jugador.num_casilla_actual, jugador.puede_comprar, jugador.saldo,
          jugador.salvoconducto, jugador.propiedades)
    end
    
    def self.new_con_nombre(nombre)
      new(false, nombre, 0, false, 7500, nill, Array.new)
    end
  
    def initialize(encarcelado,nombre,num_casilla_actual,puede_comprar,saldo,salvoconducto,propiedades)
      @nombre = nombre
      @encarcelado = encarcelado
      @salvoconducto = salvoconducto
      @puede_comprar = puede_comprar
      @saldo = saldo
      @num_casilla_actual = num_casilla_actual
      @propiedades = propiedades     
    end
    
    def debe_ser_encarcelado()
      deber = false
      
      if (!@encarcelado && !tiene_salvoconducto())
        deber = true
      elsif (!@encarcelado && tiene_salvoconducto())
        perder_salvoconducto()
        Diario.get_instance.ocurre_evento("El jugador #{@nombre} se libra de la carcel.")
      else
        deber = true
      end
      return deber
      
    end
    
    def encarcelar(num_casilla_carcel)
      if (debe_ser_encarcelado)
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
      end
      
      return @encarcelado
    end
    
    def obtener_salvoconducto(s) 
      obtener = false
      if (!@encarcelado)
        @salvoconducto = true
        obtener = true
      end
      return obtener  
    end
    
    def perder_salvoconducto()
      @salvoconducto.usada()
      @salvoconducto = nill
    end
    
    def tiene_salvoconducto()
      return (@salvoconducto != nill)
    end
    
    def puede_comprar_casilla()
      if (@encarcelado)
        @puede_comprar = false
      else
        @puede_comprar = true
      end
      
      return @puede_comprar
      
    end
    
    def paga(cantidad)
      return modificar_saldo(cantidad*(-1))
    end
    
    def paga_impuestos(cantidad)
      pagado = false
      
      if (!@encarcelado)
        pagado = paga(cantidad)        
      end
      
      return pagado
    end
    
    def paga_alquiler(cantidad)
      pagado = false
      
      if (!@encarcelado)
        pagado = paga(cantidad)        
      end
      
      return pagado
    end
    
    def recibe(cantidad)
      recibido = false
      
      if (!@encarcelado)
        recibido = modificar_saldo(cantidad)
      end
      
      return recibido
    end
    
    def modificar_saldo(cantidad)
      @saldo += cantidad
      Diario.get_instance.ocurre_evento("El saldo de #{@nombre}, se modifica a #{@saldo}")
      
      return true
    end
    
    def mover_a_casilla(num_casilla)
      movido = false
      
      if (!@encarcelado)
        @num_casilla_actual = num_casilla
        @puede_comprar = false
        Diario.get_instance.ocurre_evento("El jugador #{@nombre}, se ha movido a la casilla #{@num_casilla_actual}")
        movido = true
      end
      
      return movido
    end
    
    def puedo_gastar(precio)
      puedo = false
      
      if(!@encarcelado)
        puedo = @saldo >= precio
      end
      
      return puedo
    end
    
    def vender(ip)
      vendido = false
      
      if (!@encarcelado)
        if (existe_la_propiedad(id))
          #rellenar y vender
          vendido = true
          Diario.get_instance.ocurre_evento("Se ha vendido")
        end
      end
      return vendido
    end
    
    def tiene_algo_que_gestionar()
        return (@propiedades != [])
    end
    
    def puede_salir_carcel_pagando()
      return (@saldo > @@precio_libertad)  #precio para salir de la carcel
    end
    
    def salir_carcel_pagando()
      salir = false
      if (puede_salir_carcel_pagando())
        paga(@@precio_libertad)
        Diario.get_instance.ocurre_evento("El jugador #{@nombre}, ha salido de la carcel pagando.")
        salir = true        
      end
      
      return salir
    end
    
    def salir_carcel_tirando()
      return (Dado.get_instance.salir_de_la_carcel())
    end
    
    def pasa_por_salida()
      Diario.get_instance.ocurre_evento("El jugador #{@nombre} ha pasado por la casilla de salida.")
      return modificar_saldo(@@paso_por_salida) #abono por pasar por la casilla de salida 1000
    end
    
    def get_saldo()
      return @saldo
    end
    
    def compare_to(otro)
      @saldo <=> otro.get_saldo()
    end
    
    def en_bancarrota()
      bancarrota = false
      
      if (@saldo == 0)
        bancarrota =true
      end
      
      return bancarrota
    end
    
    def get_casas_max()
      return @@casas_max
    end
    
    def get_casas_por_hotel()
      return @casas_por_hotel
    end
    
    def get_hoteles_max()
      return @@hoteles_max
    end
    
    def get_nombre()
      return @nombre
    end
    
    def get_precio_libertad()
      return @@precio_libertad
    end
    
    def get_num_casilla_actual()
      retrun @num_casilla_actual
    end
    
    def get_premio_paso_salida()
      return @@paso_por_salida
    end
    
    def get_propiedades()
      return @propiedades
    end
    
    def get_puede_comprar()
      return @puede_comprar
    end
    
    def is_encarcelado()
      return @encarcelado
    end
    
    def to_string()
      s = "Nombre: #{@nombre}. Soldao: #{@saldo}. Cantidad de propiedades: #{@propiedades.length()}. Posición actual: #{@num_casilla_actual}."
      
      return s
    end
    
  end
end