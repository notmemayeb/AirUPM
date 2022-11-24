import java.io.FileOutputStream;
import java.io.IOException;
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



    public AirUPM(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero){

        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajero = maxBilletesPasajero;

    };
    // Lee los datos de los ficheros especificados y los agrega a AirUPM
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes){
        // PREGUNTA PROFE!
        this.listaAeropuertos = ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos, maxAeropuertos);
        this.listaAviones = ListaAviones.leerAvionesCsv(ficheroAviones, maxAviones);
        this.listaVuelos = ListaVuelos.leerVuelosCsv(ficheroVuelos, maxVuelos, listaAeropuertos, listaAviones);
        this.listaPasajeros = ListaPasajeros.leerPasajerosCsv(ficheroPasajeros, maxPasajeros, maxBilletesPasajero);
        ListaBilletes.leerBilletesCsv(ficheroBilletes, listaVuelos, listaPasajeros);



//        Pasajero pasajero;
//        for (int i = 0; i < listaPasajeros.getOcupacion(); i++){
//            pasajero = listaPasajeros.getPasajero(i);
//            for (int j = 0; j < maxBilletesPasajero; j++){
//                this.listaBilletes.insertarBillete(pasajero.getBillete(i));
//            }
//        }

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
        return true;
    };
    public boolean insertarVuelo (Vuelo vuelo){
        return true;
    };
    public boolean maxPasajerosAlcanzado(){
        return true;
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
        return 1;
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

        } else {
            System.out.println("Número de argumentos incorrecto.");
        }
    };
}
