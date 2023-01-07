import jdk.jshell.spi.SPIResolutionException;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Pasajero es una clase que contiene los datos
 * necearios para identificar un pasajero y
 * sus billetes
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class Pasajero {

    /**
     * Constructor of the class
     *
     * @param nombre del pasajero
     * @param apellidos del pasajero
     * @param numeroDNI del pasajero
     * @param letraDNI del pasajero
     * @param email del pasajero
     * @param maxBilletes numero de billetes maximos que puede tener un pasajero a la vez
     */

    private String nombre, apellidos, email;
    private long numeroDNI;
    private char letraDNI;
    private int maxBilletes;

    private ListaBilletes billetes;

    public Pasajero(String nombre, String apellidos, long numeroDNI, char letraDNI, String email, int maxBilletes){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDNI = numeroDNI;
        this.letraDNI = letraDNI;
        this.email = email;
        this.maxBilletes = maxBilletes;
        billetes = new ListaBilletes(maxBilletes);
    };
    public String getNombre(){ return nombre; };
    public String getApellidos(){ return apellidos; };
    public long getNumeroDNI(){ return numeroDNI; };
    public char getLetraDNI(){ return letraDNI; };
    // Ejemplo: 00123456S
    public String getDNI(){ return String.format("%08d%c", numeroDNI, letraDNI);};
    public String getEmail(){ return email; };

    /**
     * Genera un string con formato igual al de ejemplo, "Víctor Rodríguez Fernández, 00123456S, victor.rfernandez@upm.es"
     * @return String con formato
     */
    public String toString(){
        return String.format("%s %s, %s, %s",
                this.getNombre(),
                this.getApellidos(),
                this.getDNI(),
                this.getEmail()
        );
    };
    public int numBilletesComprado(){
        return billetes.getOcupacion();
    };
    public boolean maxBilletesAlcanzado(){
        return this.numBilletesComprado() == maxBilletes;
    };
    public Billete getBillete(int i){
        return billetes.getBillete(i);
    };
    public boolean aniadirBillete(Billete billete){
        return billetes.insertarBillete(billete);
    };
    public Billete buscarBillete(String localizador){
        return billetes.buscarBillete(localizador);
    };

    /**
     * Elimina el billete de la lista de billetes del pasajero
     * @param localizador del billete a eliminar
     * @return boolean indicando si se ha podido eliminar o no
     */
    public boolean cancelarBillete(String localizador){
        return billetes.eliminarBillete(localizador);
    };
    public void listarBilletes(){
        billetes.listarBilletes();
    };

    /**
     * Encapsula la funcionalidad seleccionarBillete de ListaBilletes
     * @param teclado Scanner
     * @param mensaje usado para pedir la informacion
     * @return billete seleccionado de tipo Billete
     */
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        return billetes.seleccionarBillete(teclado, mensaje);
    };

    //Métodos estáticos

    /**
     * Crea un nuevo pasajero no repetido, pidiendo por teclado los datos necesarios al usuario en el orden
     * y con los textos indicados en los ejemplos de ejecución del enunciado
     * La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado Scanner
     * @param pasajeros comocidos por el programa
     * @param maxBilletes billetes maximos por pasajero
     * @return pasajero dado de alta de tipo Pasajero
     */
    public static Pasajero altaPasajero(Scanner teclado, ListaPasajeros pasajeros, int maxBilletes){
        Pasajero pasajero = null;
        String nombre, apellidos, email, line, dni;
        char  letraDNI;
        long numeroDNI;

        try {
            do {
                System.out.print("Ingrese nombre:");
                nombre = teclado.next();
                teclado.nextLine();
            } while (!Utilidades.isAlphaOChar(nombre, ' '));

            do {
                System.out.print("Ingrese apellidos:");
                apellidos = teclado.next();
                teclado.nextLine();
            } while (!Utilidades.isAlphaOChar(apellidos, ' '));

            do {
                do {
                    System.out.print("Ingrese número de DNI:");
                    numeroDNI = teclado.nextLong();
                    teclado.nextLine();
                } while (Long.toString(numeroDNI).length() > 8);

                do {
                    System.out.print("Ingrese letra de DNI:");
                    line = teclado.next();
                    letraDNI = line.charAt(0);
                    teclado.nextLine();
                } while (line.length() > 1 || !Character.isLetter(letraDNI));

                dni = String.format("%08d%c", numeroDNI, letraDNI);
                if (!Pasajero.correctoDNI(numeroDNI, letraDNI)) System.out.println("DNI incorrecto.");
                if (pasajeros.buscarPasajeroDNI(dni) != null) System.out.println("DNI ya existe.");

            } while (!Pasajero.correctoDNI(numeroDNI, letraDNI) || pasajeros.buscarPasajeroDNI(dni) != null);

            do {
                System.out.print("Ingrese email:");
                email = teclado.next();
                teclado.nextLine();
                if (!Pasajero.correctoEmail(email)) System.out.println("Email incorrecto.");
                if (pasajeros.buscarPasajeroEmail(email) != null) System.out.println("Email ya existe.");
            } while (!Pasajero.correctoEmail(email) || pasajeros.buscarPasajeroEmail(email) != null);

            return new Pasajero(nombre, apellidos, numeroDNI, letraDNI, email, maxBilletes);
        } catch (InputMismatchException _exc){
            System.out.println("Formato de entrada incorrecto.");
            teclado.nextLine();
            return null;
        }
    };

    /**
     * Verifica el formato de los DNis (Correcto: 00123456 S, incorrectos: 123456789 A, 12345678 0, 12345678 A)
     * @param numero del DNI del pasajero
     * @param letra del DNI del pasajero
     * @return boolean indicando si es correcto o no
     */
    public static boolean correctoDNI(long numero, char letra){
        if (numero == -1) return false;
        char[] letraPosicion = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        int length = String.valueOf(numero).length();

        return letraPosicion[(int) numero % 23] == letra && length < 9;
    };

    /**
     * Correcto: cristian.ramirez@upm.es, incorrecto: cristian.ramirez@gmail.com, cristian-23@upm.es, cristian.@upm.es
     * @param email del pasajero
     * @return boolean indicando si es correcto o no
     */
    public static boolean correctoEmail(String email){

        if (email.contains("@")){
            String domen = email.split("@")[1];
            String nombre = email.split("@")[0];

            if (!domen.equals("upm.es") && !domen.equals("alumnos.upm.es")) return false;
            if (nombre.charAt(0) != '.' && nombre.charAt(nombre.length()-1) != '.'){
                return Utilidades.isAlphaOChar(nombre, '.');
            }
        }
        return false;
    };
}
