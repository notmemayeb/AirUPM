import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * ListaPasajeros es una clase que contiene una la
 * lista de pasajeros y los metodos necesarios para menejar
 * estos
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaPasajeros {

    /**
     * Constructor of the class
     *
     * @param capacidad el numero de pasajeros maximo en la lista
     * @param listaPasajeros lista de los pasajeros
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

    /**
     * Inserta un pasajero en la lista comprobando antes que esta
     * no este llena
     * @param pasajero que se quiere introducir
     * @return boolean indicando si se ha podido insertar o no
     */
    public boolean insertarPasajero(Pasajero pasajero){
        if (!this.estaLlena()){
            this.listaPasajeros[this.getOcupacion()] = pasajero;
            return true;
        }
        return false;
    };

    /**
     * Busca un pasajero en la lsita usando su DNI
     * @param dni del pasajero a buscar
     * @return pasajero de tipo Pasajero
     */
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

    /**
     * Busca un pasajero en la lsita usando su email
     * @param email del pasajero a buscar
     * @return pasajero de tipo Pasajero
     */
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

    /**
     * Permite seleccionar un pasajero existente a partir de su DNI, usando el mensaje pasado como argumento para la solicitud
     * y siguiendo el orden y los textos mostrados en el enunciado
     * La función solicita repetidamente hasta que se introduzca un DNI correcto
     * @param teclado Scanner
     * @param mensaje usado para pedir la informacion
     * @return pasajero seleccionado tipo Pasajero
     */
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje){
        String stringDNI;
        long numDNI;
        Pasajero pasajeroSeleccionar = null;
        do {
            System.out.print(mensaje);
            stringDNI = teclado.nextLine();
            try {
                numDNI = Long.parseLong(stringDNI.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException _exc){
                numDNI = -1;
            }
        }while (!Pasajero.correctoDNI(numDNI,stringDNI.toUpperCase().charAt(stringDNI.length()-1)));
        pasajeroSeleccionar = this.buscarPasajeroDNI(String.format("%08d%c",numDNI,stringDNI.charAt(stringDNI.length()-1)).toUpperCase());
        return pasajeroSeleccionar;
    };

    /**
     * Genera un fichero CSV con la lista de pasajeros, sobreescribiendolo
     * @param fichero donde se desea escribir
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean escribirPasajerosCsv(String fichero){
        PrintWriter salida = null;
        boolean resultado = false;
        String linea = "";
        try {
            salida = new PrintWriter(fichero);
            for (int i = 1;i<getOcupacion();i++) {
//            salida.printf("%s;%s;%d;%c;%s\n",
//                    listaPasajeros[i].getNombre(),
//                    listaPasajeros[i].getApellidos(),
//                    listaPasajeros[i].getNumeroDNI(),
//                    listaPasajeros[i].getLetraDNI(),
//                    listaPasajeros[i].getEmail());
                linea = listaPasajeros[i].getNombre() + ";" +
                        listaPasajeros[i].getApellidos() + ";" +
                        listaPasajeros[i].getNumeroDNI() + ";" +
                        listaPasajeros[i].getLetraDNI() + ";" +
                        listaPasajeros[i].getEmail();
                salida.println(linea);
            }
            resultado = true;
        } catch (FileNotFoundException _exc){
            System.out.println("Fichero Pasajeros no encontrado.");

        } catch (Exception _exc){
            System.out.println("Error de escritura de fichero Pasajeros.");

        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Pasajeros.");
                }
            }
        }
        return resultado;
    };

    //Métodos estáticos

    /**
     * Genera una lista de pasajeros a partir del fichero CSV, usando los límites especificados como argumentos para la capacidad
     * de la lista y el número de billetes máximo por pasajero
     * @param fichero de donde se desea leer
     * @param capacidad maxima de pasajero a leer
     * @param maxBilletesPasajero numero de billetes maximo por pasajero
     * @return boolean indicando si se ha podido leer o no
     */
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