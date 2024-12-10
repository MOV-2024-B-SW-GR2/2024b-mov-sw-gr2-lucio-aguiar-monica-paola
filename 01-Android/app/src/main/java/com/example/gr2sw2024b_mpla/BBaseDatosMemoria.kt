package com.example.gr2sw2024b_mpla

class BBaseDatosMemoria {
    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "Adrian", "aba.com"))
            arregloBEntrenador.add(BEntrenador(2, "Vicente", "beb.com"))
            arregloBEntrenador.add(BEntrenador(3, "Carolina", "cxd.com"))
    }
}
}