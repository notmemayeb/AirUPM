import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
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
     * @param localizador
     * @param vuelo
     * @param pasajero
     * @param tipo
     * @param fila
     * @param columna
     * @param precio
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
    // Ejemplos: "1A" para el asiento con fila 1 y columna 1, "3D" para el asiento con fila 3 y columna 4
    public String getAsiento(){
        return String.format("%d%c", fila, LETRA[columma-1]);
    };
    public double getPrecio(){
        if (tipo == TIPO.PRIMERA) return precio*1.5;;
        if (tipo == TIPO.PREFERENTE) return precio*1.25;
        return precio;
    };
    // Texto que debe generar: Billete PM1111AAAA para Vuelo PM1111 de MAD T4 (24/12/2022 12:35:00) a BCN T1 (24/12/2022 14:05:30) en asiento 6C (TURISTA) por 100.00€
    public String toString(){
        return String.format("Billete %s para vuelo %s de %s (%s) a %s (%s) en asiento %s (%s) por %.2f",
                this.localizador,
                vuelo.getID(),
                vuelo.getOrigen().getCodigo(),
                vuelo.getSalida(),
                vuelo.getDestino().getCodigo(),
                vuelo.getLlegada(),
                this.getAsiento(),
                tipo,
                this.getPrecio()
        );
    };
    // Cancela este billete, eliminandolo de la lista de billetes del vuelo y del pasajero correspondiente
    public boolean cancelar(){
        return this.getPasajero().cancelarBillete(localizador) && this.getVuelo().desocuparAsiento(localizador);
    };
    // Imprime la informacion de este billete en un fichero siguiendo el formato de los ejemplos de ejecución del enunciado
    public boolean generarFactura(String fichero){
        return true;
    };

    // Métodos estáticos

    // Genera un localizador de billete. Este consistirá en una cadena de 10 caracteres, de los cuales los seis 
    // primeros será el ID del vuelo asociado y los 4 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
    // NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.  
    public static String generarLocalizador(Random rand, String idVuelo){
        String[] LETRAS = {
                "A","B","C","D","E","F","G","H","I",
                "J","K","J","M","N","O","P","Q","R",
                "S","T","U","V","W","X","Y","Z"
        };
        String letrasAleatorias = "";
        for (int i = 0; i < 5; i++){
            letrasAleatorias += LETRAS[rand.nextInt(1,25)];
        }
        return idVuelo + letrasAleatorias;
    };
    // Crea un nuevo billete para un vuelo y pasajero específico, pidiendo por teclado los datos necesarios al usuario en el orden y 
    // con los textos indicados en los ejemplos de ejecución del enunciado
    // La función solicita repetidamente los parametros hasta que sean correctos
    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero){
        Billete billete;
        int fila, columna;
        int filasMax = vuelo.getAvion().getFilas();
        int columnasMax = vuelo.getAvion().getColumnas();
        String ALPHA = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        do {
            vuelo.imprimirMatrizAsientos();
            fila = Utilidades.leerNumero(teclado, String.format("Ingrese fila del asiento (%d-%d):", 1, filasMax), 1, filasMax);
            columna = ALPHA.indexOf(Utilidades.leerLetra(teclado, String.format("Ingrese columna del asiento (%c-%c):", 'A', ALPHA.charAt(columnasMax-1)), 'A', ALPHA.charAt(columnasMax+-1))+1);
            if (vuelo.asientoOcupado(fila, columna)) System.out.println("El asiento está ocupado, por favor, seleccione otro");
        } while (vuelo.asientoOcupado(fila, columna));

        String localizador = Billete.generarLocalizador(rand, vuelo.getID());
        Billete.TIPO tipo = Billete.TIPO.TURISTA;
        double precio = vuelo.getPrecio();

        if (fila == 1){
            tipo = Billete.TIPO.PRIMERA;
            precio = vuelo.getPrecioPrimera();
        }
        else if (fila <= 4){
            tipo = Billete.TIPO.PREFERENTE;
            precio = vuelo.getPrecioPreferente();
        }

        billete = new Billete(localizador, vuelo, pasajero, tipo ,fila, columna, precio);

        return billete;
    };
}