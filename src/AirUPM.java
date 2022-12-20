import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Fedor Kunin
 * @author  Isaac Lopez
 * @version     1.0
 */
public class AirUPM {
    /**
     * Constructor of the class
     * 
     * @param maxAeropuertos 
     * @param maxAviones
     * @param maxVuelos
     * @param maxPasajeros
     * @param maxBilletesPasajero
     */

    private int maxAeropuertos, maxAviones, maxVuelos, maxPasajeros, maxBilletesPasajero;
    private ListaAeropuertos listaAeropuertos;
    private ListaAviones listaAviones;
    private ListaPasajeros listaPasajeros;
    private ListaVuelos listaVuelos;
    private ListaBilletes listaBilletes;

    private boolean lecturaCorrecta;

    public AirUPM(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero){

        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajero = maxBilletesPasajero;

    };
    // Lee los datos de los ficheros especificados y los agrega a AirUPM
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes){

        this.listaAeropuertos = ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos, maxAeropuertos);
        this.listaAviones = ListaAviones.leerAvionesCsv(ficheroAviones, maxAviones);
        this.listaVuelos = ListaVuelos.leerVuelosCsv(ficheroVuelos, maxVuelos, listaAeropuertos, listaAviones);
        this.listaPasajeros = ListaPasajeros.leerPasajerosCsv(ficheroPasajeros, maxPasajeros, maxBilletesPasajero);
        this.listaBilletes = new ListaBilletes(maxPasajeros*maxBilletesPasajero);

        if (this.listaAeropuertos != null && this.listaAviones != null && this.listaVuelos != null && this.listaPasajeros != null){

            ListaBilletes.leerBilletesCsv(ficheroBilletes, listaVuelos, listaPasajeros);

            Pasajero pasajero;
            for (int i = 0; i < listaPasajeros.getOcupacion(); i++){
                pasajero = listaPasajeros.getPasajero(i);
                for (int j = 0; j < maxBilletesPasajero; j++){
                    if (pasajero.getBillete(j) != null) this.listaBilletes.insertarBillete(pasajero.getBillete(j));
                }
            }

            this.lecturaCorrecta = true;
        } else {
            this.lecturaCorrecta = false;
        }

    };
    // Almacena los datos de AirUPM en los ficheros CSV especificados
    public boolean guardarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes){

        boolean aero, avio, vue, pasa, bille;

        aero = this.listaAeropuertos.escribirAeropuertosCsv(ficheroAeropuertos);
        avio = this.listaAviones.escribirAvionesCsv(ficheroAviones);
        vue = this.listaVuelos.escribirVuelosCsv(ficheroVuelos);
        pasa = this.listaPasajeros.escribirPasajerosCsv(ficheroPasajeros);
        bille = this.listaBilletes.aniadirBilletesCsv(ficheroBilletes);

        return aero && avio && vue && pasa && bille;

    };
    public boolean maxVuelosAlcanzado(){
        return listaVuelos.getOcupacion() == maxVuelos;
    };
    public boolean insertarVuelo (Vuelo vuelo){
        return listaVuelos.insertarVuelo(vuelo);
    };
    public boolean maxPasajerosAlcanzado(){
        return listaPasajeros.getOcupacion() == maxPasajeros;
    };
    public boolean insertarPasajero (Pasajero pasajero){ return listaPasajeros.insertarPasajero(pasajero);
    };
    // Funcionalidad buscarVuelo especificada en el enunciado del proyecto, que devuelve una lista de vuelos entre dos aeropuertos y
    // con una fecha de salida solicitados por teclado al usuario en el orden y con los textos indicados en los ejemplos de
    // ejecución del enunciado
    public ListaVuelos buscarVuelo(Scanner teclado){
        try {
            Aeropuerto origen = this.listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");
            Aeropuerto destino = this.listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
            Fecha salida = Utilidades.leerFecha(teclado, "Fecha de Salida:");
            ListaVuelos lista = this.listaVuelos.buscarVuelos(origen.getCodigo(),destino.getCodigo(), salida);
            return lista;
        } catch (InputMismatchException _exc){
            teclado.nextLine();
            System.out.println("Formato de entrada incorrecto.");
            return null;
        }
    };
    // Funcionalidad comprarBillete especificada en el enunciado del proyecto, que compra un billete para un vuelo especificado,
    // pidiendo por teclado los datos necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del
    // enunciado. Si la lista de pasajeros está vacía, creará un nuevo pasajero, si está llena seleccionará un pasajero, en cualquier
    // otro caso, deberá preguntar al usuario si crear o seleccionar
    public void comprarBillete(Scanner teclado, Random rand, Vuelo vuelo){

        char respuesta;
        do {
            System.out.print("¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?");
            respuesta = teclado.next().charAt(0);
            if (respuesta != 'n' && respuesta != 'e'){
                System.out.println("El valor de entrada debe ser 'n' o 'e'");
            }
        } while (respuesta != 'n' && respuesta != 'e');
        Pasajero pasajeroSeleccionado = null;
        if ((respuesta == 'e' && this.listaPasajeros.getOcupacion() != 0) || this.listaPasajeros.estaLlena()){
            do {
                teclado.nextLine();
                pasajeroSeleccionado = this.listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI de pasajero:");
                if (pasajeroSeleccionado.maxBilletesAlcanzado()) System.out.println("El Pasajero seleccionado no puede adquirir más billetes.");
            } while (pasajeroSeleccionado.maxBilletesAlcanzado());
        } else {
            pasajeroSeleccionado = Pasajero.altaPasajero(teclado, this.listaPasajeros, this.maxBilletesPasajero);
            this.insertarPasajero(pasajeroSeleccionado);
        }
        if (pasajeroSeleccionado != null){

            Billete billete = Billete.altaBillete(teclado, rand, vuelo, pasajeroSeleccionado);
            String localizador = billete.getLocalizador();

            pasajeroSeleccionado.aniadirBillete(billete);
            vuelo.ocuparAsiento(billete);
            this.listaBilletes.insertarBillete(billete);

            System.out.printf("Billete %s comprado con éxito\n", localizador);
        }
    };

    //Métodos estáticos
    
    // Muestra el menú y solicita una opción por teclado
    public static int menu(Scanner teclado){
        String mensaje = "Seleccione opción:";
        int response;
        System.out.println(
                """
                        1. Alta Vuelo
                        2. Alta Pasajero
                        3. Buscar Vuelo
                        4. Mostrar billetes de Pasajero
                        5. Generar lista de Pasajeros
                        0. Salir"""
        );
        response = Utilidades.leerNumero(teclado, mensaje, 0,5);

        return response;
    };

    // Carga los datos de los ficheros CSV pasados por argumento (consola) en AirUPM, llama iterativamente al menú y realiza la
    //  opción especificada hasta que se indique la opción Salir, y finalmente guarda los datos de AirUPM en los mismos ficheros CSV
    public static void main(String[] args){
        if (args.length == 10){

            AirUPM programa = new AirUPM(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4])
            );

            programa.cargarDatos(
                    args[5],
                    args[6],
                    args[7],
                    args[8],
                    args[9]
            );

            if (programa.lecturaCorrecta) {

                Scanner teclado = new Scanner(System.in);
                Random rand = new Random();
                int opcion;

                do {
                    opcion = menu(teclado);
                    switch (opcion){
                        case 1:
                            Vuelo vuelo = null;
                            if (!programa.maxVuelosAlcanzado()){
                                vuelo = Vuelo.altaVuelo(teclado, rand, programa.listaAeropuertos, programa.listaAviones, programa.listaVuelos);
                            } else {
                                System.out.println("No se pueden dar de alta más vuelos.");
                            }
                            if (vuelo != null) {
                                programa.insertarVuelo(vuelo);
                                System.out.printf("Vuelo %s creado con éxito.\n", vuelo.getID());
                            }
                            break;
                        case 2:
                            Pasajero pasajero = null;
                            if (!programa.maxPasajerosAlcanzado()){
                                pasajero = Pasajero.altaPasajero(teclado, programa.listaPasajeros, programa.maxBilletesPasajero);
                            } else {
                                System.out.println("No se pueden dar de alta más pasajeros");
                            }
                            if (pasajero != null){
                                programa.insertarPasajero(pasajero);
                                System.out.printf("Pasajero con DNI %s dado de alta con éxito.\n", pasajero.getDNI());
                            }
                            break;
                        case 3:
                            ListaVuelos lista = programa.buscarVuelo(teclado);
                            if (lista != null) {
                                if (lista.getOcupacion() != 0) {
                                    lista.listarVuelos();
                                    Vuelo vueloSelecionado = programa.listaVuelos.seleccionarVuelo(teclado, "Ingrese ID de vuelo para comprar billete o escriba CANCELAR: ", "CANCELAR");
                                    if (vueloSelecionado != null) {
                                        if (vueloSelecionado.numAsientosLibres() > 0) {
                                            programa.comprarBillete(teclado, rand, vueloSelecionado);
                                        } else {
                                            System.out.printf("El vuelo %s está lleno, no se pueden comprar más billetes\n", vueloSelecionado.getID());
                                        }
                                    }
                                } else {
                                    System.out.println("No se ha encontrado ningún vuelo.");
                                }
                            }
                            break;
                        case 4:
                            // Pregunatar sobre .csv
                            if (programa.listaPasajeros.getOcupacion() != 0){
                                Pasajero pasajeroSeleccionado = programa.listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI de pasajero:");
                                if (pasajeroSeleccionado != null && pasajeroSeleccionado.numBilletesComprado() > 0){
                                    pasajeroSeleccionado.listarBilletes();
                                    Billete billeteSeleccionado = pasajeroSeleccionado.seleccionarBillete(teclado, "Ingrese localizador del billete:");
                                    char respuesta;
                                    do {
                                        System.out.print("¿Generar factura del billete (f), cancelarlo (c) o volver al menú (m)?");
                                        respuesta = teclado.next().charAt(0);
                                        if (respuesta != 'f' && respuesta != 'c' && respuesta != 'm'){
                                            System.out.println("El valor de entrada debe ser 'f', 'c' o 'm'");
                                        }
                                    } while (respuesta != 'f' && respuesta != 'c' && respuesta != 'm');
                                    if (respuesta == 'f'){
                                        System.out.print("Introduzca la ruta donde generar la factura:");
                                        teclado.nextLine();
                                        String ruta = teclado.nextLine();
                                        billeteSeleccionado.generarFactura(ruta);
                                        System.out.println("Facturada de Billete PM1111IDIM generada en " + ruta);
                                    } else if (respuesta == 'c'){
                                        pasajeroSeleccionado.cancelarBillete(billeteSeleccionado.getLocalizador());
                                        billeteSeleccionado.getVuelo().desocuparAsiento(billeteSeleccionado.getLocalizador());
                                        programa.listaBilletes.eliminarBillete(billeteSeleccionado.getLocalizador());
                                        System.out.printf("Billete %s cancelado.\n", billeteSeleccionado.getLocalizador());
                                    }
                                } else {
                                    System.out.println("El pasajero seleccionado no ha adquirido ningún billete");
                                }
                            }
                            break;
                        case 5:
                            String id;
                            do {
                                System.out.print("Ingrese ID del vuelo:");
                                id = teclado.nextLine();
                            } while (programa.listaVuelos.buscarVuelo(id) == null);
                            System.out.print("Introduzca la ruta donde generar la lista de pasajeros:");
                            String ruta = teclado.nextLine();
                            if (programa.listaVuelos.buscarVuelo(id).generarListaPasajeros(ruta))System.out.printf("Lista de pasajeros del vuelo %s generada en %s\n", id, ruta);
                            break;
                    }
                } while (opcion != 0);
                boolean resultado = programa.guardarDatos("ae.csv", "av.csv", "v.csv", "p.csv", "b.csv");
                if (resultado) System.out.println("Datos guardados con éxito.");
            }

        } else {
            System.out.println("Número de argumentos incorrecto.");
        }
    };
}
