# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

class Dado
  
  @@salidaCarcel = 5
  @@numCaras = 6
  
  def initialize
    
    @debug = false
    @ultimoResultado = 0
    @random = Random.new
    
    
  end
  
  @@instance = Dado.new
  
  def tirar()
    
    resultado = 1
    if(!@debug)
      resultado = @random.rand(@@numCaras+1)
    end
    
    @ultimoResultado = resultado
    return @ultimoResultado
    
  end
  
  
  def salgoDeLaCarcel()
    salgo = false
    
    if(tirar() >= @@salidaCarcel)
      salgo = true
    end
    
    return true
  end
  
  
  def quienEmpieza(n)
    return @random.rand(n-1)
  end
  
  
  def setDebug(d)
    #Añadir instancia del dairio
    
    @debug = d
  end
  
  def getUltimoResultado()
    return @ultimoResultado
  end
  
end
