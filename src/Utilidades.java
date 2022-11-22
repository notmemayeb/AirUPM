import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Isaac López
 * @author  Fedor Kunin
 * @version     1.0
 */
public class Utilidades {
    // Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo){
        int aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextInt();
        } while (aux> maximo || aux < minimo);
        return aux;
    };
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo){
        long aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextLong();
        } while (aux > maximo || aux < minimo);
        return aux;
    };
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo){
        double aux;
        do {
            System.out.print(mensaje);
            aux = teclado.nextDouble();
        } while (aux > maximo || aux < minimo);
        return aux;
    };
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo){
        char aux;
        do {
            System.out.print(mensaje);
            aux = teclado.next().charAt(0);
        } while (aux > maximo || aux < minimo);
        return aux;
    };
    // Solicita una fecha repetidamente hasta que se introduzca una correcta
    public static Fecha leerFecha(Scanner teclado, String mensaje){
        return null;
    };
    // Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
    public static Fecha leerFechaHora(Scanner teclado, String mensaje){
        return null;
    };
}
