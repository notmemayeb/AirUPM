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
public class ListaBilletes {
    /**
     * Constructor of the class
     *
     * @param capacidad
     */

    private int capacidad;
    private Billete[] lista;

    public ListaBilletes(int capacidad){
        this.capacidad = capacidad;
        this.lista =new Billete[capacidad];
        for (int i = 0; i < capacidad; i++){
            lista[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Billete billete: lista
        ) {
            if (billete != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Billete getBillete(int i){
        return lista[i];
    };
    public boolean insertarBillete (Billete billete){
        if (!this.estaLlena()){
            lista[this.getOcupacion()] = billete;
            return true;
        }
        return false;
    };
    public Billete buscarBillete (String localizador){
        Billete billeteBuscado = null;
        for (Billete billete: lista) {
            if (billete.getLocalizador().equals(localizador)) billeteBuscado = billete;
        }
        return billeteBuscado;
    };
    public Billete buscarBillete (String idVuelo, int fila, int columna){
        Billete billeteBuscado = null;
        for (Billete billete: lista) {
            if (billete.getVuelo().getID().equals(idVuelo)  && billete.getColumna() == columna && billete.getFila() == fila) billeteBuscado = billete;
        }
        return billeteBuscado;
    };
    public boolean eliminarBillete (String localizador){
        boolean resultado = false;
        for (int i = 0; i < capacidad; i++){
            if (lista[i].getLocalizador().equals(localizador)){
                lista[i] = null;
                resultado = true;
            }
        }
        return resultado;
    };
    // Muestra por pantalla los billetes de la lista
    public void listarBilletes(){
        for (int i = 0; i <= getOcupacion(); i++) {
            System.out.println(getBillete(i));
        }
    };
    // Permite seleccionar un billete existente a partir de su localizador, usando el mensaje pasado como argumento para la solicitud
    // y siguiendo el orden y los textos mostrados en el enunciado
    // La función solicita repetidamente hasta que se introduzca un localizador correcto
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        String localizador;
        do {
            System.out.println(mensaje);
            localizador = teclado.next();
        }while(buscarBillete(localizador) == null);
        return buscarBillete(localizador);
    };
    // Añade los billetes al final de un fichero CSV, sin sobreescribirlo
    public boolean aniadirBilletesCsv(String fichero){
        //Preguntar si se escriben todos los billetes conocidos ahunque se repitan
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            for (int i = 0; i < getOcupacion()-1; i++) {
                salida.printf("%s;%s;%s;%s;%d;%d;%f\n",
                        lista[i].getLocalizador(),
                        lista[i].getVuelo(),
                        lista[i].getPasajero().toString(),
                        lista[i].getTipo().toString(),
                        lista[i].getFila(),
                        lista[i].getColumna(),
                        lista[i].getPrecio());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (salida != null) {
                salida.close();
            }
        }return true;
    };

    // Métodos estáticos
    
    // Lee los billetes del fichero CSV y los añade a las listas de sus respectivos Vuelos y Pasajeros
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros){
        Scanner entrada = null;
        int lineas = 0;
        if (Utilidades.contarLineasFichero(ficheroBilletes, "Billetes") != -1){
            lineas = Utilidades.contarLineasFichero(ficheroBilletes, "Billetes");
        }
        try {
            entrada = new Scanner(new FileReader(ficheroBilletes));
            for (int i = 0; i < lineas; i++){
                String[] linea = entrada.nextLine().split(";");
                String localizador = linea[0];
                Vuelo vuelo = vuelos.buscarVuelo(linea[1]);
                Pasajero pasajero = pasajeros.buscarPasajeroDNI(linea[2]);
                Billete.TIPO tipo = Billete.TIPO.valueOf(linea[3]);
                int fila = Integer.parseInt(linea[4]);
                int columna = Integer.parseInt(linea[5]);
                double precio = Double.parseDouble(linea[6]);

                Billete billete = new Billete(localizador, vuelo, pasajero, tipo, fila, columna, precio);

                pasajero.aniadirBillete(billete);
                vuelo.ocuparAsiento(billete);


            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Billetes no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Billetes.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Billetes.");
                }
            }
        }
    };
}
