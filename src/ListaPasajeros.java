import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private Pasajero[] listaPasajeros;

    public ListaPasajeros(int capacidad){
        this.capacidad = capacidad;
        this.listaPasajeros = new Pasajero[capacidad];
        for (int i = 0; i < capacidad; i++){
            listaPasajeros[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Pasajero pasajero: listaPasajeros
             ) {
            if (pasajero != null) ocupacion++;
        }
        return ocupacion;
        };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Pasajero getPasajero(int i){
        return listaPasajeros[i];
    };
    public boolean insertarPasajero(Pasajero pasajero){
        if (!this.estaLlena()){
            this.listaPasajeros[this.getOcupacion()] = pasajero;
            return true;
        }
        return false;
    };
    public Pasajero buscarPasajeroDNI(String dni){
        Pasajero pasajeroBusacado = null;

        for (Pasajero pasajero: listaPasajeros
        ) {
            if (pasajero != null){
                if (pasajero.getDNI().equals(dni)){
                    pasajeroBusacado = pasajero;

                }
            }
        }

        return pasajeroBusacado;
    };
    public Pasajero buscarPasajeroEmail(String email){
        Pasajero pasajeroBusacado = null;
        for (Pasajero pasajero: listaPasajeros
             ) {
            if (pasajero != null){
                if (pasajero.getEmail().equals(email)){
                    pasajeroBusacado = pasajero;
                }
            }
        }
        return pasajeroBusacado;
    };
    // Permite seleccionar un pasajero existente a partir de su DNI, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un DNI correcto
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje){
        String stringDNI;
        long numDNI;
        Pasajero pasajeroSeleccionar = null;
        do {
            System.out.print(mensaje);
            teclado.nextLine();
            stringDNI = teclado.nextLine();
            numDNI = Long.parseLong(stringDNI.replaceAll("[^0-9]", ""));
        }while (!Pasajero.correctoDNI(numDNI,stringDNI.charAt(stringDNI.length()-1)));
        pasajeroSeleccionar = this.buscarPasajeroDNI(String.format("%08d%c",numDNI,stringDNI.charAt(stringDNI.length()-1)));
        return pasajeroSeleccionar;
    };
    // Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
    public boolean escribirPasajerosCsv(String fichero){
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            for (int i = 1;i<getOcupacion()-1;i++) {
            salida.printf("%s;%s;%d;%c;%s\n",
                    listaPasajeros[i].getNombre(),
                    listaPasajeros[i].getApellidos(),
                    listaPasajeros[i].getNumeroDNI(),
                    listaPasajeros[i].getLetraDNI(),
                    listaPasajeros[i].getEmail());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (salida != null) {
                salida.close();
            }
        }
        return true;
    };

    //Métodos estáticos
    // Genera una lista de pasajeros a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
    // de la lista y el número de billetes máximo por pasajero
    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajero){
        Scanner entrada = null;
        ListaPasajeros lista = new ListaPasajeros(capacidad);
        int lineas = 0;
        if (Utilidades.contarLineasFichero(fichero, "Pasajeros") != -1){
            lineas = Utilidades.contarLineasFichero(fichero, "Pasajeros");
        }
        try {
            entrada = new Scanner(new FileReader(fichero));
            for (int i = 0; i < Math.min(lineas, capacidad); i++){
                String[] linea = entrada.nextLine().split(";");
                String nombre = linea[0];
                String apellido = linea[1];
                long numeroDNI = Long.parseLong(linea[2]);
                char letraDNI = linea[3].charAt(0);
                String email = linea[4];

                lista.listaPasajeros[i] = new Pasajero(nombre,apellido,numeroDNI, letraDNI, email, maxBilletesPasajero);
            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Pasajeros no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Pasajeros.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Pasajeros.");
                }
            }
        }
        return lista;
    };
}