import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utilidades es una clase que contiene metodos
 * utiles para hacer el código mas ameno y comprensible,
 * no tiene constructor pues no es una clase impricada
 * directamente en el programa
 *
 * @author  Isaac López
 * @author  Fedor Kunin
 * @version     1.0
 */
public class Utilidades {

    public static String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @param minimo limite inferior de los valores aceptados
     * @param maximo limite superior de los valores aceptados
     * @return numero introducido de tipo int
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo){
        int aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextInt();
            teclado.nextLine();
        } while (aux> maximo || aux < minimo);
        return aux;
    };

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @param minimo limite inferior de los valores aceptados
     * @param maximo limite superior de los valores aceptados
     * @return numero introducido de tipo long
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo){
        long aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextLong();
            teclado.nextLine();
        } while (aux > maximo || aux < minimo);
        return aux;
    };

    /**
     * Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @param minimo limite inferior de los valores aceptados
     * @param maximo limite superior de los valores aceptados
     * @return numero introducido de tipo double
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo){
        double aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextDouble();
            teclado.nextLine();
        } while (aux > maximo || aux < minimo);
        return aux;
    };

    /**
     * Solicita un caracter repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @param minimo limite inferior de los valores aceptados
     * @param maximo limite superior de los valores aceptados
     * @return numero introducido de tipo char
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo){
        char aux;
        do {
            System.out.print(mensaje);
            aux = teclado.next().charAt(0);
            teclado.nextLine();
        } while (aux > maximo || aux < minimo);
        return aux;
    };

    /**
     * Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @return fecha de tipo Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje){
        int dia,mes, anio;
        System.out.println(mensaje);
        do {
            dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            anio = leerNumero(teclado, "Ingrese año:", 1900, 3000);
            if (!Fecha.comprobarFecha(dia, mes, anio)) {
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        }while (!Fecha.comprobarFecha(dia, mes, anio));
        return new Fecha(dia, mes, anio);
    };

    /**
     * Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado Scanner
     * @param mensaje usado para pedir informacion
     * @return fecha con hora de tipo Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje){
        int dia,mes, anio, hora, minuto, segundo;
        System.out.println(mensaje);
        do {
            dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            anio = leerNumero(teclado, "Ingrese año:", 1900, 3000);
            hora = leerNumero(teclado, "Ingrese hora:", 0, 23);
            minuto = leerNumero(teclado, "Ingrese minuto:", 0, 59);
            segundo = leerNumero(teclado, "Ingrese segundo:", 0, 59);
            if (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo)) {
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        }while (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo));
        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    };

    /**
     * Cuenta las lineas de un fichero
     * @param ruta del fichero
     * @param nombre del fichero
     * @return numero de lineas del fichero de tipo int
     */
    public static int contarLineasFichero(String ruta, String nombre){
        BufferedReader entrada = null;
        int lineas = 0;
        try {
            entrada = new BufferedReader(new FileReader(ruta));
            while (entrada.readLine() != null) lineas++;
        } catch (FileNotFoundException _exc){

            System.out.printf("Fichero %s no encontrado.\n", nombre);

        } catch (Exception _exc){

            System.out.printf("Error de lectura de fichero %s.\n", nombre);

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.printf("Error de cierre de fichero %s.\n", nombre);
                }
            }
        }
        return lineas;
    }
    public static boolean isAlphaOChar(String name, char spec) {
        String filtro = String.format("[ÁáÉéÚúÓóÍía-zA-Z%c]+", spec);
        return name.matches(filtro);
    }



}
