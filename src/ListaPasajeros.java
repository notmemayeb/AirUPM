import java.io.FileReader;
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
     * @param listaPasajeros
     */

    private int capacidad;
    private Pasajero[] listaPasajeros = new Pasajero[capacidad];

    public ListaPasajeros(int capacidad){
        this.capacidad = capacidad;
    };
    public int getOcupacion(){
        return listaPasajeros.length;
        };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Pasajero getPasajero(int i){
        return listaPasajeros[i];
    };
    public boolean insertarPasajero(Pasajero pasajero){
        if (!this.estaLlena()){
            this.listaPasajeros[listaPasajeros.length-1] = pasajero;
            return true;
        }
        return false;
    };
    public Pasajero buscarPasajeroDNI(String dni){
        Pasajero pasajeroBusacado = null;
        for (Pasajero pasajero: listaPasajeros
        ) {
            if (pasajero.getDNI() == dni){
                pasajeroBusacado = pasajero;
            }
        }
        return pasajeroBusacado;
    };
    public Pasajero buscarPasajeroEmail(String email){
        Pasajero pasajeroBusacado = null;
        for (Pasajero pasajero: listaPasajeros
             ) {
            if (pasajero.getEmail() == email){
                pasajeroBusacado = pasajero;
            }
        }
        return pasajeroBusacado;
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
        Scanner sc = null;
        ListaPasajeros lista = new ListaPasajeros(capacidad);
        try{
            sc = new Scanner(new FileReader(fichero));
            for (int i = 0; i < capacidad; i++) {
                if (sc.hasNext()) {
                    String[] nextLine = sc.nextLine().split(";");
                    lista.listaPasajeros[i] = new Pasajero(nextLine[0], nextLine[1], Integer.parseInt(nextLine[2]), nextLine[3].charAt(0), nextLine[4], maxBilletesPasajero);
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