import jdk.jshell.spi.SPIResolutionException;

import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class Pasajero {

    /**
     * Constructor of the class
     *
     * @param nombre
     * @param apellidos
     * @param numeroDNI
     * @param letraDNI
     * @param email
     * @param maxBilletes
     */

    private String nombre, apellidos, email;
    private long numeroDNI;
    private char letraDNI;
    private int maxBilletes;

    public Pasajero(String nombre, String apellidos, long numeroDNI, char letraDNI, String email, int maxBilletes){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDNI = numeroDNI;
        this.letraDNI = letraDNI;
        this.email = email;
        this.maxBilletes = maxBilletes;
    };
    public String getNombre(){ return nombre; };
    public String getApellidos(){ return apellidos; };
    public long getNumeroDNI(){ return numeroDNI; };
    public char getLetraDNI(){ return letraDNI; };
    // Ejemplo: 00123456S
    public String getDNI(){ return String.format("%d%c", numeroDNI, letraDNI);};
    public String getEmail(){ return email; };
    // Texto que debe generar: Víctor Rodríguez Fernández, 00123456S, victor.rfernandez@upm.es
    public String toString(){
        return nombre+", "+apellidos+", "+email;
    };
    public int numBilletesComprado(){
        return 1;
    };
    public boolean maxBilletesAlcanzado(){
        return false;
    };
    public Billete getBillete(int i){
        return null;
    };
    public boolean aniadirBillete(Billete billete){
        return true;
    };
    public Billete buscarBillete(String localizador){
        return null;
    };
    // Elimina el billete de la lista de billetes del pasajero
    public boolean cancelarBillete(String localizador){
        return true;
    };
    public void listarBilletes(){

    };
    // Encapsula la funcionalidad seleccionarBillete de ListaBilletes
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        return null;
    };
    //Métodos estáticos
    // 
    // Crea un nuevo pasajero no repetido, pidiendo por teclado los datos necesarios al usuario en el orden 
    // y con los textos indicados en los ejemplos de ejecución del enunciado
    // La función solicita repetidamente los parametros hasta que sean correctos
    public static Pasajero altaPasajero(Scanner teclado, ListaPasajeros pasajeros, int maxBilletes){
        return null;
    };
    // Correcto: 00123456 S, incorrectos: 123456789 A, 12345678 0, 12345678 A
    public static boolean correctoDNI(long numero, char letra){
        return true;
    };
    // Correcto: cristian.ramirez@upm.es, incorrecto: cristian.ramirez@gmail.com, cristian-23@upm.es, cristian.@upm.es
    public static boolean correctoEmail(String email){
        return true;
    };
}
