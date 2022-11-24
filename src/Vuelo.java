import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
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
     * @param id
     * @param avion
     * @param origen
     * @param terminalOrigen
     * @param salida
     * @param destino
     * @param terminalDestino
     * @param llegada
     * @param precio
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
    //Devuelve el obejeto billete que corresponde con una fila o columna,
    //Devolverá null si está libre o se excede en el límite de fila y columna
    public Billete buscarBillete(int fila, int columna){
        return billetes.buscarBillete(id, fila, columna);
    };
    //Si está desocupado el asiento que indica el billete, lo pone ocupado y devuelve true, si no devuelve false
    public boolean ocuparAsiento(Billete billete){
        return billetes.insertarBillete(billete);
    };
    //A traves del loalizador de billete, se desocupará el asiento
    public boolean desocuparAsiento(String localizador){
        return  billetes.eliminarBillete(localizador);
    };
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero){
        return true;
    };
    // Devuelve una cadena con información completa del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05) en Boeing 747(EC-LKD) por 182,52€, asientos libres: 409
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
    // Devuelve una cadena con información abreviada del vuelo
    //Ejemplo: Vuelo PM0066 de Josep Tarradellas Barcelona-El Prat(BCN) T2 (01/01/2023 08:15:00) a Gran Canaria(LPA) T1 (01/01/2023 11:00:05)
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
    //Devuelve true si el código origen, destino y fecha son los mismos que el vuelo
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha){
        return false;
    };
    // Muestra la matriz  de asientos del vuelo, ejemplo:
    //   A  B  C  D  E  F
    // 1( )(X)( )( )( )( )
    // 2{X}{X}{ }{ }{ }{ }
    // 3{ }{ }{ }{X}{X}{X}
    // 4{ }{ }{ }{ }{ }{ }
    // 5{ }{ }{X}{ }{ }{ }
    // 6[ ][ ][ ][ ][ ][ ]
    // 7[X][X][X][ ][ ][ ]
    // 8[ ][ ][ ][ ][ ][ ]
    // 9[ ][X][ ][ ][ ][X]
    //10[ ][ ][ ][ ][ ][ ]
    public void imprimirMatrizAsientos(){

    };
    //Devuelve true si ha podido escribir en un fichero la lista de pasajeros del vuelo, siguiendo las indicaciones del enunciado
    public boolean generarListaPasajeros(String fichero){
        return true;
    };

    //Métodos estáticos

    //Genera un ID de vuelo. Este consistirá en una cadena de 6 caracteres, de los cuales los dos 
    //primeros serán PM y los 4 siguientes serán números aleatorios. Ejemplo: PM0123
    //NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
    public static String generarID(Random rand){
        String id = "PM";
        int numero;
        for (int i = 0; i < 4; i++){
            numero = rand.nextInt(0,9);
            id += String.valueOf(numero);
        }
        return id;
    };
    //Crea y devuelve un objeto Vuelo de los datos que selecciona el usuario de aeropuertos y aviones y la restricción de que
    //no puede estar repetido el identificador, siguiendo las indicaciones del enunciado
    //La función solicita repetidamente los parametros hasta que sean correctos
    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos){
        return null;
    };
}