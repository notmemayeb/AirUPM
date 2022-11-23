import java.io.FileReader;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaVuelos {
    /**
     * Constructor of the class
     *
     * @param capacidad
     * @param listaVuelos
     */

    private int capacidad;
    private Vuelo[] listaVuelos = new Vuelo[capacidad];
    public ListaVuelos(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return 0;
        };
    public boolean estaLlena(){
        return false;
        };
    //Devuelve el objeto vuelo que está en la posición i del array
    public Vuelo getVuelo(int i){
        return null;
    };
    //Devuelve true si puede insertar el vuelo
    public boolean insertarVuelo (Vuelo vuelo){
        return true;
    };
    //Devuelve el objeto vuelo que tenga el identificador igual al parámetro id
    //Si no lo encuentra, devolverá null
    public Vuelo buscarVuelo (String id){
        return null;
    };
    //Devuelve un nuevo objeto ListaVuelos conteniendo los vuelos que vayan de un aeropuerto a otro en una determinada fecha
    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha){
        return null;
    };
    //Muestra por pantalla los vuelos siguiendo el formato de los ejemplos del enunciado
    public void listarVuelos(){};
    //Permite seleccionar un vuelo existente a partir de su ID, usando el mensaje pasado como argumento para la solicitud
    //y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para salir devolviendo null
    //La función solicita repetidamente hasta que se introduzca un ID correcto
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar){
        return null;
    };
    //Ha de escribir la lista de vuelos en la ruta y nombre del fichero pasado como parámetro.
    //Si existe el fichero, se sobreescribe, si no existe se crea.
    public boolean escribirVuelosCsv(String fichero){
        return true;
    };

    //Métodos estáticos
    //Genera una lista de vuelos a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    //de la lista
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones){
        Scanner sc = null;
        ListaVuelos lista = new ListaVuelos(capacidad);
        try {
            sc = new Scanner(new FileReader(fichero));
            String[] nextLine = sc.nextLine().split(";");
            for (int i = 0; i < capacidad; i++) {
                if (sc.hasNext()) {
                    lista.listaVuelos[i] = new Vuelo(nextLine[0],
                            aviones.buscarAvion(nextLine[1]),
                            aeropuertos.buscarAeropuerto(nextLine[2]),
                            Integer.parseInt(nextLine[3]),
                            Fecha.fromString(nextLine[4]),
                            aeropuertos.buscarAeropuerto(nextLine[5]),
                            Integer.parseInt(nextLine[6]),
                            Fecha.fromString(nextLine[7]),
                            Double.parseDouble(nextLine[8]));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return lista;
    };
}
