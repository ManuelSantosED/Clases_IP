
/**
 * Esta es la clase principal que contiene el método main y todas las funciones necesarias para trabajar con direcciones IP.
 * @author Manuel Santos
 * Método principal que se ejecuta al iniciar el programa.
 * Solicita al usuario una dirección IP. Después muestra un menú con varias opciones para obtener información sobre la IP.
 */
fun main(args: Array<String>) {

    var IP = pedirIP()

    do {
        println("")
        println("------------- Menu --------------")
        println("La IP escrita es: $IP")
        println(" 1.Mostrar IP en binario")
        println(" 2.Información de la clase de IP")
        println(" 3.Numero de red y host")
        println(" 4.Numero de mascara")
        println(" 5.Direccion publica o privada")
        println(" 6.Introducir una nueva IP: ")
        println(" 0.Salir")
        print("Escribe una opcion: ")
        var opcion = readln()

        when(opcion){
            "1" -> {
                println("")
                println(convertirABinario(IP))
                println("Pulsa intro para volver al menu")
                readln()
            }
            "2" -> {
                println("")
                println("La clase es: " + comprobarClaseIp(IP))
                println("Pulsa intro para volver al menu")
                readln()
            }
            "3" -> {
                println("")
                var resultados = comprobarRedYHost(IP)
                println("La direccion de red es : ${resultados.get(0)}")
                println("La direccion de host es:  ${resultados.get(1)}")
                // println("nombre:  ${resultados.get(2)}")
                println("Pulsa intro para volver al menu")
                readln()
            }
            "4" -> {
                println("")
                println("La mascara por defecto es: " + mascaraPorDefecto(IP))
                println("Pulsa intro para volver al menu")
                readln()
            }
            "5" -> {
                println("")
                println("Es una IP de tipo: " + privadaOPublica(IP))
                println("Pulsa intro para volver al menu")
                readln()
            }
            "6" -> {
                println("")
                IP = (pedirIP())
            }
        }

    }while (opcion != "0")

}

/**
 * Funcion Pedir IP.
 * Ëste método (pedirIP) solicita al usuario que introduzca una dirección IP.
 * Después se verifica que la IP es correcta y la devuelve, en caso contrario, se vuelve a pedir.
 *
 * @param readln sirve para leer la entrada del usuario.
 * @return Devuelve la dirección IP introducida por el usuario, siempre que sea válida.
 */
fun pedirIP ():String {
    var IP: String
    do {
        print("Escribe una IP: ")
        IP = readln()

    } while (!checkIP(IP)) //Mientras no se cumpla en chekeo de la IP
    return IP
}


/**
 * Funcion para cheaquear la IP.
 *
 * Este método comprueba si la dirección IP proporcionada es válida.
 * Para que una IP sea valida debe tener: 4 partes separadas por puntos y cada parte debe ser un número entre 0 y 255.
 *
 * @param ip La dirección IP a verificar.
 * @return Devuelve verdadero si la dirección IP es válida y falso en caso contrario.
 */
fun checkIP(ip:String?):Boolean {

    if (ip == null)  return false //Comprueba los nulos, por ejemplo una cadena vacia

    val grupo = ip.split(".")  //Comprueba que tiene 4 partes separadas por puntos
    if (grupo.size!=4) return false


    //for para verificar que cada grupo de la dirección IP es un número entero válido entre 0 y 255.
    for (partes in grupo) {   //Comprueba los nulos de las partes de cada grupo, ej. 192.168.1.A

        val numero = partes.toIntOrNull() // //Comprueba que el String son Enteros
        if (numero==null || numero !in (0..255))  //Comprueba si son numeros, o no esta en el rango
            return false
    }

    return true
}



/**
 * Función que convierte una IP a binario.
 *
 * Este método convierte una dirección IP a binario.
 * Cada parte de la IP se convierte a binario y se separa por puntos.
 *
 * @param ip La dirección IP a convertir.
 * @return La dirección IP en formato binario.
 */
