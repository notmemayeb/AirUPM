import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Vuelo es una clase que contiene todos los
 * datos de un vuelo
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class Vuelo {
    private boolean[][] asientos;


    /**
     * Constructor of the class
     *
     * @param id del vuelo
     * @param avion que realiza el vuelo
     * @param origen del vuelo
     * @param terminalOrigen terminal del aeropuerto de origen
     * @param salida hora de salida del vuelo
     * @param destino del vuelo
     * @param terminalDestino terminal del aeropuerto de destino
     * @param llegada hora de llegada del vuelo
     * @param precio del billete sin aplicar el tipo de este
     */

    private String id;
    private Avion avion;
    private Aeropuerto origen, destino;
    private int terminalOrigen, terminalDestino;
    private Fecha salida, llegada;
    private double precio;
    private ListaBilletes billetes;

    public Vuelo(String id, Avion avion, Aeropuerto origen, int terminalOrigen, Fecha salida, Aeropuerto destino, int terminalDestino, Fecha llegada, double precio){
        this.id = id;
        this.avion = avion;
        this.origen = origen;
        this.terminalOrigen = terminalOrigen;
        this.salida = salida;
        this.destino = destino;
        this.terminalDestino = terminalDestino;
        this.llegada = llegada;
        this.precio = precio;
        this.asientos = new boolean[avion.getFilas()][avion.getColumnas()];
        for (int i = 0; i < avion.getFilas(); i++){
            for (int j = 0; j < avion.getColumnas(); j++){
                asientos[i][j] = false;
            }
        }
        billetes = new ListaBilletes(avion.getColumnas()*avion.getFilas());

    };
    public String getID(){ return id; };
    public Avion getAvion(){ return avion; };
    public Aeropuerto getOrigen(){ return origen; };
    public int getTerminalOrigen(){ return terminalOrigen; };
    public Fecha getSalida(){ return salida; };
    public Aeropuerto getDestino(){ return destino; };
    public int getTerminalDestino(){ return terminalDestino; };
    public Fecha getLlegada(){ return llegada; };
    public double getPrecio(){ return precio; };
    public double getPrecioPreferente(){return precio*1.25;};
    public double getPrecioPrimera(){
         return precio*1.5;
    };
    public int numAsientosLibres(){
        return (avion.getFilas()*avion.getColumnas() - billetes.getOcupacion());
    };
    public boolean vueloLleno(){
        return numAsientosLibres() == 0;
    };
    public boolean asientoOcupado(int fila, int columna){
        return this.buscarBillete(fila, columna) != null;
    };
    public Billete buscarBillete(String localizador){
        return billetes.buscarBillete(localizador);
    };

    /**
     * Devuelve el obejeto billete que corresponde con una fila o columna,
     * Devolver?? null si est?? libre o se excede en el l??mite de fila y columna
     * @param fila dode esta el asiento del billete
     * @param columna dode esta el asiento del billete
     * @return billete de tipo billete
     */
    public Billete buscarBillete(int fila, int columna){
        return billetes.buscarBillete(id, fila, columna);
    };

    /**
     * Si est?? desocupado el asiento que indica el billete, lo pone ocupado y devuelve true, si no devuelve false
     * @param billete con el asiento a ocupar
     * @return boolean indicando si se ha podido ocupar o no
     */
    public boolean ocuparAsiento(Billete billete){
        this.asientos[billete.getFila()-1][billete.getColumna()-1] = true;
        return this.billetes.insertarBillete(billete);
    };

    /**
     * A traves del loalizador de billete, se desocupar?? el asiento
     * @param localizador del billete con el asiento a eliminar
     * @return boolean indicando si se ha podido descupar  o no
     */
    public boolean desocuparAsiento(String localizador){
        return  billetes.eliminarBillete(localizador);
    };

    /**
     * A??ade los billetes al final de un fichero CSV, sin sobreescribirlo
     * @param fichero donde se quiere escribir
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean aniadirBilletesCsv(String fichero){
        billetes.aniadirBilletesCsv(fichero);
        return true;
    };

    /**
     * Devuelve una cadena con informaci??n completa del vuelo
     * Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05) en Boeing 747(EC-LKD) por 182,52???, asientos libres: 409
     * @return String con formato
     */
    public String toString(){
        return String.format("Vuelo %s de %s(%s) T%d (%s) a %s(%s) T%d (%s) en %s %s(%s) por %.2f, asinetos libres: %d",
                this.getID(),
                this.getOrigen().getNombre(),
                this.getOrigen().getCodigo(),
                this.getTerminalOrigen(),
                this.getSalida().toString(),
                this.getDestino().getNombre(),
                this.getDestino().getCodigo(),
                this.getTerminalDestino(),
                this.getLlegada().toString(),
                this.getAvion().getMarca(),
                this.getAvion().getModelo(),
                this.getAvion().getMatricula(),
                this.precio,
                this.numAsientosLibres()
                );
    };

    /**
     * Devuelve una cadena con informaci??n abreviada del vuelo
     * Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05)
     * @return String con formato
     */
    public String toStringSimple(){
        return String.format("Vuelo %s de %s(%s) T%d (%s) a %s(%s) T%d (%s)",
                this.getID(),
                this.getOrigen().getNombre(),
                this.getOrigen().getCodigo(),
                this.getTerminalOrigen(),
                this.getSalida().toString(),
                this.getDestino().getNombre(),
                this.getDestino().getCodigo(),
                this.getTerminalDestino(),
                this.getLlegada().toString()
        );
    };

    /**
     * Devuelve true si el c??digo origen, destino y fecha son los mismos que el vuelo
     * @param codigoOrigen del vuelo
     * @param codigoDestino del vuelo
     * @param fecha de salida del vuelo
     * @return devuelve true si coinciden
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha){
        return (this.origen.getCodigo().equals(codigoOrigen) && this.destino.getCodigo().equals(codigoDestino) && this.salida.coincide(fecha));
    };

    /**
     *  Muestra la matriz  de asientos del vuelo, ejemplo:
     *        A  B  C  D  E  F
     *      1( )(X)( )( )( )( )
     *      2{X}{X}{ }{ }{ }{ }
     *      3{ }{ }{ }{X}{X}{X}
     *      4{ }{ }{ }{ }{ }{ }
     *      5{ }{ }{X}{ }{ }{ }
     *      6[ ][ ][ ][ ][ ][ ]
     *      7[X][X][X][ ][ ][ ]
     *      8[ ][ ][ ][ ][ ][ ]
     *      9[ ][X][ ][ ][ ][X]
     *     10[ ][ ][ ][ ][ ][ ]
     */

    public void imprimirMatrizAsientos(){
        String spacer1, spacer2;
        String isres = " ";
        for (int i = 0; i < avion.getColumnas(); i++) {
            if (i == 0) System.out.printf("   %c", Utilidades.ALPHA.charAt(i));
            if (i > 0) System.out.printf("  %c", Utilidades.ALPHA.charAt(i));

        }
        System.out.println();
        for (int i = 0; i < avion.getFilas(); i++) {
            if (i<9) {
                System.out.print(" ");
            }
            System.out.print(i+1);
            switch (i) {
                case 0 -> {
                    spacer1 = "(";
                    spacer2 = ")";
                }
                case 1, 2, 3, 4 -> {
                    spacer1 = "{";
                    spacer2 = "}";
                }
                default -> {
                    spacer1 = "[";
                    spacer2 = "]";
                }
            }
            for (int r = 0; r < avion.getColumnas(); r++) {
                isres = " ";
                if (asientos[i][r]) {
                    isres = "X";
                }
                System.out.printf("%s%s%s",spacer1,isres,spacer2);
            }
            System.out.println();
        }
        System.out.println("Tipo de asiento: '[ ]' = TURISTA, '{ }' = PREFERENTE, '( )' = PRIMERA");
    };

    /**
     * Devuelve true si ha podido escribir en un fichero la lista de pasajeros del vuelo, siguiendo las indicaciones del enunciado
     * @param fichero donde se desea escribir la lista
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean generarListaPasajeros(String fichero){
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            salida.printf(
                    """
                            --------------------------------------------------
                            ------- Lista de pasajeros en vuelo PM1990 -------
                            --------------------------------------------------
                            Asiento  Tipo        Pasajero
                            """);

            char columna = 'A';
            int fila = 1;

            for (int i = 0; i < avion.getFilas(); i++) {
                for (int r = 0; r < avion.getColumnas(); r++) {
                    if (columna == Utilidades.ALPHA.charAt(avion.getColumnas())) {
                        columna = 'A';
                        fila++;
                    }
                    salida.printf("%d%-8c",fila, columna);
                    columna = (char) (columna + 1);
                    if (buscarBillete(i+1, r+1) != null){
                        salida.printf("%-12s%s, %s, %s\r\n",
                                buscarBillete(i+1, r+1).getTipo(),
                                buscarBillete(i+1, r+1).getPasajero().getNombre(),
                                buscarBillete(i+1, r+1).getPasajero().getDNI(),
                                buscarBillete(i+1, r+1).getPasajero().getEmail());
                    } else {
                        salida.println("");
                    }

                }
            }
        } catch (Exception _exc){
            System.out.printf("Error de escritura de fichero %s.\n", fichero);
            System.out.println(_exc.getMessage());
            return false;
        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Vuelo.");
                }
            }
        }
        return true;
    };

    //M??todos est??ticos

    /**
     * Genera un ID de vuelo. Este consistir?? en una cadena de 6 caracteres, de los cuales los dos
     * primeros ser??n PM y los 4 siguientes ser??n n??meros aleatorios. Ejemplo: PM0123
     * NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand Random
     * @return String de la ID generada
     */
    public static String generarID(Random rand){
        String id = "PM";
        int numero;
        for (int i = 0; i < 4; i++){
            numero = rand.nextInt(0,9);
            id += String.valueOf(numero);
        }
        return id;
    };

    /**
     * Crea y devuelve un objeto Vuelo de los datos que selecciona el usuario de aeropuertos y aviones y la restricci??n de que
     * no puede estar repetido el identificador, siguiendo las indicaciones del enunciado
     * La funci??n solicita repetidamente los parametros hasta que sean correctos
     * @param teclado Scanner
     * @param rand Random
     * @param aeropuertos conocidos por el programa
     * @param aviones conocidos por el programa
     * @param vuelos conocidos por el programa
     * @return vuelo dado de alta de tipo Vuelo
     */
    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos) {

        Aeropuerto origen, destino;
        Avion avion;
        Fecha salida, llegada;
        String mensaje, id;
        int terminalOrigen, terminalDestino;
        double distancia, precio;

        try {
            do {
                origen = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese c??digo de Aeropuerto Origen:");
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
                destino = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese c??digo de Aeropuerto Destino:");
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
                if (origen.getCodigo().equals(destino.getCodigo()))
                    System.out.println("Aeroperto origen y Aeroperto destino deben ser distintos");
            } while (origen.getCodigo().equals(destino.getCodigo()));
            distancia = origen.distancia(destino);
            avion = aviones.seleccionarAvion(teclado, "Ingrese matr??cula de Avi??n:", distancia);
            do {
                salida = Utilidades.leerFechaHora(teclado, "Fecha de Salida:");
                llegada = Utilidades.leerFechaHora(teclado, "Fecha de Legada:");
                if (!salida.anterior(llegada)) System.out.println("Llegada debe ser posterior a salida.");
            } while (!salida.anterior(llegada));
            precio = 0;
            while (precio <= 0) {
                System.out.print("Ingrese precio de pasaje:");
                precio = teclado.nextDouble();
            }
            do {
                id = Vuelo.generarID(rand);
            } while (vuelos.buscarVuelo(id) != null);
            return new Vuelo(id, avion, origen, terminalOrigen, salida, destino, terminalDestino, llegada, precio);
        } catch (InputMismatchException _exc){
            teclado.nextLine();
            System.out.println("Formato de entrada incorrecto.");
            return null;
        }
    };
}