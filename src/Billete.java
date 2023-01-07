import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Billete es una clase la cual contiene los datos
 * de la compra de un tipo pasajero
 *
 * @author  Fedor Kunin
 * @author  Isaac Lopez
 * @version     1.0
 */
public class


Billete {
    enum TIPO { TURISTA, PREFERENTE, PRIMERA }


    /**
     * Constructor of the class
     *
     * @param localizador da identidad al billete (PM1234ABCD)
     * @param vuelo asociado al billete
     * @param pasajero poseedor del billete
     * @param tipo clase de tarifa (turista, preferente, primera)
     * @param fila donde esta el asiento
     * @param columna donde esta el asiento
     * @param precio del billete, según el tipo
     */

    private final char[] LETRA =  {'A', 'B', 'C', 'D', 'E', 'F'};
    private String localizador;
    private Vuelo vuelo;
    private Pasajero pasajero;
    private TIPO tipo;
    private int fila, columma;
    private double precio;

    public Billete(String localizador, Vuelo vuelo, Pasajero pasajero, TIPO tipo, int fila, int columna, double precio){

        this.localizador = localizador;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.tipo = tipo;
        this.fila = fila;
        this.columma = columna;
        this.precio = precio;

    };
    public String getLocalizador(){ return localizador; };
    public Vuelo getVuelo(){ return vuelo; };
    public Pasajero getPasajero(){ return pasajero; };
    public TIPO getTipo(){ return tipo; };
    public int getFila(){ return fila; };
    public int getColumna(){ return columma; };

    /**
     * Devuelve un string con formato, ejemplo: "1A" para el asiento con fila 1 y columna 1, "3D" para el asiento con fila 3 y columna 4
     * @return string con formato
     */
    public String getAsiento(){ return String.format("%d%c", fila, LETRA[columma-1]);};

    /**
     * Devuelve el precio del billete según el tipo de este
     * @return int del precio
     */
    public double getPrecio(){
        if (tipo == TIPO.PRIMERA) return precio*1.5;;
        if (tipo == TIPO.PREFERENTE) return precio*1.25;
        return precio;
    };

    /**
     * Devuelve un string con el siguiente formato:
     * Billete PM1111AAAA para Vuelo PM1111 de MAD T4 (24/12/2022 12:35:00) a BCN T1 (24/12/2022 14:05:30) en asiento 6C (TURISTA) por 100.00€
     * @return String con formato
     */
    public String toString(){
        return String.format("Billete %s para Vuelo %s de %s T%d (%s) a %s T%d (%s) en asiento %s (%s) por %.2f€",
                this.localizador,
                vuelo.getID(),
                vuelo.getOrigen().getCodigo(),
                vuelo.getTerminalOrigen(),
                vuelo.getSalida(),
                vuelo.getDestino().getCodigo(),
                vuelo.getTerminalDestino(),
                vuelo.getLlegada(),
                this.getAsiento(),
                tipo,
                this.getPrecio()
        );
    };

    /**
     * Cancela este billete, eliminandolo de la lista de billetes del vuelo y del pasajero correspondiente
     * @return boolean indicando si se ha podido cancelar el billete o no
     */
    public boolean cancelar(){
        return this.getPasajero().cancelarBillete(localizador) && this.getVuelo().desocuparAsiento(localizador);
    };

    /**
     * Imprime la informacion de este billete en un fichero siguiendo el formato de los ejemplos de ejecución del enunciado
     * @param fichero dirección o nombre del fichero donde escribir
     * @return boolean indicando si se ha podido generar o no
     */
    public boolean generarFactura(String fichero){
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            salida.printf(
                    """
                            --------------------------------------------------
                            --------- Factura del billete %s ---------
                            --------------------------------------------------
                            Vuelo: %s
                            Origen: %s (%s) T%d
                            Destino: %s (%s) T%d
                            Salida: %s
                            Llegada: %s
                            Pasajero: %s %s, %s, %s
                            Tipo de billete: %s
                            Asiento: %s
                            Precio: %.2f€""",
                    this.localizador,
                    vuelo.getID(),
                    vuelo.getOrigen().getNombre(),
                    vuelo.getOrigen().getCodigo(),
                    vuelo.getTerminalOrigen(),
                    vuelo.getDestino().getNombre(),
                    vuelo.getDestino().getCodigo(),
                    vuelo.getTerminalDestino(),
                    vuelo.getSalida(),
                    vuelo.getLlegada(),
                    pasajero.getNombre(),
                    pasajero.getApellidos(),
                    pasajero.getDNI(),
                    pasajero.getEmail(),
                    getTipo().toString(),
                    getAsiento(),
                    getPrecio());
        } catch (Exception _exc){
            System.out.printf("Error de escritura de fichero %s.",fichero);
        } finally {
            if (salida != null){
                try {
                    salida.close();
                    System.out.printf("Facturada de Billete %s generada en %s", vuelo.getID(), fichero);
                } catch (Exception _exc){
                    System.out.printf("Error de cierre de fichero %s.",fichero);
                }
            }
        }
        return true;
    };

    // Métodos estáticos

    /**
     * Genera un localizador de billete. Este consistirá en una cadena de 10 caracteres, de los cuales los seis
     * primeros será el ID del vuelo asociado y los 4 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     * NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand random
     * @param idVuelo identificador del vuelo
     * @return string del localizador
     */
    public static String generarLocalizador(Random rand, String idVuelo){
        String letrasAleatorias = "";
        for (int i = 0; i < 4; i++){
            letrasAleatorias += Utilidades.ALPHA.charAt(rand.nextInt(1,25));
        }
        return idVuelo + letrasAleatorias;
    };

    /**
     * Crea un nuevo billete para un vuelo y pasajero específico, pidiendo por teclado los datos necesarios al usuario en el orden y
     * con los textos indicados en los ejemplos de ejecución del enunciado
     * La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado Scanner
     * @param rand Random
     * @param vuelo vuelo
     * @param pasajero pasajero
     * @return billete tipo Billete dado de alta
     */
    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero){
        Billete billete;
        int fila, columna;
        int filasMax = vuelo.getAvion().getFilas();
        int columnasMax = vuelo.getAvion().getColumnas();
        do {
            vuelo.imprimirMatrizAsientos();
            fila = Utilidades.leerNumero(teclado, String.format("Ingrese fila del asiento (%d-%d):", 1, filasMax), 1, filasMax);
            columna = Utilidades.ALPHA.indexOf(Utilidades.leerLetra(teclado, String.format("Ingrese columna del asiento (%c-%c):", 'A', Utilidades.ALPHA.charAt(columnasMax-1)), 'A', Utilidades.ALPHA.charAt(columnasMax+-1))+1);
            if (vuelo.asientoOcupado(fila, columna)) System.out.println("El asiento está ocupado, por favor, seleccione otro");
        } while (vuelo.asientoOcupado(fila, columna));
        String localizadorNuevo;
        do {
            localizadorNuevo = Billete.generarLocalizador(rand, vuelo.getID());
        } while (vuelo.buscarBillete(localizadorNuevo) != null);
        Billete.TIPO tipo = Billete.TIPO.TURISTA;
        double precio = vuelo.getPrecio();

        if (fila == 1){
            tipo = Billete.TIPO.PRIMERA;
            precio = vuelo.getPrecioPrimera();
        }
        else if (fila <= 5){
            tipo = Billete.TIPO.PREFERENTE;
            precio = vuelo.getPrecioPreferente();
        }

        billete = new Billete(localizadorNuevo, vuelo, pasajero, tipo ,fila, columna, precio);

        return billete;
    };
}