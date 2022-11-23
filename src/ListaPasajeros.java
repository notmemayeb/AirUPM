import java.io.FileReader;
import java.io.PrintWriter;
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
        String sDni;
        char[] dni = new char[9];
        long numdni;
        do {
            System.out.print(mensaje);
            sDni = teclado.nextLine();
            dni = sDni.toCharArray();
            numdni = dni[0]+dni[1]+dni[2]+dni[3]+dni[4]+dni[5]+dni[6]+dni[7];
        }while (!Pasajero.correctoDNI(numdni,dni[8]));
        for (int i = 0; i < capacidad; i++) {
            if (listaPasajeros[i].getDNI() == sDni) {
                return listaPasajeros[i];
            }
        }
        return null;
    };
    // Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
    public boolean escribirPasajerosCsv(String fichero){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 1;i<getOcupacion()-1;i++) {
            pw.printf("%s;%s;%ld;%c;%s\n",listaPasajeros[i].getNombre(),listaPasajeros[i].getApellidos(),listaPasajeros[i].getNumeroDNI(),listaPasajeros[i].getLetraDNI(),listaPasajeros[i].getEmail());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
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