fun convertirABinario(ip:String):String{
    // parte la cadena de texto por el caracter ".", creanda una lista de numeros (octetosen)
    var partes = ip.split(".")

    // recorremos las partes, las comvertimos a binario y las concatenamos en una cadena
    var binario = ""
    for ((indice, parte) in partes.withIndex()){
        binario += Integer.toBinaryString(parte.toInt()).padStart(8, '0') // padStart indica que debe tener 8 numeros de longitud y si no lo rellena con 0

        // si la parte no es la ultima, añade el punto de separación
        if (indice != partes.size-1){
            binario += "."
        }
    }
    return binario
}


/**
 * Funcion que comprueba la clase de la IP.
 *
 * Este método determina la clase de la dirección IP proporcionada.
 * La clase de la IP se determina por el primer octeto de la IP.
 *
 * @param ip La dirección IP a comprobar.
 * @return El tipo clase a la que pertenece la dirección IP.
 */
fun comprobarClaseIp(ip:String):String{
    var partes = ip.split(".")
    var primerOcteto = partes[0].toInt()
    var clase = "E"

    when(primerOcteto){
        in 0..127 -> clase = "A"
        in 128..191 -> clase = "B"
        in 192..223 -> clase = "C"
        in 224..240 -> clase = "D"
    }
    return clase
}


/**
 * Funcion para comprobar la red y el host.
 *
 * Este método divide la direccion Ip en dos partes: la de red y la de host de la IP proporcionada.
 * La red y el host se determinan por el primer octeto de la IP.
 *
 * @param ip La dirección IP a comprobar.
 * @return Una array o lista de dos elementos que contiene la dirección de red y host.
 */


fun comprobarRedYHost(ip: String):List<String>{
    var partes = ip.split(".")
    var clase = comprobarClaseIp(ip)
    // variable lista para guardar los resultados
    var lista :List<String> = listOf()

    //When para sacar las posiciones de la lista
    when(clase){
        "A" -> {
            var red = partes[0]
            var host = partes[1] + "." + partes[2] + "." + partes[3]
            lista = lista + red // Aqui se mete el numero de red en la lista que devolvemos
            lista= lista + host // Aqui se mete el numero de host en la lista que devolvemos
        }
        "B" -> {
            var red = partes[0] + "." + partes[1]
            var host = partes[2] + "." + partes[3]
            lista= lista + red
            lista= lista + host
        }
        "C" -> {
            var red = partes[0] + "." + partes[1] + "." + partes[2]
            var host = partes[3]

            lista= lista + red
            lista= lista + host
            //lista=lista+ "Manuel"
        }
        "D" ->  lista= listOf("no disponible - (Multicast)" , "no disponible (Multicast)")
        "E" ->  lista= listOf("no disponible - (Experimental)" , "no disponible (Experimental")
    }
    return lista
}



//
/**
 * Funcion para sacar la mascara por defecto.
 *
 * Este método sirve oara obtener cual es la máscara por defecto de la dirección IP proporcionada.
 * La máscara por defecto se obtiene "por el primer octeto de la IP" (No tiene en cuenta las subredes).
 *
 * @param ip La dirección IP a comprobar.
 * @return La máscara por defecto de la dirección IP.
 */
fun mascaraPorDefecto(ip: String):String{
    var clase = comprobarClaseIp(ip)
    var mascara ="No Disponble"

    when(clase){
        "A" -> mascara = "255.0.0.0"
        "B" -> mascara = "255.255.0.0"
        "C" -> mascara = "255.255.255.0"


    }
    return mascara
}



/**
 * Funcion para sacar si la IP es de tipo publica o privada.
 *
 * indica y muestra en pantalla si la dirección IP del usuario es de tipo privada o pública.
 *
 * @param ip La dirección IP a comprobar.
 * @return "Privada" si la dirección IP es privada, "Publica" si es pública.
 */
fun privadaOPublica(ip: String):String{
    var clase = comprobarClaseIp(ip)
    var partes = ip.split(".")
    var tipo = "Publica"


    when(clase){
        "A" -> {
            if (partes.get(0) == "10"){
                tipo = "Privada"
            }
        }
        "B" ->{
            if (partes.get(0) == "172" && partes.get(1).toInt() in 16..31){
                tipo = "Privada"
            }

        }
        "C" ->{
            if (partes.get(0) == "192" && partes.get(1) == "168"){
                tipo = "Privada"
            }

        }
        "D" -> tipo = "No disponible"
        "E" -> tipo = "No disponible"
    }
    return tipo
}



