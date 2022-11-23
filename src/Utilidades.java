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
        int dia,mes, anio;
        System.out.print(mensaje);
        do {
            do {
                System.out.print("Ingrese día:");
                dia = teclado.nextInt();
            } while (dia > 31 || dia < 1);
            do {
                System.out.print("Ingrese mes:");
                mes = teclado.nextInt();
            } while (mes > 12 || mes < 1);
            do {
                System.out.print("Ingrese año:");
                anio = teclado.nextInt();
            } while (anio > 3000 || anio < 1900);
            if (!Fecha.comprobarFecha(dia, mes, anio)) {
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        } while (!Fecha.comprobarFecha(dia, mes, anio));
        return new Fecha(dia, mes, anio);
    };
    // Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
    public static Fecha leerFechaHora(Scanner teclado, String mensaje){
        int dia,mes, anio, hora, minuto, segundo;
        System.out.print(mensaje);
        do {
            do {
                System.out.print("Ingrese día:");
                dia = teclado.nextInt();
            } while (dia > 31 || dia < 1);
            do {
                System.out.print("Ingrese mes:");
                mes = teclado.nextInt();
            } while (mes > 12 || mes < 1);
            do {
                System.out.print("Ingrese año:");
                anio = teclado.nextInt();
            } while (anio > 3000 || anio < 1900);
            do {
                System.out.print("Ingrese año:");
                hora = teclado.nextInt();
            } while (hora > 23 || hora < 0);
            do {
                System.out.print("Ingrese año:");
                minuto = teclado.nextInt();
            } while (minuto > 59 || minuto < 0);
            do {
                System.out.print("Ingrese año:");
                segundo = teclado.nextInt();
            } while (segundo > 59 || segundo < 0);
            if (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo)) {
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        } while (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo));
        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    };
}
