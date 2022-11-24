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
    // Texto que debe generar: Víctor Rodríguez Fernández, 00123456S, victor.rfernandez@upm.es
    public String toString(){
        return nombre+", "+apellidos+", "+email;
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
    // Elimina el billete de la lista de billetes del pasajero
    public boolean cancelarBillete(String localizador){
        return !billetes.eliminarBillete(localizador);
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
        char[] letraPosicion = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        if ((letra >= 'A' && letra <= 'Z') || (letra >= 'a' && letra <= 'z')){
            return numero < 10000000 && letra == letraPosicion[(int) (numero % 23)];
        }
        return false;
    };
    // Correcto: cristian.ramirez@upm.es, incorrecto: cristian.ramirez@gmail.com, cristian-23@upm.es, cristian.@upm.es
    public static boolean correctoEmail(String email){
        boolean correcto = false;
        if (email.contains("@")){
            String domen = email.split("@")[1];
            String nombre = email.split("@")[0];
            String[] nombreArr = nombre.split("");
            if (domen == "upm.es" || domen == "alumnos.upm.es"){
                if (nombreArr[0] != "." && nombreArr[nombre.length()-1] != "."){
                    for (int i = 0; i < nombre.length()-1; i++){
                        if (((nombreArr[i].charAt(0) >= 'A' && nombreArr[i].charAt(0) >= 'Z') || (nombreArr[i].charAt(0) <= 'a' && nombreArr[i].charAt(0) >= 'z')) || nombreArr[i].charAt(0) != '.'){
                            correcto = true;
                        }
                    }
                }
            }
        }
        return correcto;
    };
}
