import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaBilletes {
    /**
     * Constructor of the class
     *
     * @param capacidad
     */

    private int capacidad;

    public ListaBilletes(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return 0;
        };
    public boolean estaLlena(){
        return false;
        };
    public Billete getBillete(int i){
        return null;
    };
    public boolean insertarBillete (Billete billete){
        return true;
    };
    public Billete buscarBillete (String localizador){
        return null;
    };
    public Billete buscarBillete (String idVuelo, int fila, int columna){
        return null;
    };
    public boolean eliminarBillete (String localizador){
        return true;
    };
    // Muestra por pantalla los billetes de la lista
    public void listarBilletes(){

    };
    // Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un localizador correcto
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        return null;
    };
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero){
        return true;
    };

    // Métodos estáticos
    
    // Lee los billetes del fichero CSV y los añade a las listas de sus respectivos Vuelos y Pasajeros
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros){

    };
}
