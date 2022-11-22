import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaPasajeros {

    /**
     * Constructor of the class
     *
     * @param capacidad
     */

    private int capacidad;

    public ListaPasajeros(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return 0;
        };
    public boolean estaLlena(){
        return false;
        };
    public Pasajero getPasajero(int i){
        return null;
    };
    public boolean insertarPasajero(Pasajero pasajero){
        return true;
    };
    public Pasajero buscarPasajeroDNI(String dni){
        return null;
    };
    public Pasajero buscarPasajeroEmail(String email){
        return null;
    };
    // Permite seleccionar un pasajero existente a partir de su DNI, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un DNI correcto
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje){
        return null;
    };
    // Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
    public boolean escribirPasajerosCsv(String fichero){
        return true;
    };

    //Métodos estáticos
    // Genera una lista de pasajeros a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    // de la lista y el número de billetes máximo por pasajero
    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajero){
        return null;
    };
}