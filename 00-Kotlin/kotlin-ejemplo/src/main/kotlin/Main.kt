package org.example

import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {

    //inmutables (no se asigna "=")
    val inmutable: String = "Monica";
    // inmutable = "Monica" // Error!
    //Mutables
    var mutable: String = "Monica";
    mutable = "Monica";


    // Duck Typing
    val ejemploVariable = "Monica Lucio"
    ejemploVariable.trim()
    val edadEjemplo: Int = 20
    //Variables primitivas
    val nombreAlumno: String = "Monica Lucio"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases en Java
    val fechaNaciemiento: Date = Date();

    //when (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") ->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No"


    imprimirNombre("Monica")

    calcularSueldo(10.00) // solo parametro requerido
    calcularSueldo(10.00,15.00,20.00) //parametros requeridos y sobreescribir parametros opcionales
    // named parameters
    // calcularSueldo(sueld, tasa, bono especial)
    calcularSueldo(10.00, bonoEspecial = 20.00)// usando el parametro bono Especial en segunda posicion
    // gracias a los parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa = 14.00)
    // usando el parametro bonoEspecial en 1ra posicion
    // usando el parametro sueldo en 2da posicion
    // usando el parametro taa en 3ra posicion
    // gracias a los parametros nombrados

    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

// Clase 8-11-2024
    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);
    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // For each = > Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("valorActual: $valorActual");
        }
    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach { println( "Valor Actual (it): ${it}")}
    // Map -> Muta (Modifica cambio) el arreglo
    // 1. enviamos el nuevo valor a la iteracion
    // 2. nos devuelve un nuevo Arreglo con valores
    // de las iteracionnes
    val respuestaMap:List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    // Filter -> filtara el arreglo
    // 1. DEvolver una expresion true o false
    // 2. nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //expresion o condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println(respuestaFilter)
    println(respuestaFilterDos)
    
    // OP AND
    // AND → &&? (¿MISMO NOMBRE?)
    // AND → ALL (TODOS JUNTOS?)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) // true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) // false

    // REDUCE → Valor acumulado
    // Valor acumulado = 0 (Siempre empiezo en 0 en Kotlin)
    // [1,2,3,4,5] → Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza  + 1 = 0 + 1 = 1 → Iteracion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 → Iteracion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 → Iteracion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 → Iteracion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 → Iteracion4

    val respuestaReduce: Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce (acumulado + valorActual) // → Cambiar o usar la logica de negocio
        }
    println(respuestaReduce);

    // return@reduce acumulado + (itemCarrito.cantidad * itemCarrito.precio)

}


    fun imprimirNombre(nombre:String): Unit{
        fun otraFuncionAdentro(){
            println("Otra funcion adentro")
        }
        println("Nombre: $nombre") //Uso sin llaves
        println("Nombre: ${nombre}") //uso ocn llaves especial
        println("Nombre: ${nombre + nombre}")
        println("Nombre: ${nombre.toString()}")
        println("Nombre: $nombre.toString()")

        otraFuncionAdentro()
    }
//Clase 25 de octubre

fun calcularSueldo(
    sueldo:Double, //Requerido
    tasa: Double = 12.00, //Opcioal (defecto)
    bonoEspecial:Double? = null //Opcional(nullable)
    // Varible? -> "?" Es nullable (osea que puede en algun momento ser nulo)
): Double {
    // Int -> Int ? (nullable)
    // String -> String? (nullable)
    // date -> Date? (nullable)
    if(bonoEspecial == null) {
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

// JAVA
abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int

    constructor(Uno:Int,Dos:Int){
        this.numeroUno = Uno
        this.numeroDos = Dos
        println("Inicializando")
    }
}
// KOTLIN
abstract class Numeros( //Cosntructor Primerio
    // Caso 1) Parametro normal
    //uno:Int, (parametro (sin modificar acceso))

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var uno: int(propiedad "instancia.uno")
    protected val numeroUno:Int,
    protected val numeroDos:Int,
    parametroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init {// bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma ( // Cosntructor primario
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametros
): Numeros( //clase papá, numeros (extendiendo)
    unoParametro,
    dosParametro,
){
// Clase 7-11-2024
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicite: String="Publico implicito"

    init { //bloque constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno // this. Opcional (propiedade, metodos)
        numeroDos // this. Opcional (propiedade, metodos)
        this.soyPublicoExplicito
        soyPublicoExplicito
    }
    constructor( //constructor secundario
        uno: Int?, // Entero nullable
        dos: Int,
    ):this(
        if(uno==null) 0 else uno,
        dos
    ){
        //bloque de codigo de contructor secundario
    }
    //constructor 2
    constructor( //constrcutor secundario
        uno: Int, // Entero nullable
        dos: Int?,
    ):this(
        uno,
        if (dos==null) 0 else dos
    )

    // constructor 3
    constructor( //constrcutor secundario
        uno: Int?, // Entero nullable
        dos: Int?,
    ):this(
        if(uno==null) 0 else uno,
        if (dos==null) 0 else dos
    )

    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{ // Comaparte entre todas las instancias, similar al static
        // funciones, variables
        // NombreClase, metodo, NombreClase, funcion
        // Suma.pi
        val pi = 3.14159
        // suma elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{return num * num }
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorTotalSuma:Int){ //Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)





        }
    }
}


















