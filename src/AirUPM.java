import java.io.FileOutputStream;
import java.io.IOException;
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
    public ListaVuelos listaVuelos;
    public ListaBilletes listaBilletes;



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
        ListaBilletes.leerBilletesCsv(ficheroBilletes, listaVuelos, listaPasajeros);

        Pasajero pasajero;
        for (int i = 0; i < listaPasajeros.getOcupacion(); i++){
            pasajero = listaPasajeros.getPasajero(i);
            for (int j = 0; j < maxBilletesPasajero; j++){
                if (pasajero.getBillete(j) != null) this.listaBilletes.insertarBillete(pasajero.getBillete(j));
            }
        }

    };
    // Almacena los datos de AirUPM en los ficheros CSV especificados
    public boolean guardarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes){

        // FALTAN BILLETES!

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
    public boolean insertarPasajero (Pasajero pasajero){
        return true;
    };
    // Funcionalidad buscarVuelo especificada en el enunciado del proyecto, que devuelve una lista de vuelos entre dos aeropuertos y
    // con una fecha de salida solicitados por teclado al usuario en el orden y con los textos indicados en los ejemplos de
    // ejecución del enunciado
    public ListaVuelos buscarVuelo(Scanner teclado){
       return null;
    };
    // Funcionalidad comprarBillete especificada en el enunciado del proyecto, que compra un billete para un vuelo especificado,
    // pidiendo por teclado los datos necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del
    // enunciado. Si la lista de pasajeros está vacía, creará un nuevo pasajero, si está llena seleccionará un pasajero, en cualquier
    // otro caso, deberá preguntar al usuario si crear o seleccionar
    public void comprarBillete(Scanner teclado, Random rand, Vuelo vuelo){

    };

    //Métodos estáticos
    
    // Muestra el menú y solicita una opción por teclado
    public static int menu(Scanner teclado){
        String mensaje = "Seleccione opción:";
        int response;
        System.out.println(
                        "1. Alta Vuelo\n" +
                        "2. Alta Pasajero\n" +
                        "3. Buscar Vuelo\n" +
                        "4. Mostrar billetes de Pasajero\n" +
                        "5. Generar lista de Pasajeros\n" +
                        "0. Salir"
        );
        response = Utilidades.leerNumero(teclado, mensaje, 0,5);

        return response;
    };

    public void altaVuelo(Scanner teclado, Random rand) {

        if (!this.maxVuelosAlcanzado()){

            Aeropuerto origen, destino;
            Avion avion;
            Fecha salida, llegada;
            String mensaje, matricula, id;
            int terminalOrigen, terminalDestino;
            double distancia , precio;

            origen = this.listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");

            mensaje = String.format(
                    "Ingrese Terminal Origen (%d - %d):",
                    1,
                    origen.getTerminales()
            );
            terminalOrigen = Utilidades.leerNumero(
                    teclado,
                    mensaje,
                    1,
                    origen.getTerminales()
            );

            destino = this.listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");

            mensaje = String.format(
                    "Ingrese Terminal Destino (%d - %d):",
                    1,
                    destino.getTerminales()
            );
            terminalDestino = Utilidades.leerNumero(
                    teclado,
                    mensaje,
                    1,
                    destino.getTerminales()
            );

            distancia = origen.distancia(destino);

            avion = this.listaAviones.seleccionarAvion(teclado, "Ingrese matrícula de Avión:", distancia);

            do {
                salida = Utilidades.leerFechaHora(teclado, "Fecha de Salida:\n");
                llegada = Utilidades.leerFechaHora(teclado, "Fecha de Legada:\n");
                if (!salida.anterior(llegada)) System.out.println("Llegada debe ser posterior a salida.");
            } while (!salida.anterior(llegada));

            precio = 0;
            boolean isDouble = false;

            while (precio <= 0 || !isDouble){
                try{
                    System.out.print("Ingrese precio de pasaje:");
                    teclado.nextLine();
                    precio = teclado.nextDouble();
                    isDouble = true;
                } catch (Exception exc){
                    isDouble = false;
                }
            }

            do {
                id = Vuelo.generarID(rand);
            } while (this.listaVuelos.buscarVuelo(id) != null);

            Vuelo vuelo = new Vuelo(id, avion, origen, terminalOrigen, salida, destino, terminalDestino, llegada, precio);

            if (this.insertarVuelo(vuelo)) System.out.printf("Vuelo %s creado con éxito.\n", vuelo.getID());

        } else {
            System.out.println("No se pueden dar de alta más vuelos.");
        }
    }
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

            Scanner teclado = new Scanner(System.in);
            Random rand = new Random();
            int opcion;

            do {
                opcion = menu(teclado);
                switch (opcion){
                    case 1:
                        programa.altaVuelo(teclado, rand);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            } while (opcion != 0);

        } else {
            System.out.println("Número de argumentos incorrecto.");
        }
    };
}
